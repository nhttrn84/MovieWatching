import customAxios from './customAPI';

interface Movie {
  title: string;
  subTitle: string;
  link: string;
  poster: string;
  status: string;
}

interface GetMovieByCountryParams {
  country: string | undefined;
  pageNumber?: number;
}

interface CountryResponse {
  status: string;
  message: string;
  status_code: number;
  data: {
    countries?: string[];
  };
}

interface MovieByCountryResponse {
  status: string;
  message: string;
  status_code: number;
  data: {
    movies?: Movie[];
    currentPage?: number;
  };
}

class CountryAPI {
  async getAllCountries(): Promise<CountryResponse> {
    const response = await customAxios.get('/countries');
    return response.data;
  }

  async getMovieByCountry({ country, pageNumber = 1 }: GetMovieByCountryParams): Promise<MovieByCountryResponse> {
    if (!country) {
      throw new Error('No country specified 💥');
    }

    const response = await customAxios.get(
      `/country/${country}?page=${pageNumber}`
    );
    return response.data;
  }
}

export default new CountryAPI();