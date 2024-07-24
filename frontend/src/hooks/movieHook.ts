import { useQuery } from "@tanstack/react-query";
import { movieAPI } from "../api";

export function useMovieDetails(type: string, title: string) {
  const {
    isPending,
    error,
    data: movieDetails,
  } = useQuery({
    queryKey: ["movie-details", type, title],
    queryFn: () => movieAPI.getMovieDetails({type, title}),
  });

  return { isPending, error, movieDetails: movieDetails?.data?.movieDetails };
}

export function useMovieEpisode(type: string, title: string) {
  const {
    isPending,
    error,
    data: movieEpisode,
  } = useQuery({
    queryKey: ["movie-episode", type, title],
    queryFn: () =>
        movieAPI.getMovieEpisode({ type, title }),
  });

  return { isPending, error, movieEpisode: movieEpisode?.data?.movieEpisode };
}