document.addEventListener("DOMContentLoaded", function() {
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    displayProductsInCart(cart);

    displayCustomerInfo();
});

function displayProductsInCart(cart) {
    const orderContainer = document.getElementById("products");
    orderContainer.innerHTML = '';

    let sumQuantity = 0;
    let sumPrice = 0;

    cart.forEach(product => {
        const cartItem = document.createElement("div");
        cartItem.classList.add("product-item");

        sumQuantity += parseInt(product.quantity);
        sumPrice += product.quantity * product.price;

        cartItem.innerHTML = `
            <img alt="Giày ${product.productName}" src="${product.productImage}">
            <div class="productItem-info">
                <div class="productInfo">
                    <p class="product-name">${product.productName}</p>
                    <p class="description">${product.categories}</p>
                    <p class="price">${formatNumber(parseInt(product.price))} đ</p>
                    <p class="color">${product.color}</p>
                    <p class="size">${product.size}</p>
                    <p class="quantity">${product.quantity}</p>
                    <p class="total">${formatNumber(parseInt(product.quantity * product.price))} đ</p>
                </div>
                <div class="dropCartItem">
                    <button type="button" class="dropCartItemBtn" onclick="removeFromCart(${parseInt(product.id)})"><i class="fas fa-x"></i></button>
                </div>
            </div>
        `
        orderContainer.appendChild(cartItem);
    });

    document.getElementById("sumQuantity").innerHTML = sumQuantity;
    document.getElementById("sumPrice").innerHTML = `${formatNumber(parseInt(sumPrice))} đ`;

}

document.getElementById("placeOrderBtn").addEventListener("click", function() {

    let customerInfo = JSON.parse(localStorage.getItem('user')) || [];
    if(customerInfo.address == null) {
        confirm("Quý khách vui lòng nhập địa chỉ trước khi đặt hàng");
        window.location.href = "/user/account.html";
        return;
    }


    let customerId = getCustomerId();
    let orderDetails = [];
    let cart = JSON.parse(localStorage.getItem('cart')) || [];

    cart.forEach(product => {
        let orderDetail = {
            productId: product.id,
            productName: product.productName,
            quantity: parseInt(product.quantity),
            unit_price: parseInt(product.price),
            size: parseInt(product.size),
            color: product.color,
            categories: product.categories,
            productImage: product.productImage
        };

        orderDetails.push(orderDetail);
    })

    createOrder(customerId, orderDetails);
    clearCart();
});

async function createOrder(customerId, orderDetails) {
    const url = '/api/v1/orders/placeOrder';
    const orderData = {
        customerId: customerId,
        orderDetails: orderDetails.map(detail => ({
            productId: detail.productId,
            quantity: detail.quantity,
            unit_price: detail.unit_price,
            productName: detail.productName,
            color: detail.color,
            size: detail.size,
            categories: detail.categories,
            productImage: detail.productImage
        }))
    };

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(orderData)
        });

        if (!response.ok) {
            throw new Error('Network response was not ok' + response.statusText);
        }

        const data = await response.json();
        window.alert("Đơn hàng được đặt thành công!")
        window.location.href = '/user/account.html';
        console.log('Order created successfully:', data);
        return data;
    } catch (error) {
        console.error('Error creating order:', error);
    }
}

function displayCustomerInfo() {
    try {
        let customerInfo = JSON.parse(localStorage.getItem('user')) || [];

        const phoneSpan = document.getElementById('phone');
        const addressSpan = document.getElementById('address');

        let address = customerInfo.address;
        if(address == null) {
            address = "Chưa có địa chỉ";
        }

        phoneSpan.innerHTML = `${customerInfo.phone}`;
        addressSpan.innerHTML = `${address}`;

    } catch (error) {
        console.error('Error fetching customer infomation:', error);
    }
}

