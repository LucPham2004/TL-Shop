document.addEventListener('DOMContentLoaded', function() {
    fetchCustomerInfo();
    fetchCustomerOrders();

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

async function fetchCustomerOrders() {
    try {
        const orderResponse = await fetch('/api/v1/orders/customer/' + getCustomerId());
        const orders = await orderResponse.json();

        const ordersContainer = document.getElementById("orders-container");
        ordersContainer.innerHTML = '';

        orders.forEach(order => {
            const orderItem = document.createElement('div');
            orderItem.classList.add('order');

            const orderProducts = document.createElement('div');
            orderProducts.classList.add('orderProducts');
            
            order.orderDetails.forEach(orderDetails => {
                const orderProduct = document.createElement('div');
                orderProduct.classList.add('orderProduct');
    
                orderProduct.innerHTML = `
                    <img alt="Giày ${orderDetails.productName}" src="./img/homepage/favorite/favorite1.png">
                    <div class="productItem-info">
                        <div class="productInfo">
                            <p class="product-name">${orderDetails.productName}</p>
                            <p class="description">${orderDetails.categories}</p>
                            <p class="price">Đơn giá: ${formatNumber(parseInt(orderDetails.unitPrice))} đ</p>
                            <p class="color">Màu: ${orderDetails.color}</p>
                            <p class="size">Size: ${orderDetails.size}</p>
                            <p class="quantity">SL: ${orderDetails.quantity}</p>
                            <p class="total">Tổng: ${formatNumber(orderDetails.subtotal)} đ</p>
                        </div>
                    </div>
                `
                orderProducts.appendChild(orderProduct);

            });

            orderItem.innerHTML = `
                <div class="orderInfo">
                    <p><strong>Mã đơn hàng:</strong> ${order.id}</p>
                    <p><strong>Ngày đặt hàng:</strong> ${order.date}</p>
                    <p><strong>Tổng tiền:</strong> ${formatNumber(parseInt(order.total))} VNĐ</p>
                    <p><strong>Trạng thái:</strong> ${order.status}</p>
                    <button type="button" id="deleteOrder" onclick="deleteOrder(${order.id})">Xóa</button>
                </div>
            `
            orderItem.appendChild(orderProducts);

            ordersContainer.appendChild(orderItem);
        })

    } catch (error) {
        console.error('Error fetching customer orders: ', error);
    }
}

async function deleteOrder(id) {
    if(confirm("Bạn có chắc muốn xóa đơn hàng này?")) {
        const response = await fetch(`/api/v1/orders/${parseInt(id)}`, {
            method: 'DELETE',
        });

        if (response.ok) {
            
            showNotification();
            fetchCustomerOrders();
        } else {
            console.error('Failed to delete order');
        }
    } else {
        return;
    }
}

function formatNumber(number) {
    return number.toLocaleString('vi-VN');
}