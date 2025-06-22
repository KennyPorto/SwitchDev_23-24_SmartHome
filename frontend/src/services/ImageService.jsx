import axios from 'axios';

export const fetchImages = async (query) => {
    try {
        const response = await axios.get('https://api.pexels.com/v1/search', {
            params: {
                query: query,
                total_results: 10,
            },
            headers: {
                Authorization: import.meta.env.VITE_IMAGE_KEY,
            },
        });

        return response.data.photos;
    } catch (error) {
        console.error('Error fetching images:', error);
        throw error;
    }

};