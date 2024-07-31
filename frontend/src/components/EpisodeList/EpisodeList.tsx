import React from 'react';
import { Container, Button } from '@mui/material';

interface Episode {
    title: string;
    link: string;
}

const EpisodeList: React.FC<Episode[]> = (episodeList) => {

    return (
        <Container>
            {episodeList?.map((episode, index) => (
                <Button>
                    {index + 1}
                </Button>
            ))}
        </Container>
    );
};

export default EpisodeList;