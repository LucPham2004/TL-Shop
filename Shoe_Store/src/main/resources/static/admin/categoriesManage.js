// Category Methods
// Show Categories
function showCategoriesInAdminPage(categories) {
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
};
// Create Category
document.getElementById('addCategoryForm').addEventListener('submit', function(event) {
    event.preventDefault();  
    var categoryName = document.getElementById('categoryName').value;

    fetch('/api/v1/category', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: categoryName
    })
    .then(response => response.json())
    .then(data => {
        console.log('Success:', data);
    })
    .catch((error) => {
        console.error('Error:', error);
    });

    fetchCategoryData();
});

// Delete Category
document.getElementById('deleteCategoryForm').addEventListener('submit', function(event) {
    event.preventDefault();  
    if(confirm("Bạn có chắc muốn xóa danh mục này?")) {
        var categoryId = document.getElementById('categoryId').value;

        fetch(`/api/v1/category/${categoryId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: categoryId
        })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
        
        fetchCategoryData();
    } else {
        return
    }
});


