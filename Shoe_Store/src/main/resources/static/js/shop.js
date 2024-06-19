// Show Products In Shop Page
function showProductsInShopPage(products){
    const productsContainer = document.querySelector('.Products .products-container');

    products.forEach(product => {
        const productItem = document.createElement('div');
        productItem.classList.add('product-item');

        // Link ảnh tượng trưng, phải chỉnh lại
        productItem.innerHTML = `
            <img alt="${product.productName}" src="../img/homepage/favorite/favorite1.png">
            <p class="product-name">${product.productName}</p>
            <p class="description">${product.categories}</p>
            <p class="price">${product.productPrice}đ</p>
        `;

        productsContainer.appendChild(productItem);
    });
}