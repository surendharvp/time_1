import { createAsyncThunk } from '@reduxjs/toolkit';
import { requestsApi } from '../api/apiClient';

export const fetchRequests = createAsyncThunk('requests/fetchAll', async () => {
  const response = await requestsApi.getAll();
  return response.data;
});

export const fetchRequestById = createAsyncThunk(
  'requests/fetchById',
  async (id: string) => {
    const response = await requestsApi.getById(id);
    return response.data;
  }
);

export const createRequest = createAsyncThunk(
  'requests/create',
  async (requestData: any) => {
    const response = await requestsApi.create(requestData);
    return response.data;
  }
);

export const submitBid = createAsyncThunk(
  'requests/submitBid',
  async ({ requestId, bidData }: { requestId: string; bidData: any }) => {
    const response = await requestsApi.submitBid(requestId, bidData);
    return response.data;
  }
);
