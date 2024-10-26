package com.timebank.service;

import com.timebank.dto.RequestDTO;
import com.timebank.model.Request;
import com.timebank.model.User;
import com.timebank.repository.RequestRepository;
import com.timebank.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final SecurityUtils securityUtils;
    private final WebSocketService webSocketService;

    @Cacheable(value = "requestCache", key = "'requests'")
    public Page<RequestDTO> getAllRequests(Pageable pageable) {
        return requestRepository.findByStatusOrderByCreatedAtDesc("open", pageable)
            .map(this::mapToDTO);
    }

    @Cacheable(value = "requestCache", key = "#id")
    public RequestDTO getRequestById(Long id) {
        Request request = requestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Request not found"));
        return mapToDTO(request);
    }

    @Async
    public CompletableFuture<List<RequestDTO>> searchRequests(String query) {
        return CompletableFuture.completedFuture(
            requestRepository.findByTitleContainingOrDescriptionContaining(query, query)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList())
        );
    }

    @Transactional
    @CacheEvict(value = "requestCache", allEntries = true)
    public RequestDTO createRequest(RequestDTO requestDTO) {
        User user = securityUtils.getCurrentUser();
        
        Request request = new Request();
        request.setTitle(requestDTO.getTitle());
        request.setDescription(requestDTO.getDescription());
        request.setEstimatedHours(requestDTO.getEstimatedHours());
        request.setStatus("open");
        request.setCreatedAt(LocalDateTime.now());
        request.setUser(user);

        request = requestRepository.save(request);
        
        RequestDTO createdRequest = mapToDTO(request);
        webSocketService.broadcastRequestUpdate(request.getId().toString(), createdRequest);
        
        return createdRequest;
    }

    @Transactional
    @CacheEvict(value = "requestCache", key = "#id")
    public RequestDTO updateRequest(Long id, RequestDTO requestDTO) {
        Request request = requestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Request not found"));
            
        User currentUser = securityUtils.getCurrentUser();
        if (!request.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Not authorized to update this request");
        }

        request.setTitle(requestDTO.getTitle());
        request.setDescription(requestDTO.getDescription());
        request.setEstimatedHours(requestDTO.getEstimatedHours());
        
        request = requestRepository.save(request);
        
        RequestDTO updatedRequest = mapToDTO(request);
        webSocketService.broadcastRequestUpdate(request.getId().toString(), updatedRequest);
        
        return updatedRequest;
    }

    @Transactional
    @CacheEvict(value = "requestCache", allEntries = true)
    public void deleteRequest(Long id) {
        Request request = requestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Request not found"));
            
        User currentUser = securityUtils.getCurrentUser();
        if (!request.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Not authorized to delete this request");
        }

        requestRepository.delete(request);
        webSocketService.broadcastRequestUpdate(id.toString(), null);
    }

    private RequestDTO mapToDTO(Request request) {
        RequestDTO dto = new RequestDTO();
        dto.setId(request.getId());
        dto.setTitle(request.getTitle());
        dto.setDescription(request.getDescription());
        dto.setEstimatedHours(request.getEstimatedHours());
        dto.setStatus(request.getStatus());
        dto.setCreatedAt(request.getCreatedAt());
        dto.setUser(mapToUserDTO(request.getUser()));
        dto.setBids(request.getBids().stream()
            .map(this::mapToBidDTO)
            .collect(Collectors.toList()));
        return dto;
    }

    // Add necessary mapping methods
}