import React from 'react';
import { AppBar, Toolbar, Typography, Button, InputBase } from '@mui/material';
import { styled, alpha } from '@mui/material/styles';
import SearchIcon from '@mui/icons-material/Search';
import Categories from '../Categories/Categories';

const Search = styled('div')(({ theme }) => ({
    position: 'relative',
    borderRadius: theme.shape.borderRadius,
    backgroundColor: alpha(theme.palette.common.white, 0.15),
    '&:hover': {
        backgroundColor: alpha(theme.palette.common.white, 0.25),
    },
    marginRight: theme.spacing(2),
    marginLeft: 0,
    width: '100%',
    [theme.breakpoints.up('sm')]: {
        marginLeft: theme.spacing(3),
        width: 'auto',
    },
}));

const SearchIconWrapper = styled('div')(({ theme }) => ({
    padding: theme.spacing(0, 2),
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
}));

const StyledInputBase = styled(InputBase)(({ theme }) => ({
    color: 'inherit',
    '& .MuiInputBase-input': {
        padding: theme.spacing(1, 1, 1, 0),
        paddingLeft: `calc(1em + ${theme.spacing(4)})`,
        transition: theme.transitions.create('width'),
        width: '100%',
        [theme.breakpoints.up('md')]: {
            width: '20ch',
        },
    },
}));

const Header: React.FC = () => {

    return (
        <AppBar position="fixed" color="primary">
            <Toolbar>
                <div style={{ flexGrow: 1 }}>
                    <Typography variant="h6" noWrap component="div">
                        PHIMMOI.net
                    </Typography>
                </div>
                <div style={{ flexGrow: 1 }}>
                    <nav>
                        <Button color="inherit">Phim mới</Button>
                        <Button color="inherit">Phim lẻ</Button>
                        <Button color="inherit">Phim bộ</Button>
                        <Categories/>
                        <Button color="inherit">Quốc gia</Button>
                    </nav>
                </div>
                <div style={{ flexGrow: 1 }}>
                    <Search>
                        <SearchIconWrapper>
                            <SearchIcon />
                        </SearchIconWrapper>
                        <StyledInputBase placeholder="Tìm kiếm..." inputProps={{ 'aria-label': 'search' }} />
                    </Search>
                </div>
            </Toolbar>
        </AppBar>
    );
};

export default Header;
