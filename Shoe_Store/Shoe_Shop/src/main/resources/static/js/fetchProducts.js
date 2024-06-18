async function fetchProducts() {
    try {
        const response = await fetch('/api/v1/products');
        const products = await response.json();

        const tbody = document.querySelector('#product-table tbody');
        tbody.innerHTML = '';

        // Show products in admin page
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


        // Show data in shop page
        const productsContainer = document.querySelector('.Products .products-container');

        products.forEach(product => {
            const productItem = document.createElement('div');
            productItem.classList.add('product-item');

            // Link ảnh tượng trưng, phải chỉnh lại
            productItem.innerHTML = `
                <img alt="${product.productName}" src="../img/homepage/favorite/favorite1.png">
                <p class="product-name">${product.productName}</p>
                <p class="description">${product.productDescription}</p>
                <p class="price">${product.productPrice}đ</p>
            `;

            productsContainer.appendChild(productItem);
        });
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

document.addEventListener('DOMContentLoaded', fetchProducts);