async function fetchCustomers() {
    try {
        const response = await fetch('/api/v1/customers');
        const customers = await response.json();

        const tbody = document.querySelector('#customer-table tbody');
        tbody.innerHTML = '';

        customers.forEach(customer => {
            const tr = document.createElement('tr');

            tr.innerHTML = `
                <td>${customer.id}</td>
                <td>${customer.name}</td>
                <td>${customer.email}</td>
                <td>${customer.phone}</td>
                <td>${customer.address}</td>
                <td>
                    <button class="customerBtn-edit" onclick="openEditModal(${customer.id}, '${customer.name}', '${customer.email}', '${customer.phone}', '${customer.address}')">Sửa</button>
                    <button class="customerBtn-delete" onclick="deleteCustomer(${customer.id}, '${customer.email}')">Xóa</button>
                    <button class="customerBtn-showOrders" onclick="showOrders(${customer.id})">Xem Đơn Hàng</button>
                </td>
            `;

            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error('Error fetching customers:', error);
    }
}

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
        fetchCustomers();
        closeEditModal();
    } else {
        console.error('Failed to update customer');
    }
}

async function deleteCustomer(id, email) {
    const response = await fetch(`/api/v1/customers?id=${id}&email=${email}`, {
        method: 'DELETE',
    });

    if (response.ok) {
        fetchCustomers();
    } else {
        console.error('Failed to delete customer');
    }
}

function showOrders(id) {
    console.log('Show orders for customer with ID:', id);
    // Thêm mã để xử lý xem đơn hàng của khách hàng
}

// Gọi hàm fetchCustomers khi trang được tải
document.addEventListener('DOMContentLoaded', fetchCustomers);

// Thêm sự kiện cho form chỉnh sửa
document.getElementById('editCustomerForm').addEventListener('submit', updateCustomer);