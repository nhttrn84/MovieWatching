import { Container, Grid, useMediaQuery, useTheme } from '@mui/material';
import { styled } from '@mui/material/styles';
import { useParams } from 'react-router-dom';
import { useMovieDetails } from '../../hooks/movieHook';
import { MovieInfo, EpisodeList } from '../../components';

const PREFIX = 'MovieIntroduction';
const classes = {
  root: `${PREFIX}-root`,
  sidebar: `${PREFIX}-sidebar`,
  content: `${PREFIX}-content`,
  breadcrumbs: `${PREFIX}-breadcrumbs`,
};

const Root = styled('div')(({ theme }) => ({
  [`&.${classes.root}`]: {
    display: 'flex',
  },
  [`& .${classes.sidebar}`]: {
    width: '30%',
  },
  [`& .${classes.content}`]: {
    width: '70%',
    padding: theme.spacing(2),
  },
  [`& .${classes.breadcrumbs}`]: {
    width: '100%',
  },
}));

const MovieDetails = () => {
  const { movie } = useParams<{ movie: string }>();

  const { movieDetails } = useMovieDetails(`phim-bo/${movie}`);

  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

  return (
    <Root className={classes.root}>
      <Container>
        {isMobile ? (
          <Grid>
            hi
          </Grid>
        ) : (
          <Grid container>
            <Grid item className={classes.content}>
              {movieDetails && (
                <>
                <MovieInfo {...movieDetails}/>
                <EpisodeList episodeList={movieDetails?.episodes}/>
                </>
              )
              }
            </Grid>
          </Grid>
        )}
      </Container>
    </Root>
  );
};

export default MovieDetails;