

// Display Product Infomation
document.addEventListener("DOMContentLoaded", function() {
    const params = new URLSearchParams(window.location.search);
    const productId = params.get("id");
    
    if (productId) {
        fetch("/api/v1/products/" + productId)
        .then(response => {
            if (!response.ok) {
                throw new Error('Product not found');
            }
            return response.json();
        })
        .then(product => {
            console.log(product);

            price = product.productPrice * (100 - product.discountPercent) / 100;

            document.querySelector(".main-container .product-name").textContent = product.productName;
            document.querySelector(".main-container .Type").textContent = product.categories;
            document.querySelector(".main-container .avgRating .rating").textContent = product.averageRating ;
            document.querySelector(".main-container .productReview .reviewCount").textContent = product.reviewCount + " đánh giá";
            document.querySelector(".main-container .price").textContent = formatNumber(price) + " đ";
            document.querySelector(".main-container .originPrice").textContent = formatNumber(product.productPrice) + " đ";
            document.querySelector(".main-container .description").innerHTML = `
                <h6>Mô Tả Sản Phẩm:</h6>
                <ul>
                    <li>${product.productDescription}</li>
                    <!-- Add more description if available -->
                </ul>
            `;

            const colorSelect = document.getElementById('color');
            const sizeSelect = document.getElementById('size');

            colorSelect.innerHTML = '';
            sizeSelect.innerHTML = '';

            let colors = new Set();
            product.details.forEach(detail => {
                colors.add(detail.color);
            });

            colors.forEach(color => {
                let option = document.createElement('option');
                option.value = color;
                option.textContent = color;
                colorSelect.appendChild(option);
            });

            const updateSizeOptions = (selectedColor) => {
                sizeSelect.innerHTML = '';
                let sizes = new Set();
                product.details.forEach(detail => {
                    if (detail.color === selectedColor) {
                        sizes.add(detail.size);
                    }
                });
                sizes.forEach(size => {
                    let option = document.createElement('option');
                    option.value = size;
                    option.textContent = size;
                    sizeSelect.appendChild(option);
                });
            };

            if (colorSelect.value) {
                updateSizeOptions(colorSelect.value);
            }

            colorSelect.addEventListener('change', (event) => {
                updateSizeOptions(event.target.value);
            });

            // const picsContainer = document.querySelector(".pics");
            // picsContainer.innerHTML = ''; 
            // product.images.forEach(image => {
            //     const imgElement = document.createElement("img");
            //     imgElement.src = image;
            //     imgElement.alt = "products pictures";
            //     picsContainer.appendChild(imgElement);
            // });
        })
        .catch(error => {
            document.querySelector(".main-container").textContent = "Product not found. Error: " + error;
            console.error(error);
        });
    } else {
        document.querySelector(".main-container").textContent = "No product selected";
    }
});

// Display Similar products
document.addEventListener('DOMContentLoaded', () => {
    fetch('/api/v1/products')
        .then(response => response.json())
        .then(data => {
            const similarProductsTop = document.getElementById('similar-products-top');
            const carouselInner = document.getElementById('carousel-inner');
            let topProductsHTML = '';

            const firstCarouselItem = carouselInner.querySelector('.carousel-item:nth-child(1)');
            const secondCarouselItem = carouselInner.querySelector('.carousel-item:nth-child(2)');

            let carouselItemInnerContainer1 = document.createElement('div');
            carouselItemInnerContainer1.classList.add('carousel-item-innerContainer');

            let carouselItemInnerContainer2 = document.createElement('div');
            carouselItemInnerContainer2.classList.add('carousel-item-innerContainer');

            data.forEach((product, index) => {
                // Tạo một div mới để chứa thông tin sản phẩm
                let productContainer = document.createElement('div');
                productContainer.classList.add('similar-item');

                price = product.productPrice * (100 - product.discountPercent) / 100;

                // Thiết lập nội dung cho sản phẩm
                productContainer.innerHTML = `
                    <img alt="Giày similar" src="${product.productImage}">
                    <p class="product-name">${product.productName}</p>
                    <p class="description">${product.productDescription}</p>
                    <p class="price">${formatNumber(price)} đ
                        <span class="originPrice" style="text-decoration: line-through;">${formatNumber(product.productPrice)} đ</span>
                    </p>
                `;

                if (index < 4) {
                    topProductsHTML += productContainer.outerHTML;
                } else if (index >= 4 && index < 8) {
                    carouselItemInnerContainer1.appendChild(productContainer);
                } else if (index >= 8 && index < 12) {
                    carouselItemInnerContainer2.appendChild(productContainer);
                }
            });

            similarProductsTop.innerHTML = topProductsHTML;
            firstCarouselItem.appendChild(carouselItemInnerContainer1);
            secondCarouselItem.appendChild(carouselItemInnerContainer2);

        })
        .catch(error => console.error('Error fetching products:', error));
});



