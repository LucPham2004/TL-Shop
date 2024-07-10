// Function to fetch brand data
async function fetchBrandData() {
    try {
        const response = await fetch('/api/v1/brand');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return await response.json();
        
    } catch (error) {
        console.error('There has been a problem with your fetch operation:', error);
    }
}