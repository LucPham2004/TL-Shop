// Hàm để lấy giá trị của một cookie dựa vào tên
function getCookie(name) {
    let cookieArr = document.cookie.split(";");
    
    // Duyệt qua từng cặp cookie
    for(let i = 0; i < cookieArr.length; i++) {
        let cookiePair = cookieArr[i].split("=");
        
        // Loại bỏ khoảng trắng ở đầu và cuối tên cookie và so sánh với tên đã cho
        if(name == cookiePair[0].trim()) {
            // Trả về giá trị của cookie
            return decodeURIComponent(cookiePair[1]);
        }
    }
    
    // Nếu không tìm thấy cookie thì trả về null
    return null;
}

// Kiểm tra xem người dùng đã đăng nhập hay chưa
function checkLogin() {
    let userLoggedIn = getCookie("userLoggedIn");
    
    if(userLoggedIn === "true") {
        return true
    } else {
        return false
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const accountDiv = document.querySelector('.account');

    const isLoggedIn = checkLogin();

    if (isLoggedIn) {
        // Hiển thị phần dropdown
        accountDiv.innerHTML = `
            <div class="dropdown">
                <a class="dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                    <i class="fas fa-user-circle"></i> Account
                </a>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="./account.html">Profile</a></li>
                    <li><a class="dropdown-item" href="#">Đăng xuất<i class="fas fa-arrow-circle-right" style="margin-left: 15px;"></i></a></li>
                </ul>
            </div>
        `;
    } else {
        // Hiển thị nút đăng nhập
        accountDiv.innerHTML = `
            <a href="./login.html">
                <div id="login" style="width:max-content">
                    <i class="fas fa-user" style="margin-right: 10px"></i>
                    Log in | Sign up
                </div>
            </a>
        `;
    }
});