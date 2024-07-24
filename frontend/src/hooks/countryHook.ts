import { useQuery } from "@tanstack/react-query";
import { countryAPI } from "../api";

interface Movie {
  title: string;
  subTitle: string;
  link: string;
  poster: string;
  status: string;
}

export function useAllCountries() {
  const {
    isPending,
    error,
    data: countries,
  } = useQuery({
    queryKey: ["countries"],
    queryFn: () => countryAPI.getAllCountries(),
  });

  return { isPending, error, countries: countries?.data?.countries };
}

export function useMovieByCountry(country: string | undefined, pageNumber?: number): {
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
    queryKey: ["movie-by-country", country, pageNumber],
    queryFn: () =>
        countryAPI.getMovieByCountry({ country, pageNumber }),
  });

  return { isPending, error, movies: movieList?.data?.movies, currentPage: movieList?.data?.currentPage  };
}