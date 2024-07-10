// Brands Methods
// Show Brands
function showBrandsInAdminPage(brands) {
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
};
// Create Brand
document.getElementById('addBrandForm').addEventListener('submit', function(event) {
    event.preventDefault();  
    var brandName = document.getElementById('brandName').value;

    fetch('/api/v1/brand', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: brandName
    })
    .then(response => response.json())
    .then(data => {
        console.log('Success:', data);
    })
    .catch((error) => {
        console.error('Error:', error);
    });

    fetchBrandData();
});

// Delete Brand
document.getElementById('deleteBrandForm').addEventListener('submit', function(event) {
    event.preventDefault();  
    if(confirm("Bạn có chắc muốn xóa thương hiệu này?")) {
        var brandId = document.getElementById('brandId').value;

        fetch(`/api/v1/brand/${brandId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: brandId
        })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });

        fetchBrandData();
    } else {
        return
    }
});

