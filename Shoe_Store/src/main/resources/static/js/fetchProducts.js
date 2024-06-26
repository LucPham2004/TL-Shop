// Fetching products data
async function fetchProducts() {
    try {
        const response = await fetch('/api/v1/products');
        const products = await response.json();

        if (window.location.pathname.endsWith('admin.html')) {
            showProductsInAdminPage(products);
        }
        
        if (window.location.pathname.endsWith('shop.html')) {
            showProductsInShopPage(products);
        }

    } catch (error) {
        console.error('Error fetching products:', error);
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
    productsContainer.innerHTML = '';

    products.forEach(product => {
        const productItem = document.createElement('div');
        productItem.classList.add('product-item');

        price = product.productPrice * (100 - product.discountPercent) / 100;

        // Link ảnh tượng trưng
        productItem.innerHTML = `
            <a href="/products.html?${convertProductName(product.productName)}&id=${product.id}">
                <img alt="${product.productName}" src="../img/homepage/favorite/favorite1.png">
                <p class="product-name">${product.productName}</p>
                <p class="price">${formatNumber(price)} đ 
                    <span class="originPrice" style="text-decoration: line-through;">${formatNumber(product.productPrice)} đ</span>
                </p>
            </a>
        `;

        productsContainer.appendChild(productItem);
    });
}

document.addEventListener('DOMContentLoaded', fetchProducts);

// Minor functions

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

function formatNumber(number) {
    return number.toLocaleString('vi-VN');
}