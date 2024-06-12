document.addEventListener('DOMContentLoaded', () => {
	const form = document.querySelector('.login-form');

	form.addEventListener('submit', async (event) => {
		event.preventDefault();

		const email = document.querySelector('#email').value;
		const password = document.querySelector('#password').value;

		const loginData = {
			email: email,
			password: password
		};

		try {
			const response = await fetch('/api/auth/login', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(loginData)
			});

			if (!response.ok) {
				throw new Error('Đăng nhập thất bại');
			}

			document.cookie = "userLoggedIn=true;path=/";
			
			alert('Đăng nhập thành công!');

			window.location.href = '/index.html';

		} catch (error) {
			console.error('Có lỗi xảy ra:', error);
			alert('Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin.');
		}
	});
});
