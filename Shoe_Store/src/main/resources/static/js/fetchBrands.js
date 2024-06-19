document.addEventListener('DOMContentLoaded', () => {
    // Function to fetch brand data
    async function fetchBrandData() {
        try {
            const response = await fetch('/api/v1/brand');
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const brands = await response.json();
            populateTable(brands);
        } catch (error) {
            console.error('There has been a problem with your fetch operation:', error);
        }
    }

    function populateTable(brands) {
        const idColumn = document.querySelector('#brandTable .column:nth-child(1)');
        const nameColumn = document.querySelector('#brandTable .column:nth-child(2)');

        brands.forEach(brand => {
            const idCell = document.createElement('div');
            const nameCell = document.createElement('div');

            idCell.textContent = brand.id;
            nameCell.textContent = brand.name;

            idColumn.appendChild(idCell);
            nameColumn.appendChild(nameCell);
        });
    }

    // Fetch the brand data when the page loads
    fetchBrandData();
});