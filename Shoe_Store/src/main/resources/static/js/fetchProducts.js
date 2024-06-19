// Fetching products data
async function fetchProducts() {
    try {
        const response = await fetch('/api/v1/products');
        const products = await response.json();

        // show data in pages
        if(window.location.href == '/admin.html'){
            showProductsInAdminPage(products);
        }

        if(window.location.href == '/shop.html'){
            showProductsInShopPage(products);
        }
        
    } catch (error) {
        console.error('Error fetching products:', error);
    }
}

async function deleteProduct(id) {
    const response = await fetch(`/api/v1/products/${id}`, {
        method: 'DELETE',
    });

    if (response.ok) {
        fetchProducts();
    } else {
        console.error('Failed to delete product');
    }
}

// Show Products In Admin Page
function showProductsInAdminPage(products){
    const tbody = document.querySelector('#product-table tbody');
    tbody.innerHTML = '';

    products.forEach(product => {
        const tr = document.createElement('tr');

        tr.innerHTML = `
            <td>${product.id}</td>
            <td>${product.productName}</td>
            <td>${product.productPrice}</td>
            <td>${product.productDescription}</td>
            <td>${product.productQuantity}</td>
            <td>${product.brandName}, ${product.categories}</td>
            <td>${product.averageRating}</td>
            <td>
                <button class="productBtn-edit" onclick="editProduct(${product})">Sửa</button>
                <button class="productBtn-delete" onclick="deleteProduct(${product.id})">Xóa</button>
                <button class="productBtn-details">Chi tiết</button>
            </td>
        `;

        tbody.appendChild(tr);
    });
}


document.addEventListener('DOMContentLoaded', fetchProducts);