import customAxios from './customAPI';

interface Movie {
  title: string;
  subTitle: string;
  link: string;
  poster: string;
  status: string;
}

interface GetMovieByCategoryParams {
  category: string | undefined;
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

interface MovieByCategoryResponse {
  status: string;
  message: string;
  status_code: number;
  data: {
    movies?: Movie[];
    currentPage?: number;
  };
}

class CategoryAPI {
  async getAllCategories(): Promise<CategoryResponse> {
    const response = await customAxios.get('/categories');
    return response.data;
  }

  async getMovieByCategory({ category, pageNumber = 1 }: GetMovieByCategoryParams): Promise<MovieByCategoryResponse> {
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