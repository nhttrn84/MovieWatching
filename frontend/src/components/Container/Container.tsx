import React from 'react';
import { Container, Grid, Typography } from '@mui/material';
import { MovieCard } from '../../components';
import { featuredMovies, newReleases } from '../../contexts/MovieData/MovieData';

const MovieContainer: React.FC = () => {
    return (
        <Container>
            <Typography variant="h4" gutterBottom>
                Phim nổi bật
            </Typography>
            <Grid container spacing={4}>
                {featuredMovies.map((movie, index) => (
                    <Grid item key={index} xs={12} sm={6} md={4}>
                        <MovieCard {...movie} />
                    </Grid>
                ))}
            </Grid>

            <Typography variant="h4" gutterBottom>
                Phim mới nổi bật
            </Typography>
            <Grid container spacing={4}>
                {newReleases.map((movie, index) => (
                    <Grid item key={index} xs={12} sm={6} md={4}>
                        <MovieCard {...movie} />
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
};

export default MovieContainer;
