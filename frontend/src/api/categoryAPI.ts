import customAxios from './customAPI';

interface GetMovieByCategoryParams {
  category: string;
  pageNumber?: number;
}

class CategoryAPI {
  async getAllCategories(): Promise<Array<string>> {
    const response = await customAxios.get('/categories');
    return response.data;
  }

  async getMovieByCategory({ category, pageNumber = 1 }: GetMovieByCategoryParams): Promise<Array<string>> {
    if (!category) {
      throw new Error('No category specified 💥');
    }

    const response = await customAxios.get(
      `/category/${category}?page=${pageNumber}`
    );
    return response.data;
  }
}

export default new CategoryAPI();