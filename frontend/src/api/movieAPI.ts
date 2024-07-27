import customAxios from './customAPI';

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

interface MovieDetailResponse {
  status: string;
  message: string;
  status_code: number;
  data: {
    movieDetails?: MovieDetail;
  };
}

interface MovieEpisode {
  video: string;
}

interface MovieEpisodeResponse {
  status: string;
  message: string;
  status_code: number;
  data: {
    movieEpisode?: MovieEpisode;
  };
}

class MovieAPI {
  async getMovieDetails(title: string | undefined): Promise<MovieDetailResponse> {
    if (!title) {
        throw new Error('No title specified 💥');
    }

    const response = await customAxios.get(
      `/detail/${title}`
    );
    return response.data;
  }

  async getMovieEpisode(title: string | undefined): Promise<MovieEpisodeResponse> {
    if (!title) {
      throw new Error('No title specified 💥');
    }

    const response = await customAxios.get(
      `/episode/${title}`
    );
    return response.data;
  }
}

export default new MovieAPI();