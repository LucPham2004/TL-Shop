function redirectToAccountPageWithJWT() {
    const jwtToken = localStorage.getItem('token');
    console.log(jwtToken);

    fetch('/user/account.html', {
        method: 'GET',
        headers: {
          'Authorization': 'Bearer ' + jwtToken
        }
      })
      .then(response => {
        if (response.ok) {
          return response.text(); // Hoặc response.json() nếu bạn nhận JSON
        }
        throw new Error('Network response was not ok.');
      })
      .then(data => {
        document.body.innerHTML = data; // Hiển thị nội dung của trang account.html
      })
      .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
      });
}
