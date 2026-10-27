import { useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const Navbar = () => {
    const { user, logout } = useContext(AuthContext);
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <nav className="navbar">
            <h2>Sistema de Préstamos</h2>
            {user && (
                <div className="user-info">
                    <span>Bienvenido, <strong>{user}</strong></span>
                    <button onClick={handleLogout} className="btn-logout">Cerrar Sesión</button>
                </div>
            )}
        </nav>
    );
};

export default Navbar;