import { createContext, useState } from 'react';
import api from '../api/axiosInstance';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useState(localStorage.getItem('token') || null);
    const [user, setUser] = useState(localStorage.getItem('user') || null);

    const login = async (username, password) => {
        try {
            const response = await api.post('/auth/login', { username, password });
            const jwtToken = response.data.token;

            setToken(jwtToken);
            setUser(username);
            localStorage.setItem('token', jwtToken);
            localStorage.setItem('user', username);
            return { success: true };
        } catch (error) {
            return {
                success: false,
                message: error.response?.data?.message || 'Error al iniciar sesión'
            };
        }
    };

    const logout = () => {
        setToken(null);
        setUser(null);
        localStorage.removeItem('token');
        localStorage.removeItem('user');
    };

    return (
        <AuthContext.Provider value={{ token, user, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};