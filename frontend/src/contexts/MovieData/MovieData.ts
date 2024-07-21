// moviesData.ts
interface Movie {
    title: string;
    year: number;
    imageUrl: string;
    description: string;
}

export const featuredMovies: Movie[] = [
    {
        title: 'Nâng Cấp',
        year: 2024,
        imageUrl: 'path/to/nang-cap.jpg',
        description: 'HD VIETSUB'
    },
    {
        title: 'Bhakshak: Tội lỗi lầm ngơ',
        year: 2024,
        imageUrl: 'path/to/bhakshak.jpg',
        description: 'HD VIETSUB'
    }
];

export const newReleases: Movie[] = [
    {
        title: 'Họ Nhân Bản Tyrone',
        year: 2023,
        imageUrl: 'path/to/ho-nhan-ban-tyrone.jpg',
        description: ''
    }
    // Add more movies as needed
];
