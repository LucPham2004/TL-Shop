document.addEventListener('DOMContentLoaded', () => {
    const signupForm = document.querySelector('#signup_form');

    signupForm.addEventListener('submit', async (event) => {
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
            const signupResponse = await fetch('/api/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(signupData)
            });

            if (!signupResponse.ok) {
                const errorMessage = await signupResponse.text();
                throw new Error(errorMessage || 'Đăng ký thất bại');
            }

            alert('Đăng ký thành công!');

            if(window.location.href ==  '/register.html'){
                window.location.href = '/login.html';
            }

        } catch (error) {
            console.error('Có lỗi xảy ra:', error);
            alert('Đăng ký thất bại. Vui lòng kiểm tra lại thông tin.');
        }
    });
});