// New Review Form
document.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    const productId = params.get("id");
    displayReviews(productId);
    const customerId = getCustomerId();
    const reviewForm = document.getElementById('reviewForm');

    reviewForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const reviewTitle = document.getElementById('reviewTitle').value;
        const rating = document.getElementById('rating').value;
        const comment = document.getElementById('comment').value;

        const reviewData = {
            reviewTitle: reviewTitle,
            reviewContent: comment,
            reviewDate: new Date(),
            reviewRating: parseInt(rating),
            productId: parseInt(productId),
            customerId: customerId
        }
        console.log(reviewData);
        
        try {
            const response = fetch('/api/v1/reviews', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(reviewData)
            });

            reviewForm.reset();
            
            window.alert('Đánh giá thành công!');
            displayReviews();

        } catch (error) {
            console.error('Error while creating review: ' + error);
        }
        
    });
});

// Display Reviews
async function displayReviews(productId) {
    try {
        const response = await fetch('/api/v1/reviews/product/' + parseInt(productId));
        const reviews = await response.json();

        const reviewContainer = document.querySelector('#collapseReview .card-body');
        reviewContainer.innerHTML = '';

        if(reviews.length == 0) {
            reviewContainer.innerHTML = 'Chưa có bình luận, đánh giá nào về sản phẩm này.'
        } else {
            reviews.forEach(review => {
                const reviewItem = document.createElement('div');
                reviewItem.classList.add(`review-item-${review.id}`);
        
                reviewItem.innerHTML = `
                    <div class="review-info">
                        <img class="user-icon" alt="user-icon" src="../public/img/logo/user.png">
                        <p class="customer-name">${review.customerName}</p>
                        <p class="review-date">${extractDate(review.reviewDate)}</p>
                        <p class="star" data-rating=${review.reviewRating}>${displayStars(review.reviewRating)}</p>
                        <span class="editReview" onclick="editReview(${review.id})">Sửa</span>
                        <span class="deleteReview" onclick="deleteReview(${review.id})">Xóa</span>
                    </div>
                    <div>
                        <h6 class="review-title">${review.reviewTitle}</h6>
                        <p class="review-content">${review.reviewContent}</p>
                    </div>
                    <div style="display: flex;justify-content:center;"><hr style="width:80%;"></div>
                `;

                reviewContainer.appendChild(reviewItem);
            });
        }

    } catch (error) {
        console.error('Error fetching reviews:', error);
    }
}

// Edit Review
function editReview(reviewId) {
    const reviewItem = document.querySelector(`.review-item-${reviewId}`);

    if (!reviewItem) {
        console.error(`Review item with ID ${reviewId} not found`);
        return;
    }

    const reviewTitle = reviewItem.querySelector('.review-title').innerText;
    const reviewContent = reviewItem.querySelector('.review-content').innerText;
    const reviewRating = reviewItem.querySelector('.star').getAttribute('data-rating');

    reviewItem.innerHTML = `
        <div class="review-info">
            <img class="user-icon" alt="user-icon" src="../public/img/logo/user.png">
            <p class="customer-name">${reviewItem.querySelector('.customer-name').innerText}</p>
            <p class="review-date">${reviewItem.querySelector('.review-date').innerText}</p>
            <input type="number" class="star" value="${reviewRating}" min="1" max="5">
        </div>
        <div>
            <input type="text" class="review-title" value="${reviewTitle}">
            <textarea class="review-content">${reviewContent}</textarea>
            <button class="saveReview" onclick="saveReview(${reviewId})">Lưu</button>
        </div>
        <div style="display: flex;justify-content:center;"><hr style="width:80%;"></div>
    `;
}

// Save edited review
function saveReview(reviewId) {
    const reviewItem = document.querySelector(`.review-item-${reviewId}`);
    const updatedReview = {
        id: reviewId,
        reviewDate: reviewItem.querySelector('.review-date').innerText,
        reviewRating: reviewItem.querySelector('.star').value,
        reviewTitle: reviewItem.querySelector('.review-title').value,
        reviewContent: reviewItem.querySelector('.review-content').value
    };
    try {
        fetch('/api/v1/reviews', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedReview)
        })
            alert('Cập nhật đánh giá thành công!');
            location.reload();
            
    } catch(error) {
        console.error('Error:', error);
        alert('Có lỗi xảy ra. Vui lòng thử lại.');
    };
}

// Delete review
async function deleteReview(id) {
    if(confirm("Bạn có chắc muốn xóa bình luận đánh giá này?")) {
        const response = await fetch(`/api/v1/reviews/${id}`, {
            method: 'DELETE',
        });

        if (response.ok) {
            location.reload();
        } else {
            console.error('Failed to delete product');
        }
    } else {
        return
    }
}

// Small helping functions
document.getElementById('increase').addEventListener('click', function() {
    const quantityInput = document.getElementById('quantity');
    let currentValue = parseInt(quantityInput.value);
    quantityInput.value = currentValue + 1;
});

document.getElementById('decrease').addEventListener('click', function() {
    const quantityInput = document.getElementById('quantity');
    let currentValue = parseInt(quantityInput.value);
    if (currentValue > 1) {
        quantityInput.value = currentValue - 1;
    }
});

function extractDate(datetimeString) {
    let datePart = datetimeString.split('T')[0];
    
    return datePart;
}

function displayStars(rating) {
    let stars = "";

    for (let i = 0; i < rating; i++) {
        stars += "★";
    }

    for (let i = rating; i < 5; i++) {
        stars += "☆";
    }

    return stars;
}

function formatNumber(number) {
    return number.toLocaleString('vi-VN');
}