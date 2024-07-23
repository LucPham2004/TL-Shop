// Show data in admin page
function showCustomersInAdminPage(customers) {
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
    })
};


// Delete Customer
async function deleteCustomer(id, email) {
    if(confirm("Bạn có chắc muốn xóa tài khoản của khách hàng này?")) {
        const response = await fetch(`/api/v1/customers?id=${id}&email=${email}`, {
            method: 'DELETE',
        });

        if (response.ok) {
            fetchCustomers();
        } else {
            console.error('Failed to delete customer');
        }
    } else {
        return
    }
}