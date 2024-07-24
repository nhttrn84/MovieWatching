import React from 'react';
import { Container, Grid } from '@mui/material';
import { MovieCard } from '../../components';

interface Movie {
    title: string;
    subTitle: string;
    link: string;
    poster: string;
    status: string;
}

interface MovieList {
    movies: Movie[] | undefined;
}

const MovieContainer: React.FC<MovieList> = ({ movies }) => {
    return (
        <Container>
            <Grid container spacing={4}>
                {movies?.map((movie, index) => (
                    <Grid item key={index} xs={12} sm={6} md={4}>
                        <MovieCard {...movie} />
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
};

export default MovieContainer;
