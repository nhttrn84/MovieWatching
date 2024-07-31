import React from 'react';
import { Container, Box } from '@mui/material';

interface MovieEpisodes {
  video: string;
}

const MovieStreaming: React.FC<MovieEpisodes | undefined> = (movieEpisode) => {
  
  return (
    <Container>
      <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', mt: 4 }}>
        {movieEpisode && (
          <Box sx={{ mt: 4 }}>
            <iframe width="100%" allowFullScreen src={movieEpisode.video}/>
          </Box>
        )}
      </Box>
    </Container>
  );
};

export default MovieStreaming;
