document.addEventListener("DOMContentLoaded", function() {
    // Thêm phần header và cart vào đầu body
    const headerHTML = `
    <header>
        <div class="header-container">
            <a href="./index.html"><div id="logo"><img src="./img/logo/TL.png">TL SHOP</div></a>
            <div class="header-menu">
                <a href="./index.html"><div class="header-item">Home</div></a>
                <a href="./shop.html"><div class="header-item">Cửa Hàng</div></a>
                <h5 style="margin-bottom: 15px;align-self:center; color:rgb(89, 89, 89);">|</h5>
                <a href="./shop.html"><div class="header-item">Adidas</div></a>
                <a href="./shop.html"><div class="header-item">Nike</div></a>
                <a href="./shop.html"><div class="header-item">Thể Thao</div></a>
                <a href="./shop.html"><div class="header-item">Giày Da</div></a>
            </div>
            <div class="header-rightPart">
                <div class="search-box">
                    <input type="text" class="search-input" placeholder="seach..."/>
                    <button class="search-btn" title="search" type="button">
                        <i class="fas fa-search"></i>
                    </button>
                </div>

                <div type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling" class="cart" style="text-align: center;">
                    <i class="fas fa-cart-plus" style="font-size: 30px;"></i>
                </div>

                <div class="account">
                    <a href="./login.html"><div id="login" style="width:max-content">
                        <i class="fas fa-user" style="margin-right: 10px"></i>
                        Log in | Sign up</div>
                    </a>
                </div>
            </div>
        </div>
    </header>
    
    <!-- Cart -->
    <div class="offcanvas offcanvas-end" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1" id="offcanvasScrolling" aria-labelledby="offcanvasScrollingLabel">
        <div class="offcanvas-header">
            <h5 class="offcanvas-title" id="offcanvasScrollingLabel">Giỏ Hàng</h5>
            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">
            <div class="cart-items">
                <div class="cart-item">
                    <img alt="Giày similar" src="./img/homepage/favorite/favorite1.png">
                    <div class="item-info">
                        <p class="product-name">STAN SMITH SHOES</p>
                        <p class="description">Women's Shoes</p>
                        <p class="price">3.350.000đ</p>
                    </div>
                </div>
                <div class="cart-item">
                    <img alt="Giày similar" src="./img/homepage/favorite/favorite1.png">
                    <div class="item-info">
                        <p class="product-name">STAN SMITH SHOES</p>
                        <p class="description">Women's Shoes</p>
                        <p class="price">3.350.000đ</p>
                    </div>
                </div>
                <div class="cart-item">
                    <img alt="Giày similar" src="./img/homepage/favorite/favorite1.png">
                    <div class="item-info">
                        <p class="product-name">STAN SMITH SHOES</p>
                        <p class="description">Women's Shoes</p>
                        <p class="price">3.350.000đ</p>
                    </div>
                </div>
                <div class="cart-item">
                    <img alt="Giày similar" src="./img/homepage/favorite/favorite1.png">
                    <div class="item-info">
                        <p class="product-name">STAN SMITH SHOES</p>
                        <p class="description">Women's Shoes</p>
                        <p class="price">3.350.000đ</p>
                    </div>
                </div>
                <div class="cart-item">
                    <img alt="Giày similar" src="./img/homepage/favorite/favorite1.png">
                    <div class="item-info">
                        <p class="product-name">STAN SMITH SHOES</p>
                        <p class="description">Women's Shoes</p>
                        <p class="price">3.350.000đ</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    `;

    const footerHTML = `
    <!-- Button scroll to top -->
    <button onclick="scrollToTop()" id="scrollToTopBtn" title="Go to top">
        <span class="fas fa-arrow-circle-up"></span>
    </button>

    <!-- Footer -->
    <footer>
        <div style="background-color: black;display:flex;justify-content:center;margin-top:5%;">
            <div id="a-bar">
                <p>Rất nhiều sản phẩm bạn có thể lựa chọn</p>
                <a href="shop.html"><button class="shopNow-btn">Xem Tất Cả Sản Phẩm</button></a>
            </div>
        </div>
        <div class="container-fluid p-0">
            <div class="footer-top">
                <div style="display: flex;flex-direction:row;gap:20px 0;width:100%">
                    <div class="footer-links">
                        <a href="">Help</a>
                        <a href="">Contact Us</a>
                        <a href="">FAQ</a>
                        <a href="">Size Guide</a>
                        <a href="">Find a Store</a>
                    </div>
                    <div class="footer-links">
                        <a href="">Shop</a>
                        <a href="">Order Status</a>
                        <a href="">Shipping Information</a>
                        <a href="">Returns</a>
                    </div>
                    <div class="footer-links">
                        <a href="">About Us</a>
                        <a href="">For You</a>
                        <a href="">My Account</a>
                    </div>
                </div>
                <p>We stand for something bigger than sneakers. We champion those who are fearlessly driven by their passions. We elevate sport. We do right by people and the planet. Together, we drive meaningful change in communities around the world. We Got Now.</p>
            </div>
            <div class="footer-bottom">
                <a href="">MY</a>
                <a href="">Pivacy Policy</a>
                <a href="">Website Terms & Conditions</a>
                <p>Pham Tien Luc - No: 0383132114 - Copyright - 2024</p>
            </div>
        </div>
    </footer>
    `;

    // Thêm HTML vào body
    document.body.insertAdjacentHTML("afterbegin", headerHTML);
    document.body.insertAdjacentHTML("beforeend", footerHTML);

    const searchButton = document.querySelector('.search-btn');
    if (searchButton) {
        searchButton.addEventListener('click', function(){
            this.parentElement.classList.toggle('open');
            this.previousElementSibling.focus();
        });
    }
});
