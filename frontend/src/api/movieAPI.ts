import customAxios from './customAPI';

interface GetMovieDetailParams {
  type: string;
  title: string;
}

interface MovieDetailResponse {
  status: string;
  message: string;
  status_code: number;
  data: {
    movieDetails?: string[];
  };
}

interface MovieEpisodeResponse {
  status: string;
  message: string;
  status_code: number;
  data: {
    movieEpisode?: string[];
  };
}

class MovieAPI {
  async getMovieDetails({ type, title }: GetMovieDetailParams): Promise<MovieDetailResponse> {
    if (!type) {
      throw new Error('No type specified 💥');
    }

    if (!title) {
        throw new Error('No title specified 💥');
    }

    const response = await customAxios.get(
      `/detail/${type}/${title}`
    );
    return response.data;
  }

  async getMovieEpisode({ type, title }: GetMovieDetailParams): Promise<MovieEpisodeResponse> {
    if (!type) {
      throw new Error('No type specified 💥');
    }

    if (!title) {
      throw new Error('No title specified 💥');
    }

    const response = await customAxios.get(
      `/episode/${type}/${title}`
    );
    return response.data;
  }
}

export default new MovieAPI();