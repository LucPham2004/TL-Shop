function getCookie(name) {
    let cookieArr = document.cookie.split(";");
    
    for(let i = 0; i < cookieArr.length; i++) {
        let cookiePair = cookieArr[i].split("=");
        
        if(name == cookiePair[0].trim()) {
            return decodeURIComponent(cookiePair[1]);
        }
    }
    
    return null;
}

// Check if user logged in or not
function checkLogin() {
    let userLoggedIn = getCookie("userLoggedIn");
    
    if(userLoggedIn === "true") {
        return true
    } else {
        return false
    }
}

// Get user's id
function getCustomerId() {
    return parseInt(getCookie("id"));
}

// Log out
async function logout() {
    try {
        const response = await fetch('/api/auth/logout', {
            method: 'POST', 
            headers: {
                'Content-Type': 'application/json', 
            },
        });

        if (!response.ok) {
            throw new Error('Đăng xuất thất bại');
        }
        
        alert('Đăng xuất thành công!');

        window.location.href = '/index.html';

    } catch (error) {
        console.error('Có lỗi xảy ra:', error);
        alert('Đăng xuất thất bại.');
    }
}
