import { useQuery } from "@tanstack/react-query";
import { movieAPI } from "../api";

interface Episode {
  title: string;
  link: string;
}

interface Person {
  name: string;
  image: string;
  character: string;
}

interface MovieDetail {
  title: string;
  subTitle: string;
  poster: string;
  date: string;
  status: string;
  rating: string;
  categories: string[];
  episodes: Episode[];
  info: string;
  creators: Person[];
  actors: Person[];
}

interface MovieEpisode {
  video: string;
}

export function useMovieDetails(title: string | undefined) : {
  isPending: boolean;
  error: Error | null;
  movieDetails: MovieDetail | undefined
} {
  const {
    isPending,
    error,
    data: movieDetails,
  } = useQuery({
    queryKey: ["movie-details", title],
    queryFn: () => movieAPI.getMovieDetails(title),
  });

  return { isPending, error, movieDetails: movieDetails?.data?.movieDetails };
}

export function useMovieEpisode(title: string | undefined) : {
  isPending: boolean;
  error: Error | null;
  movieEpisode: MovieEpisode | undefined
} {
  const {
    isPending,
    error,
    data: movieEpisode,
  } = useQuery({
    queryKey: ["movie-episode", title],
    queryFn: () => movieAPI.getMovieEpisode(title),
  });

  return { isPending, error, movieEpisode: movieEpisode?.data?.movieEpisode };
}