import React from 'react';
import { Card, CardMedia, CardContent, Typography } from '@mui/material';

interface Movie {
    title: string;
    year: number;
    imageUrl: string;
    description: string;
}

const MovieCard: React.FC<Movie> = ({ title, year, imageUrl, description }) => (
    <Card>
        <CardMedia component="img" height="140" image={imageUrl} alt={title} />
        <CardContent>
            <Typography gutterBottom variant="h5" component="div">
                {title}
            </Typography>
            <Typography variant="body2" color="text.secondary">
                {year}
            </Typography>
            <Typography variant="body2" color="text.secondary">
                {description}
            </Typography>
        </CardContent>
    </Card>
);

export default MovieCard;
