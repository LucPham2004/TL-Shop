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
