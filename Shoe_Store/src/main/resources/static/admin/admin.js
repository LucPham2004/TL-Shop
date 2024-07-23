// Show shop data from server
document.addEventListener("DOMContentLoaded", async function() {
    const products = await fetchProducts();
    showProductsInAdminPage(products);

    const brands = await fetchBrandData();
    showBrandsInAdminPage(brands);

    const categories = await fetchCategoryData();
    showCategoriesInAdminPage(categories);

    const customers = await fetchCustomers();
    showCustomersInAdminPage(customers);
});

// Change banners
async function changeBanner() {
    const input = document.getElementById('selectBanner');
    const files = input.files;
    const formData = new FormData();

    for (let i = 0; i < files.length; i++) {
        formData.append('files', files[i]);
    }

    const response = await fetch('/admin/api/managehomepage/changeBanners', {
        method: 'POST',
        body: formData
    });

    if (!response.ok) {
        throw new Error('Failed to change banners');
    }

    return response.text();
}

async function updateBannerImages() {
    try {
        const fileNamesResponse = await changeBanner();
        const fileList = fileNamesResponse.split(" ");
        const bannerCarousel = document.querySelector('.banner .carousel-inner');
        
        bannerCarousel.innerHTML = `
            <div class="carousel-item active">
                <img id="banner1" src="../public/img/homepage/banner/${fileList[0]}" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img id="banner2" src="../public/img/homepage/banner/${fileList[1]}" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img id="banner3" src="../public/img/homepage/banner/${fileList[2]}" class="d-block w-100" alt="...">
            </div>
        `;
    } catch (error) {
        console.error('Error updating banners:', error);
    }
}

// Change posters
async function changePoster() {
    const input = document.getElementById('selectPoster');
    const files = input.files;
    const formData = new FormData();

    for (let i = 0; i < files.length; i++) {
        formData.append('files', files[i]);
    }

    const response = await fetch('/admin/api/managehomepage/changeposters', {
        method: 'POST',
        body: formData
    });

    if (!response.ok) {
        throw new Error('Failed to change Posters');
    }

    return response.text();
}

async function updatePosterImages() {
    try {
        const fileNamesResponse = await changePoster();
        const fileList = fileNamesResponse.split(" ");
        const bannerCarousel = document.querySelector('.poster-main');
        
        bannerCarousel.innerHTML = `
            <div class="poster-main-left">
                <a href="#" style="text-decoration: none; color: black">
                    <img id="poster-main" src="../public/img/homepage/poster/${fileList[0]}" class="card-img-top" alt="...">
                </a>
            </div>
            <div class="poster-main-right">
                <div class="poster-main-right-div" style="margin-bottom: 10px;">
                    <a href="#" style="text-decoration: none; color: black">
                        <img id="poster2" src="../public/img/homepage/poster/${fileList[1]}" alt="...">
                    </a>
                </div>
                <div class="poster-main-right-div">
                    <a href="#" style="text-decoration: none; color: black">
                        <img id="poster3" src="../public/img/homepage/poster/${fileList[2]}" alt="...">
                    </a>
                </div>
            </div>
            <div class="changePoster">
                <h3>Thay đổi Poster</h3>
                <label for="#selectPoster">Lựa chọn tấm banPosterner</label>
                <input type="file" id="selectPoster" accept="image/*" multiple>
                <button onclick="updatePosterImages()">Update Poster</button>
            </div>
        `;
    } catch (error) {
        console.error('Error updating banners:', error);
    }
}

// Change Product Samples

async function changeSampleImage(newImage, index) {
    const formData = new FormData();
    formData.append('files', newImage);

    const response = await fetch(`/admin/api/managehomepage/changesample${index}`, {
        method: 'POST',
        body: formData
    });

    if (!response.ok) {
        throw new Error('Failed to change Posters');
    }

    return response.text();
}

// Sample 1
function changeSample1() {
    const sampleContent = document.querySelector('#sample-product-1 .sample-content');
    const title = document.getElementById('sampleTitle1').textContent;
    const description = document.getElementById('sampleDescription1').textContent;

    sampleContent.innerHTML = `
        <input type="text" id="newTitle1" value="${title}">
        <input type="text" id="newDescription1" value="${description}">
        <input type="file" id="newImage1" accept="image/*">
        <button onclick="applyChanges1()">Áp dụng</button>
        <button onclick="cancelChanges1()">Hủy</button>
    `;
}

function applyChanges1() {
    const newTitle = document.getElementById('newTitle1').value;
    const newDescription = document.getElementById('newDescription1').value;
    const newImage = document.getElementById('newImage1').files[0];
    const sampleContent = document.querySelector('#sample-product-1 .sample-content');
    
    if (newImage) {
        changeSampleImage(newImage, 1);
    }

    sampleContent.innerHTML = `
        <h3 id="sampleTitle1">${newTitle}</h3>
        <i></i>
        <p id="sampleDescription1" class="description">${newDescription}</p>
        <a href="shop.html"><button class="shopNow-btn">Shop Ngay!</button></a>
    `;
}

function cancelChanges1() {
    const sampleContent = document.querySelector('#sample-product-1 .sample-content');
    const title = document.getElementById('newTitle1').value;
    const description = document.getElementById('newDescription1').value;

    sampleContent.innerHTML = `
        <h3 id="sampleTitle1">${title}</h3>
        <i></i>
        <p id="sampleDescription1" class="description">${description}</p>
        <a href="shop.html"><button class="shopNow-btn">Shop Ngay!</button></a>
    `;
}

// Sample2
function changeSample2() {
    const sampleContent = document.querySelector('#sample-product-2 .sample-content');
    const title = document.getElementById('sampleTitle2').textContent;
    const description = document.getElementById('sampleDescription2').textContent;

    sampleContent.innerHTML = `
        <input type="text" id="newTitle2" value="${title}">
        <input type="text" id="newDescription2" value="${description}">
        <input type="file" id="newImage2" accept="image/*">
        <button onclick="applyChanges2()">Áp dụng</button>
        <button onclick="cancelChanges2()">Hủy</button>
    `;
}

function applyChanges2() {
    const newTitle = document.getElementById('newTitle2').value;
    const newDescription = document.getElementById('newDescription2').value;
    const newImage = document.getElementById('newImage2').files[0];
    const sampleContent = document.querySelector('#sample-product-2 .sample-content');
    
    if (newImage) {
        changeSampleImage(newImage, 2);
    }

    sampleContent.innerHTML = `
        <h3 id="sampleTitle2">${newTitle}</h3>
        <i></i>
        <p id="sampleDescription2" class="description">${newDescription}</p>
        <a href="shop.html"><button class="shopNow-btn">Shop Ngay!</button></a>
    `;
}

function cancelChanges2() {
    const sampleContent = document.querySelector('#sample-product-2 .sample-content');
    const title = document.getElementById('newTitle2').value;
    const description = document.getElementById('newDescription2').value;

    sampleContent.innerHTML = `
        <h3 id="sampleTitle2">${title}</h3>
        <i></i>
        <p id="sampleDescription1" class="description">${description}</p>
        <a href="shop.html"><button class="shopNow-btn">Shop Ngay!</button></a>
    `;
}