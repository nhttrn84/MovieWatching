import React from 'react';
import { Header } from '../../components';
import { Outlet } from 'react-router-dom';

const Layout: React.FC = () => {
    return (
        <div>
            <Header />
            <div style={{ marginTop: '50px' }} className="flex-1">
            <Outlet />
          </div>
        </div>
    );
};

export default Layout;
