
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
                document.querySelector(".main-container .product-name").textContent = product.productName;
                document.querySelector(".main-container .Type").textContent = product.categories; 
                document.querySelector(".main-container .star span").textContent = product.reviewCount;
                document.querySelector(".main-container .price").textContent = formatNumber(product.productPrice) + " đ";
                document.querySelector(".main-container .description").innerHTML = `
                    <h6>Mô Tả Sản Phẩm:</h6>
                    <ul>
                        <li>${product.productDescription}</li>
                        <!-- Add more description if available -->
                    </ul>
                `;

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
            });
    } else {
        document.querySelector(".main-container").textContent = "No product selected";
    }
});

document.addEventListener('DOMContentLoaded', () => {
    fetch('/api/v1/products')
        .then(response => response.json())
        .then(data => {
            const similarProductsTop = document.getElementById('similar-products-top');
            const carouselInner = document.getElementById('carousel-inner');
            let carouselItems = '';
            let activeSet = false;

            data.forEach((product, index) => {
                const productHTML = `
                    <div class="similar-item">
                        <img alt="Giày similar" src="./img/homepage/favorite/favorite1.png">
                        <p class="product-name">${product.productName}</p>
                        <p class="description">${product.productDescription}</p>
                        <p class="price">${formatNumber(product.productPrice)} đ</p>
                    </div>
                `;

                if (index < 4) {
                    similarProductsTop.innerHTML += productHTML;
                }

                if (index % 4 === 0) {
                    if (index > 0) {
                        carouselItems += '</div>';
                    }
                    carouselItems += `<div class="carousel-item ${!activeSet ? 'active' : ''}">
                        <div class="similar-products">`;
                    activeSet = true;
                }

                carouselItems += productHTML;

                if (index === data.length - 1) {
                    carouselItems += '</div></div>';
                }
            });

            carouselInner.innerHTML = carouselItems;
        })
        .catch(error => console.error('Error fetching products:', error));
});

function formatNumber(number) {
    return number.toLocaleString('vi-VN');
}

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
            reviewRating: rating,
            productId: parseInt(productId),
            customerId: customerId
        }
        
        try {
            const response = fetch('/api/v1/reviews', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(reviewData)
            });

            reviewForm.reset();
            displayReviews(productId);
            window.alert('Đánh giá thành công!')

        } catch (error) {
            console.error('Error while creating review: ' + error);
        }
        
    });

});

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
                        <img class="user-icon" alt="user-icon" src="./img/logo/user.png">
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
            <img class="user-icon" alt="user-icon" src="./img/logo/user.png">
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