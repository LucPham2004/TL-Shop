function redirectToProfilePageWithJWT() {
    const jwtToken = localStorage.getItem('token');

    fetch('/user/profile.html', {
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
	.then(data => {
		document.body.innerHTML = data;
	})
	.catch(error => {
		console.error('There was a problem with the fetch operation:', error);
	});
}
