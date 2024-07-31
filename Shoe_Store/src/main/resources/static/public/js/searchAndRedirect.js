document.addEventListener("DOMContentLoaded", function() {
    const searchBtn = document.getElementById('searchButton');
    if(searchBtn) {
        searchBtn.addEventListener('click', function() {
            this.parentElement.classList.toggle('open');
            this.previousElementSibling.focus();

            const keyword = document.getElementById('searchInput').value;

            if(keyword) {
                window.location.href = `/public/shop.html?keyword=${encodeURIComponent(keyword)}`;
            }
        })
    } else {
        console.log("not found search button")
    }
});
