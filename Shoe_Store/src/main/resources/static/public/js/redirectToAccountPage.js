function redirectToAccountPageWithJWT() {
    const jwtToken = localStorage.getItem('token');
    console.log(jwtToken);

    fetch('/user/account.html', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + jwtToken,
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            window.location.href = '/user/account.html';
            return response.text();
        }
        throw new Error('Network response was not ok.');
    })
    .catch(error => {
        console.error('There was a problem with your fetch operation:', error);
    });
}
