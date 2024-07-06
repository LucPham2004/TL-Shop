function redirectToAccountPageWithJWT() {
    const jwtToken = JSON.parse(localStorage.getItem('token')) || [];

    fetch('/user/account.html', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + jwtToken
        }
    })
    .then(response => {
        if (response.ok) {
            return response.text();
        }
        throw new Error('Network response was not ok.');
    })
    .then(html => {
        document.open();
        document.write(html);
        document.close();
    })
    .catch(error => {
        console.error('There was a problem with your fetch operation:', error);
    });
}
