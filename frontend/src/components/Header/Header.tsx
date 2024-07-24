import React from 'react';
import { AppBar, Toolbar, Typography, Button } from '@mui/material';
import { Categories, Countries, Searchbar } from '../index';
import { useNavigate } from 'react-router-dom';

const Header: React.FC = () => {
    const navigate = useNavigate();

    const homeButtonClick = () => {
        navigate('/trang-chu');
    };

    return (
        <AppBar position="fixed" color="primary">
            <Toolbar>
                <div style={{ flexGrow: 1 }}>
                    <Button onClick={homeButtonClick}>
                        <Typography variant="h6" noWrap component="div" color={'white'}>
                            PHIMMOI.net
                        </Typography>
                    </Button>
                </div>
                <div style={{ flexGrow: 1 }}>
                    <Categories/>
                </div>
                <div style={{ flexGrow: 1 }}>
                    <Countries/>
                </div>
                <div style={{ flexGrow: 1 }}>
                    <Searchbar/>
                </div>
            </Toolbar>
        </AppBar>
    );
};

export default Header;
