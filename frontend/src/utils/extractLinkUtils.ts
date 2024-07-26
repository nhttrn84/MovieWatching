/**
 * Extract the title from the given URL.
 * 
 * @param {string} url - The input URL
 * @returns {string} - The extracted title
 */
export function extractTitleFromURL(url: string): string {
    const baseUrl = 'https://phimmoiiii.net/';
    const title = url.replace(baseUrl, '');
    console.log(title);
    return title;
}