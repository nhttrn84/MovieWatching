import React from 'react';
import { Card, CardMedia, CardContent, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { extractTitleFromURL } from '../../utils/extractLinkUtils';

interface Movie {
    title: string;
    subTitle: string;
    link: string;
    poster: string;
    status: string;
}
const MovieCard: React.FC<Movie> = ({ title, subTitle, link, poster, status }) => {
    const navigate = useNavigate();
    const handleMovieClick = () => {
        navigate(`/${extractTitleFromURL(link)}`);
    };

    return (
        <Card onClick={handleMovieClick} >
            <CardMedia component="img" height="140" image={poster} alt={title} />
            <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                    {title}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    {subTitle}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    {status}
                </Typography>
            </CardContent>
        </Card>
    )
};

export default MovieCard;
