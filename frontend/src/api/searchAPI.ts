import customAxios from './customAPI';

interface GetMovieBySearchParams {
  keyword: string;
  pageNumber?: number;
}

class SearchAPI {
  async getMovieBySearch({ keyword, pageNumber = 1 }: GetMovieBySearchParams): Promise<Array<string>> {
    if (!keyword) {
      throw new Error('No keyword specified 💥');
    }

    const response = await customAxios.get(
      `/search/keyword=${keyword}?page=${pageNumber}`
    );
    return response.data;
  }
}

export default new SearchAPI();