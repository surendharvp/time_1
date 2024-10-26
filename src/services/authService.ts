import { createAsyncThunk } from '@reduxjs/toolkit';
import { authApi } from '../api/apiClient';

export interface LoginCredentials {
  email: string;
  password: string;
}

export interface RegisterData {
  email: string;
  password: string;
  name: string;
}

export const login = createAsyncThunk(
  'auth/login',
  async (credentials: LoginCredentials) => {
    const response = await authApi.login(credentials);
    localStorage.setItem('token', response.data.token);
    return response.data;
  }
);

export const register = createAsyncThunk(
  'auth/register',
  async (userData: RegisterData) => {
    const response = await authApi.register(userData);
    localStorage.setItem('token', response.data.token);
    return response.data;
  }
);

export const logout = createAsyncThunk(
  'auth/logout',
  async () => {
    await authApi.logout();
    localStorage.removeItem('token');
  }
);