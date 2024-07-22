import { useQuery } from "@tanstack/react-query";
import { categoryAPI } from "../api";

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

export function useMovieByCategory(category: string, pageNumber?: number) {
  const {
    isPending,
    error,
    data: data,
  } = useQuery({
    queryKey: ["movie-by-category", category, pageNumber],
    queryFn: () =>
      categoryAPI.getMovieByCategory({ category, pageNumber }),
  });

  return { isPending, error, data };
}