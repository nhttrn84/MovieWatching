import customAxios from './customAPI';

interface GetMovieByCountryParams {
  country: string;
  pageNumber?: number;
}

class CountryAPI {
  async getAllCountries(): Promise<Array<string>> {
    const response = await customAxios.get('/countries');
    return response.data;
  }

  async getMovieByCountry({ country, pageNumber = 1 }: GetMovieByCountryParams): Promise<Array<string>> {
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