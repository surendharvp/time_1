import { configureStore } from '@reduxjs/toolkit';
import userReducer from './slices/userSlice';
import requestsReducer from './slices/requestsSlice';
import transactionsReducer from './slices/transactionsSlice';
import notificationsReducer from './slices/notificationsSlice';

export const store = configureStore({
  reducer: {
    user: userReducer,
    requests: requestsReducer,
    transactions: transactionsReducer,
    notifications: notificationsReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;