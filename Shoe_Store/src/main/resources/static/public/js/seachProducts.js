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
                    showProductsInShopPage(data);
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
