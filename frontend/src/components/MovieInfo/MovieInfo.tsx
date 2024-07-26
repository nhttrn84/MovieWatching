import { useState } from 'react';
import { Typography, Grid, Paper, Box, Rating, Button } from '@mui/material';
import { styled } from '@mui/material/styles';
import { useNavigate } from 'react-router-dom';
import { normalizeString } from '../../utils/stringUtils';
import { extractTitleFromURL } from '../../utils/extractLinkUtils';

const PREFIX = 'MovieInfo';
const classes = {
  root: `${PREFIX}-root`,
  image: `${PREFIX}-image`,
  details: `${PREFIX}-details`,
  title: `${PREFIX}-title`,
  info: `${PREFIX}-info`,
  rating: `${PREFIX}-rating`,
  paper: `${PREFIX}-paper`,
  description: `${PREFIX}-description`,
  showMoreButton: `${PREFIX}-showMoreButton`,
};

const Root = styled('div')(({ theme }) => ({
  [`& .${classes.paper}`]: {
    backgroundColor: theme.palette.background.paper,
    padding: theme.spacing(2),
  },
  [`& .${classes.image}`]: {
    width: '100%',
    height: 'auto',
  },
  [`& .${classes.details}`]: {
    padding: theme.spacing(2),
  },
}));

interface Episode {
    title: string;
    link: string;
}

interface Person {
    name: string;
    image: string;
    character: string;
}

interface MovieDetail {
    title: string;
    subTitle: string;
    poster: string;
    date: string;
    status: string;
    rating: string;
    categories: string[];
    episodes: Episode[];
    info: string;
    creators: Person[];
    actors: Person[];
}

const MovieInfo: React.FC<MovieDetail> = ({
    title, 
    subTitle, 
    poster, 
    date, 
    status, 
    rating, 
    categories,
    episodes, 
    info,
    creators, 
    actors }) => {
  const navigate = useNavigate();
  const [showMore, setShowMore] = useState(false);
  const [menuSelect, setMenuSelect] = useState(false);

  const handleCategory = (category: string) => {
    navigate(`/the-loai/${normalizeString(category)}`);
  };

  const handleEpisode = (url: string) => {
    navigate(`${extractTitleFromURL(url)}`);
  };

  const handleShowMore = () => {
    setShowMore(!showMore);
  };

  const handleSelectInfo = () => {
    setMenuSelect(false);
  };

  const handleSelectActors = () => {
    setMenuSelect(true);
  };

  const shortDescription =
    info?.split('.').slice(0, 3).join('. ') + '...';

  return (
    <Root>
      <Paper className={classes.paper}>
        <Grid container spacing={2}>
          <Grid item xs={12} sm={4}>
            <img
              src={poster}
              alt="Movie poster"
              className={classes.image}
            />
          </Grid>
          <Grid item xs={12} sm={8} className={classes.details}>
            <Typography variant="h5" className={classes.title}>
              {title}
            </Typography>
            <Typography className={classes.title}>
              {subTitle}
            </Typography>
            <Typography variant="body2" className={classes.title}>
              {date}
            </Typography>
            <Box className={classes.rating}>
              <Typography
                variant="body2"
                component="span"
                style={{ marginLeft: 8 }}
              >
                {rating}
              </Typography>
              <Rating value={parseFloat(rating)/2} readOnly />
            </Box>
            
            <Typography variant="body1" className={classes.info}>
              <div>
                {categories?.map((category: string, index: number) => (
                  <span
                    key={index}
                    onClick={() => handleCategory(category)}
                    style={{ cursor: 'pointer' }}
                  >
                    &nbsp;{category}
                    {index < categories?.length - 1 ? ' | ' : ''}
                  </span>
                ))}
              </div>
            </Typography>
          </Grid>
        </Grid>
        <Button onClick={handleSelectInfo} >Thông tin</Button>
        <Button onClick={handleSelectActors} >Diễn viên</Button>
        <Grid>
          { menuSelect ? (
            <div>
              <Typography>
                Đạo diễn
              </Typography>
              <div>
              {creators?.map((person: Person, index: number) => (
                  <span
                    key={index}
                    style={{ cursor: 'pointer' }}
                  >
                    &nbsp;{person?.name}
                    &nbsp;{person?.character}
                    {index < categories?.length - 1 ? ' | ' : ''}
                  </span>
                ))}
              </div>
              <Typography>
                DIễn viên
              </Typography>
              <div>
              {actors?.map((person: Person, index: number) => (
                  <span
                    key={index}
                    style={{ cursor: 'pointer' }}
                  >
                    <Grid container>
                      <Grid item>
                      <img
                        src={person?.image}
                        alt="Actor"
                      />
                        {person?.name}
                        {person?.character}
                      </Grid>
                    </Grid>
                  </span>
                ))}
              </div>
            </div>
          ) : (
            <div>
              <Typography variant="body2" className={classes.description}>
                {showMore ? info : shortDescription}
              </Typography>
              <Button onClick={handleShowMore} className={classes.showMoreButton}>
                {showMore ? 'Thu gọn' : 'Xem thêm'}
              </Button>
            </div>
          )}
        </Grid>
      </Paper>
    </Root>
  );
};

export default MovieInfo;