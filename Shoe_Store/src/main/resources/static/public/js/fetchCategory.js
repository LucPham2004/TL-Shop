document.addEventListener('DOMContentLoaded', () => {
    // Function to fetch brand data
    async function fetchCategoryData() {
        try {
            const response = await fetch('/api/v1/category');
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const categories = await response.json();
            populateTable(categories);
        } catch (error) {
            console.error('There has been a problem with your fetch operation:', error);
        }
    }

    function populateTable(categories) {
        const idColumn = document.querySelector('#categoryTable .column:nth-child(1)');
        const nameColumn = document.querySelector('#categoryTable .column:nth-child(2)');

        categories.forEach(category => {
            const idCell = document.createElement('div');
            const nameCell = document.createElement('div');

            idCell.textContent = category.id;
            nameCell.textContent = category.name;

            idColumn.appendChild(idCell);
            nameColumn.appendChild(nameCell);
        });
    }

    // Fetch the brand data when the page loads
    fetchCategoryData();
});