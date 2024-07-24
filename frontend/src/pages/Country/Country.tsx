import { useState, ChangeEvent } from 'react';
import { Typography, Pagination } from '@mui/material';
import { useParams } from 'react-router-dom';
import { styled } from '@mui/material/styles';
import { useMovieByCountry } from '../../hooks/countryHook';
import { Container } from '../../components';

const PREFIX = 'Countries';
const classes = {
  root: `${PREFIX}-root`,
  pagination: `${PREFIX}-pagination`,
  title: `${PREFIX}-title`,
};

const Root = styled('div')(() => ({
  [`&.${classes.root}`]: {
    flexGrow: 1,
    minHeight: '100vh',
  },
  [`& .${classes.pagination}`]: {
    display: 'flex',
    justifyContent: 'center',
  },
  [`& .${classes.title}`]: {
    textTransform: 'uppercase',
  },
}));

const Country: React.FC = () => {
  const { country } = useParams<{ country: string }>();
  const [page, setPage] = useState(1);

  const handlePageChange = (event: ChangeEvent<unknown>, value: number) => {
    setPage(value);
  };
  const { movies, currentPage } = useMovieByCountry(country);
  const title = country ? country : 'Country';

  return (
    <Root className={classes.root}>
       <Typography className={classes.title} component="h5" variant="h5">
        {title}
      </Typography>
      <Container movies={movies} />
      <div className={classes.pagination}>
          <Pagination
            page={currentPage ?? page}
            onChange={handlePageChange}
            showFirstButton
            showLastButton
          />
        </div>
    </Root>
  );
};

export default Country;
