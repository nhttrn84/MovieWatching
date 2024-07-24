import { useQuery } from "@tanstack/react-query";
import { categoryAPI } from "../api";

interface Movie {
  title: string;
  subTitle: string;
  link: string;
  poster: string;
  status: string;
}

export function useAllCategories() {
  const {
    isPending,
    error,
    data: categories,
  } = useQuery({
    queryKey: ["categories"],
    queryFn: () => categoryAPI.getAllCategories(),
  });

  return { isPending, error, categories: categories?.data?.categories };
}

export function useMovieByCategory(category: string | undefined, pageNumber?: number): {
  isPending: boolean;
  error: Error | null;
  movies: Movie[] | undefined;
  currentPage: number | undefined;
}  {
  const {
    isPending,
    error,
    data: movieList,
  } = useQuery({
    queryKey: ["movie-by-category", category, pageNumber],
    queryFn: () =>
      categoryAPI.getMovieByCategory({ category, pageNumber }),
  });

  return { isPending, error, movies: movieList?.data?.movies, currentPage: movieList?.data?.currentPage };
}