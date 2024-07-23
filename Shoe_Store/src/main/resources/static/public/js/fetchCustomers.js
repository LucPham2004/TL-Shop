async function fetchCustomers() {
    try {
        const response = await fetch('/api/v1/customers');
        return await response.json();

    } catch (error) {
        console.error('Error fetching customers:', error);
}}


// Edit Customer's info
function openEditModal(id, name, email, phone, address) {
    document.getElementById('editCustomerId').value = id;
    document.getElementById('editCustomerName').value = name;
    document.getElementById('editCustomerEmail').value = email;
    document.getElementById('editCustomerPhone').value = phone;
    document.getElementById('editCustomerAddress').value = address;

    document.getElementById('editModal').style.display = 'block';
}

function closeEditModal() {
    document.getElementById('editModal').style.display = 'none';
}

async function updateCustomer(event) {
    event.preventDefault();

    const id = document.getElementById('editCustomerId').value;
    const name = document.getElementById('editCustomerName').value;
    const email = document.getElementById('editCustomerEmail').value;
    const phone = document.getElementById('editCustomerPhone').value;
    const address = document.getElementById('editCustomerAddress').value;

    const response = await fetch(`/api/v1/customers`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ id, name, email, phone, address }),
    });

    if (response.ok) {
        closeEditModal();
    } else {
        console.error('Failed to update customer');
    }
}


function showOrders(id) {

}

// Thêm sự kiện cho form chỉnh sửa
document.getElementById('editCustomerForm').addEventListener('submit', updateCustomer);