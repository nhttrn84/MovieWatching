import './App.css'
import { Homepage, Category, Country, FeatureMovie } from './pages'
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { Layout } from './components';

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 8 * 60 * 1000,
      refetchOnWindowFocus: false,
    },
  },
});

function App() {

  return (
    <QueryClientProvider client={queryClient}>
      <ReactQueryDevtools initialIsOpen={false} />
      <BrowserRouter>
        <Routes>
          <Route
            path="/"
            element={
              <Layout/>
            }
          >
            <Route path="/trang-chu" element={<Homepage />} />
            <Route path="/" element={<Homepage />} />
            <Route path="/the-loai/:category" element={<Category />} />
            <Route path="/quoc-gia/:country" element={<Country />} />
            <Route path="/phim-le/:movie" element={<FeatureMovie />} />
            <Route path="/phim-bo/:movie" element={<Homepage />} />
            <Route path="/thong-tin/:movie" element={<Homepage />} />
            <Route path="/tim-kiem/:keyword" element={<Homepage />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </QueryClientProvider>
  )
}

export default App
