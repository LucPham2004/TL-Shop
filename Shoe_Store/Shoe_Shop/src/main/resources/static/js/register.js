document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('.signup_form');

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        const fullName = document.querySelector('#fullName').value;
        const email = document.querySelector('#email').value;
        const phone = document.querySelector('#phone').value;
        const password = document.querySelector('#password').value;

        const signupData = {
            name: fullName,
            email: email,
            phone: phone,
            password: password
        };

        try {
            const response = await fetch('/api/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(signupData)
            });

            if (!response.ok) {
                const errorMessage = await response.text();
                throw new Error(errorMessage || 'Đăng ký thất bại');
            }

            alert('Đăng ký thành công!');

            window.location.href = '/login.html';

        } catch (error) {
            console.error('Có lỗi xảy ra:', error);
            alert('Đăng ký thất bại. Vui lòng kiểm tra lại thông tin.');
        }
    });
});
