import React from 'react';
import { Container, Box } from '@mui/material';

const MovieStreaming: React.FC = (video: string) => {
  return (
    <Container>
      <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', mt: 4 }}>
        {video && (
          <Box sx={{ mt: 4 }}>
            <video width="600" controls>
              <source src={video} type="video/mp4" />
              Your browser does not support the video tag.
            </video>
          </Box>
        )}
      </Box>
    </Container>
  );
};

export default MovieStreaming;
