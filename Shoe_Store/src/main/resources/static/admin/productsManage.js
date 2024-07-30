
// Products Methods

// Search products
document.addEventListener("DOMContentLoaded", function() {
    const searchBtn = document.getElementById('searchButton');
    if(searchBtn) {
        document.getElementById('searchButton').addEventListener('click', function() {
            this.parentElement.classList.toggle('open');
            this.previousElementSibling.focus();

            const keyword = document.getElementById('searchInput').value;

            if(keyword) {
                fetch(`/api/v1/products/search?keyword=${encodeURIComponent(keyword)}`)
                .then(response => response.json())
                .then(data => {
                    showProductsInAdminPage(data);
                })
                .catch(error => {
                    console.error('Error fetching products:', error);
                });
            }
        })
    } else {
        console.log("not found search button")
    }
})
// Show Products In Admin Page
function showProductsInAdminPage(products){
    const tbody = document.querySelector('#product-table tbody');
    tbody.innerHTML = '';

    products.forEach(product => {
        const tr = document.createElement('tr');

        tr.innerHTML = `
            <td>${product.id}</td>
            <td>${product.productName}</td>
            <td>${formatNumber(product.productPrice)} đ</td>
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

// Creare product and upload images
document.getElementById('addproductForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const form = document.getElementById('addproductForm');
    const formData = new FormData(form);

    let productQuantity = 0;
    for (let i = 0; i < detailCount; i++) {
        const quantity = parseInt(formData.get(`details[${i}].quantity`));
        if (!isNaN(quantity)) {
            productQuantity += quantity;
        }
    }
    const productImages = document.getElementById('productImages').files;
    const FfirstImageFile = productImages[0];

    formData.set('productImage', '../public/img/products/' + convertProductName(formData.get('productName'))
                                    + "/" + `${convertProductName(formData.get('productName'))}_1.${FfirstImageFile.name.split('.').pop()}`);
    formData.set('productQuantity', productQuantity);
    formData.set('reviewCount', 0);
    formData.set('averageRating', 0);

    const productDTO = {
        productName: formData.get('productName'),
        productDescription: formData.get('productDescription'),
        productImage: formData.get('productImage'),
        productPrice: formData.get('productPrice'),
        productQuantity: productQuantity,
        discountPercent: formData.get('discountPercent'),
        reviewCount: 0,
        averageRating: 0,
        brandName: formData.get('productBrandName'),
        categories: formData.get('categories').split(',').map(item => item.trim()),
        details: []
    };

    for (let i = 0; i < detailCount; i++) {
        const detail = {
            color: formData.get(`details[${i}].color`),
            size: formData.get(`details[${i}].size`),
            quantity: formData.get(`details[${i}].quantity`)
        };
        productDTO.details.push(detail);
    }

    fetch('/api/v1/products', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(productDTO)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        if (productImages.length > 0) {
            const imageFormData = new FormData();
            imageFormData.append('productName', convertProductName(productDTO.productName));
            for (let i = 0; i < productImages.length; i++) {
                const originalFile = productImages[i];
                const newFileName = `${convertProductName(productDTO.productName)}_${i + 1}.${originalFile.name.split('.').pop()}`;
                const renamedFile = new File([originalFile], newFileName, { type: originalFile.type });
                imageFormData.append('productImages', renamedFile);
            }

            fetch('/api/v1/products/uploadImages', {
                method: 'POST',
                body: imageFormData
            })
            .then(imageResponse => {
                if (!imageResponse.ok) {
                    throw new Error('Network response was not ok ' + imageResponse.statusText);
                }
                return imageResponse.text();
            })
            .then(imageData => {
                alert('Sản phẩm và hình ảnh đã được tạo thành công');
                form.reset();
            })
            .catch(imageError => {
                console.error('There was a problem with your image upload operation:', imageError);
                alert('Đã xảy ra lỗi khi tải lên hình ảnh: ' + imageError.message);
            });
        } else {
            alert('Sản phẩm đã được tạo thành công');
            form.reset();
        }
    })
    .catch(error => {
        console.error('There was a problem with your fetch operation:', error);
        alert('Đã xảy ra lỗi khi tạo sản phẩm: ' + error.message);
    });
});

// Delete product
async function deleteProduct(id) {
    if(confirm("Bạn có chắc muốn xóa sản phẩm?")) {
        const response = await fetch(`/api/v1/products/${id}`, {
            method: 'DELETE',
        });

        if (response.ok) {
            const products = await fetchProducts();
            showProductsInAdminPage(products);
        } else {
            console.error('Failed to delete product');
        }
    } else {
        return
    }
}

let detailCount = 1;

function addDetail() {
    const detailsDiv = document.getElementById('productDetails');
    const newDetail = document.createElement('div');
    newDetail.classList.add('productDetail');
    newDetail.innerHTML = `
        <label for="detailColor${detailCount}">Màu Sắc:</label>
        <input type="text" id="detailColor${detailCount}" name="details[${detailCount}].color"><br>

        <label for="detailSize${detailCount}">Kích Thước:</label>
        <input type="number" id="detailSize${detailCount}" name="details[${detailCount}].size"><br>

        <label for="detailQuantity${detailCount}">Số Lượng:</label>
        <input type="number" id="detailQuantity${detailCount}" name="details[${detailCount}].quantity"><br>
    `;
    detailsDiv.appendChild(newDetail);
    detailCount++;
}