import { useState, useEffect } from 'react';
import api from '../api/axiosInstance';
import Navbar from '../components/Navbar';

const Dashboard = () => {
    const [equipos, setEquipos] = useState([]);
    const [prestamos, setPrestamos] = useState([]);
    const [activeTab, setActiveTab] = useState('equipos');
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        setLoading(true);
        try {
            const [equiposRes, prestamosRes] = await Promise.all([
                api.get('/equipos'),
                api.get('/prestamos')
            ]);
            setEquipos(equiposRes.data);
            setPrestamos(prestamosRes.data);
        } catch (error) {
            console.error('Error cargando datos:', error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            <Navbar />
            <div className="main-content">
                <div className="tabs">
                    <button
                        className={activeTab === 'equipos' ? 'active' : ''}
                        onClick={() => setActiveTab('equipos')}
                    >
                        Equipos
                    </button>
                    <button
                        className={activeTab === 'prestamos' ? 'active' : ''}
                        onClick={() => setActiveTab('prestamos')}
                    >
                        Préstamos
                    </button>
                </div>

                {loading ? (
                    <p>Cargando datos...</p>
                ) : (
                    <div className="table-container">
                        {activeTab === 'equipos' ? (
                            <table>
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Código Patrimonial</th>
                                    <th>Nombre</th>
                                    <th>Tipo</th>
                                    <th>Estado</th>
                                </tr>
                                </thead>
                                <tbody>
                                {equipos.map((eq) => (
                                    <tr key={eq.id}>
                                        <td>{eq.id}</td>
                                        <td>{eq.codigoPatrimonial}</td>
                                        <td>{eq.nombre}</td>
                                        <td>{eq.tipo}</td>
                                        <td>
                        <span className={`badge ${eq.estado === 'DISPONIBLE' ? 'success' : 'warning'}`}>
                          {eq.estado}
                        </span>
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        ) : (
                            <table>
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Solicitante</th>
                                    <th>Fecha Préstamo</th>
                                    <th>Devolución Prevista</th>
                                    <th>Estado</th>
                                </tr>
                                </thead>
                                <tbody>
                                {prestamos.map((pr) => (
                                    <tr key={pr.id}>
                                        <td>{pr.id}</td>
                                        <td>{pr.solicitante}</td>
                                        <td>{pr.fechaPrestamo}</td>
                                        <td>{pr.fechaPrevistaDevolucion}</td>
                                        <td>
                        <span className={`badge ${pr.estado === 'ACTIVO' ? 'warning' : 'success'}`}>
                          {pr.estado}
                        </span>
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        )}
                    </div>
                )}
            </div>
        </div>
    );
};

export default Dashboard;