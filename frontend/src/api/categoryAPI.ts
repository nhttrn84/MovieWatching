import customAxios from './customAPI';

interface GetMovieByCategoryParams {
  category: string;
  pageNumber?: number;
}

interface CategoryResponse {
  status: string;
  message: string;
  status_code: number;
  data: {
    categories?: string[];
  };
}

class CategoryAPI {
  async getAllCategories(): Promise<CategoryResponse> {
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