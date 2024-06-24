// Fetching products data
async function fetchProducts() {
    try {
        const response = await fetch('/api/v1/products');
        const products = await response.json();

        showProductsInShopPage(products);
        showProductsInAdminPage(products);
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

// Show Products In Shop Page
function showProductsInShopPage(products){
    const productsContainer = document.querySelector('.main #products-container');
    if (!productsContainer) {
        console.error('No #products-container found in the DOM');
        return;
    }
    else {
        console.log(products);
    }
    productsContainer.innerHTML = '';

    products.forEach(product => {
        const productItem = document.createElement('div');
        productItem.classList.add('product-item');

        // Link ảnh tượng trưng
        productItem.innerHTML = `
            <a href="/products.html?${convertProductName(product.productName)}&id=${product.id}">
                <img alt="${product.productName}" src="../img/homepage/favorite/favorite1.png">
                <p class="productName">${product.productName}</p>
                <p class="price">${product.price}đ</p>
            </a>
        `;

        productsContainer.appendChild(productItem);
    });
}

document.addEventListener('DOMContentLoaded', fetchProducts);

function removeVietnameseTones(str) {
    str = str.normalize('NFD').replace(/[\u0300-\u036f]/g, '');
    str = str.replace(/đ/g, 'd').replace(/Đ/g, 'D');
    return str;
}

function convertProductName(productName) {
    // Bỏ dấu tiếng Việt
    let noToneName = removeVietnameseTones(productName);
    
    // Thay thế khoảng trắng bằng dấu gạch ngang
    let convertedName = noToneName.replace(/\s+/g, '-');
    
    return convertedName;
}