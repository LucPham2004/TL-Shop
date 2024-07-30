document.addEventListener("DOMContentLoaded", function() {
    displayProducts();

    
    const productImages = document.querySelectorAll('.product-item img');
    productImages.forEach((img) => {
        const originSrc = img.src;
        const hoverSrc = createHoverSrc(originSrc);
        applyFadeEffect(img, hoverSrc, originSrc);
    });

});

// Display products in homepage
async function displayProducts() {
    const products = await fetchTopProducts();

    const topSellerProducts = products.slice(0, 4);
    const favoriteProducts = products.slice(4, 8);
    const onSaleProducts = products.slice(8, 16);
    const featureProducts = products.slice(16, 20);

    function createProductHTML(product) {
        return `
            <div class="product-item">
                <a href="/public/products.html?${convertProductName(product.productName)}&id=${product.id}">
                    <img alt="${product.productName}" src="${product.productImage}">
                    <p class="product-name">${product.productName}</p>
                    <p class="description">${product.categories}</p>
                    <p class="price">${formatNumber(product.productPrice)} đ</p>
                </a>
            </div>
        `;
    }

    function insertProducts(selector, products) {
        const container = document.querySelector(selector);
        container.innerHTML = products.map(createProductHTML).join('');
    }

    insertProducts('.top-seller-products', topSellerProducts);
    insertProducts('.favorite-products', favoriteProducts);
    insertProducts('.onSale-products', onSaleProducts);
    insertProducts('.feature-products', featureProducts);
}

// Effect for product images
function applyFadeEffect(imgElement, hoverSrc, originSrc) {
    imgElement.addEventListener('mouseover', function () {
        imgElement.classList.add('fade-out');
        setTimeout(() => {
            imgElement.src = hoverSrc;
            imgElement.classList.remove('fade-out');
        }, 200); 
    });

    imgElement.addEventListener('mouseout', function () {
        imgElement.classList.add('fade-out');
        setTimeout(() => {
            imgElement.src = originSrc;
            imgElement.classList.remove('fade-out');
        }, 200); 
    });
}

function createHoverSrc(originSrc) {
    const extensionIndex = originSrc.lastIndexOf('.');
    if (extensionIndex !== -1) {
        let fileName = originSrc.slice(0, extensionIndex);
        fileName = fileName.replace(/_1$/, '_2');
        return fileName + originSrc.slice(extensionIndex);
    }
    return originSrc;
}

