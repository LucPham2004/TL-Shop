// Function to fetch brand data
async function fetchCategoryData() {
    try {
        const response = await fetch('/api/v1/category');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return await response.json();
    } catch (error) {
        console.error('There has been a problem with your fetch operation:', error);
    }
}

