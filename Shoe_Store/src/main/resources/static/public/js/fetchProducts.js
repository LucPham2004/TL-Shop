// Fetching products data
async function fetchProducts() {
    try {
        const response = await fetch('/api/v1/products');
        const products = await response.json();

        return products;

    } catch (error) {
        console.error('Error fetching products:', error);
    }
}

// Fetching TOP products
async function fetchTopProducts() {
    try {
        const response = await fetch('/api/v1/products/topproducts');
        const products = await response.json();

        return products;

    } catch (error) {
        console.error('Error fetching products:', error);
    }
}

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