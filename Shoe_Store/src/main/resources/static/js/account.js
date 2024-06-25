async function fetchCustomerInfo() {
    try {
        const response = await fetch('/api/v1/customers/' + getCustomerId());
        const customerInfo = await response.json();

        const nameSpan = document.getElementById('name');
        const emailSpan = document.getElementById('email');
        const phoneSpan = document.getElementById('phone');
        const addressSpan = document.getElementById('address');

        nameSpan.innerHTML = `${customerInfo.name}`;
        emailSpan.innerHTML = `${customerInfo.email}`;
        phoneSpan.innerHTML = `${customerInfo.phone}`;
        addressSpan.innerHTML = `${customerInfo.address}`;

    } catch (error) {
        console.error('Error fetching customer infomation:', error);
    }
}

document.addEventListener('DOMContentLoaded', function() {
    fetchCustomerInfo();

    const changeInfo = document.querySelector('.user-info');
    const changeInfoBtn = document.querySelector('.changeInfo-btn');
    const editForms = document.getElementById('editForms');
    const userInfoForm = document.getElementById('userInfoForm');
    const nameInput = document.getElementById('nameInput');
    const emailInput = document.getElementById('emailInput');
    const phoneInput = document.getElementById('phoneInput');
    const addressInput = document.getElementById('addressInput');

    const nameSpan = document.getElementById('name');
    const emailSpan = document.getElementById('email');
    const phoneSpan = document.getElementById('phone');
    const addressSpan = document.getElementById('address');

    changeInfoBtn.addEventListener('click', function() {
        editForms.classList.remove('hidden');
        changeInfo.classList.add('hidden');

        // Populate input fields with current info
        nameInput.value = nameSpan.textContent;
        emailInput.value = emailSpan.textContent;
        phoneInput.value = phoneSpan.textContent;
        addressInput.value = addressSpan.textContent;
    });

    userInfoForm.addEventListener('submit', function(event) {
        event.preventDefault();

        // Update info
        nameSpan.textContent = nameInput.value;
        emailSpan.textContent = emailInput.value;
        phoneSpan.textContent = phoneInput.value;
        addressSpan.textContent = addressInput.value;

        // Hide form, show button
        editForms.classList.add('hidden');
        changeInfo.classList.remove('hidden');
    });
});