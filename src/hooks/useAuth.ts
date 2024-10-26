import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAppSelector } from './useAppSelector';
import { useAppDispatch } from './useAppDispatch';
import { login, logout, register } from '../services/authService';
import { clearError } from '../redux/slices/authSlice';

export const useAuth = () => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const { user, token, loading, error } = useAppSelector((state) => state.auth);

  useEffect(() => {
    if (error) {
      const timer = setTimeout(() => {
        dispatch(clearError());
      }, 5000);
      return () => clearTimeout(timer);
    }
  }, [error, dispatch]);

  const handleLogin = async (email: string, password: string) => {
    try {
      await dispatch(login({ email, password })).unwrap();
      navigate('/dashboard');
    } catch (err) {
      console.error('Login failed:', err);
    }
  };

  const handleRegister = async (name: string, email: string, password: string) => {
    try {
      await dispatch(register({ name, email, password })).unwrap();
      navigate('/dashboard');
    } catch (err) {
      console.error('Registration failed:', err);
    }
  };

  const handleLogout = async () => {
    try {
      await dispatch(logout()).unwrap();
      navigate('/');
    } catch (err) {
      console.error('Logout failed:', err);
    }
  };

  return {
    user,
    token,
    loading,
    error,
    login: handleLogin,
    register: handleRegister,
    logout: handleLogout,
  };
};

export default useAuth;