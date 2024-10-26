import { createAsyncThunk } from '@reduxjs/toolkit';
import { userApi } from '../api/apiClient';

export const fetchProfile = createAsyncThunk(
  'user/fetchProfile',
  async () => {
    const response = await userApi.getProfile();
    return response.data;
  }
);

export const updateProfile = createAsyncThunk(
  'user/updateProfile',
  async (profileData: any) => {
    const response = await userApi.updateProfile(profileData);
    return response.data;
  }
);

export const fetchTransactions = createAsyncThunk(
  'user/fetchTransactions',
  async () => {
    const response = await userApi.getTransactions();
    return response.data;
  }
);