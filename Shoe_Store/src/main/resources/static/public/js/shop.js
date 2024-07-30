document.addEventListener("DOMContentLoaded", async function() {
    const products = await fetchProducts();

    // Đọc tham số URL
    const urlParams = new URLSearchParams(window.location.search);
    const category = urlParams.get('category');
    const sortBy = urlParams.get('sort');

    let filteredProducts = products;

    // Lọc sản phẩm theo danh mục nếu có
    if (category) {
        filteredProducts = products.filter(product => product.categories.includes(category) || product.brandName === category);
    }

    // Sắp xếp sản phẩm nếu có tham số sort
    if (sortBy) {
        filteredProducts = sortProducts(filteredProducts, sortBy);
    }

    await showProductsInShopPage(filteredProducts);

    const categoryItems = document.querySelectorAll('.category-item a');
    categoryItems.forEach(item => {
        item.addEventListener('click', function(event) {
            event.preventDefault();
            const category = this.getAttribute('data-category');
            const filteredProducts = products.filter(product => product.categories.includes(category) || product.brandName === category);
            showProductsInShopPage(filteredProducts);
        });
    });

    const sortSelect = document.getElementById('sort');
    sortSelect.addEventListener('change', function() {
        const sortedProducts = sortProducts(filteredProducts, this.value);
        showProductsInShopPage(sortedProducts);
    });
});

// Show Products In Shop Page
async function showProductsInShopPage(products){
    const productsContainer = document.querySelector('.main #products-container');
    productsContainer.innerHTML = '';

    products.forEach(product => {
        const productItem = document.createElement('div');
        productItem.classList.add('product-item');

        price = product.productPrice * (100 - product.discountPercent) / 100;

        // Link ảnh tượng trưng
        productItem.innerHTML = `
            <a href="/public/products.html?${convertProductName(product.productName)}&id=${product.id}">
                <img alt="${product.productName}" src="${product.productImage}">
                <p class="product-name">${product.productName}</p>
                <p class="price">${formatNumber(price)} đ 
                    <span class="originPrice" style="text-decoration: line-through;">${formatNumber(product.productPrice)} đ</span>
                </p>
            </a>
        `;

        productsContainer.appendChild(productItem);
    });
    document.getElementById('productsCount').innerHTML = `Hiển thị ${products.length} sản phẩm`;
}

function sortProducts(products, sortBy) {
    switch (sortBy) {
        case 'low-to-high':
            return products.sort((a, b) => a.productPrice - b.productPrice);
        case 'high-to-low':
            return products.sort((a, b) => b.productPrice - a.productPrice);
        case 'popularity':
            return products.sort((a, b) => {
                if (b.averageRating === a.averageRating) {
                    return b.productPrice - a.productPrice;
                }
                return b.averageRating - a.averageRating;
            });
        default:
            return products;
    }
}

async function showAllProducts() {
    const products = await fetchProducts();
    showProductsInShopPage(products);
}
