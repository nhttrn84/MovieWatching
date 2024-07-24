import { useState, ChangeEvent } from 'react';
import { Typography, Pagination } from '@mui/material';
import { useParams } from 'react-router-dom';
import { styled } from '@mui/material/styles';
import { useMovieByCategory } from '../../hooks/categoryHook';
import { Container } from '../../components';

const PREFIX = 'Categories';
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

const Category: React.FC = () => {
  const { category } = useParams<{ category: string }>();
  const [page, setPage] = useState(1);

  const handlePageChange = (event: ChangeEvent<unknown>, value: number) => {
    setPage(value);
  };
  const { movies, currentPage } = useMovieByCategory(category);
  const title = category ? category : 'Category';

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

export default Category;
