
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

// document.addEventListener('DOMContentLoaded', function() {
//     const stars = document.querySelectorAll('.star');
//     stars.forEach(star => {
//         star.addEventListener('click', setRating);
//         star.addEventListener('mouseover', hoverRating);
//         star.addEventListener('mouseout', resetRating);
//     });

//     function setRating(event) {
//         const selectedValue = event.target.getAttribute('data-value');
//         stars.forEach(star => {
//             star.classList.remove('selected');
//             if (star.getAttribute('data-value') <= selectedValue) {
//                 star.classList.add('selected');
//             }
//         });
//     }

//     function hoverRating(event) {
//         const hoverValue = event.target.getAttribute('data-value');
//         stars.forEach(star => {
//             star.classList.remove('hover');
//             if (star.getAttribute('data-value') <= hoverValue) {
//                 star.classList.add('hover');
//             }
//         });
//     }

//     function resetRating() {
//         stars.forEach(star => {
//             star.classList.remove('hover');
//         });
//     }
// });
