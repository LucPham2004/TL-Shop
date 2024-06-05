// Hiệu ứng thanh Tìm kiếm
document.querySelector('.search-btn').addEventListener('click', function(){
    this.parentElement.classList.toggle('open')
	this.previousElementSibling.focus()
})

// Hàm Hiệu ứng cho các ảnh sản phẩm
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
    // Tìm vị trí của ".png"
    const extensionIndex = originSrc.lastIndexOf('.png');
    // Nếu tìm thấy ".png", thêm "-2" trước ".png"
    if (extensionIndex !== -1) {
        return originSrc.slice(0, extensionIndex) + '-2' + originSrc.slice(extensionIndex);
    }
    // Trả về originSrc nếu không tìm thấy ".png"
    return originSrc;
}

const topSellerImages = document.querySelectorAll('.top-seller-item img');
topSellerImages.forEach((ts_img) => {
    const ts_originSrc = ts_img.src;
    const ts_hoverSrc = createHoverSrc(ts_originSrc);
    applyFadeEffect(ts_img, ts_hoverSrc, ts_originSrc);
});

const favoriteImages = document.querySelectorAll('.favorite-item img');
favoriteImages.forEach((fav_img) => {
    const fav_originSrc = fav_img.src;
    const fav_hoverSrc = createHoverSrc(fav_originSrc);
    applyFadeEffect(fav_img, fav_hoverSrc, fav_originSrc);
});

const onSaleImages = document.querySelectorAll('.onSale-item img');
onSaleImages.forEach((os_img) => {
    const os_originSrc = os_img.src;
    const os_hoverSrc = createHoverSrc(os_originSrc);
    applyFadeEffect(os_img, os_hoverSrc, os_originSrc);
});

const featureImages = document.querySelectorAll('.feature-item img');
featureImages.forEach((fe_img) => {
    const fe_originSrc = fe_img.src;
    const fe_hoverSrc = createHoverSrc(fe_originSrc);
    applyFadeEffect(fe_img, fe_hoverSrc, fe_originSrc);
});

const similarImages = document.querySelectorAll('.similar-item img');
similarImages.forEach((sm_img) => {
    const sm_originSrc = sm_img.src;
    const sm_hoverSrc = createHoverSrc(sm_originSrc);
    applyFadeEffect(sm_img, sm_hoverSrc, sm_originSrc);
});

