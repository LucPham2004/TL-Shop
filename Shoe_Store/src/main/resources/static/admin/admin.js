// Show shop data from server
document.addEventListener("DOMContentLoaded", async function() {
    const products = await fetchProducts();
    showProductsInAdminPage(products);

    const brands = await fetchBrandData();
    showBrandsInAdminPage(brands);

    const categories = await fetchCategoryData();
    showCategoriesInAdminPage(categories);

    const customers = await fetchCustomers();
    showCustomersInAdminPage(customers);

    await loadSummary();
}); 

async function loadSummary() {
    const summaryDiv = document.getElementById('summary');
    summaryDiv.innerHTML = '';

    try {
        const response = await fetch(`/admin/api/dashboard`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        console.log(data);
        const html = `
            <h2>Dashboard Summary</h2>
            <ul>
                <li>Total Categories: ${data.totalCategories}</li>
                <li>Total Brands: ${data.totalBrands}</li>
                <li>Total Products: ${data.totalProducts}</li>
                <li>Total Reviews: ${data.totalReviews}</li>
                <li>Total Customers: ${data.totalCustomers}</li>
                <li>Total Orders: ${data.totalOrders}</li>
                <li>Enabled Customers: ${data.enabledCustomersCount}</li>
                <li>Disabled Customers: ${data.disabledCustomersCount}</li>
                <li>Locked Customers: ${data.lockedCustomersCount}</li>
                <li>Non-Locked Customers: ${data.nonLockedCustomersCount}</li>
                <li>Enabled Products: ${data.enabledProductsCount}</li>
                <li>Disabled Products: ${data.disabledProductsCount}</li>
                <li>Total Revenue: ${data.totalRevenue}</li>
                <li>Delivered Orders: ${data.deliveredOrdersCount}</li>
                <li>Processing Orders: ${data.processingOrdersCount}</li>
                <li>Shipping Orders: ${data.shippingOrdersCount}</li>
                <li>Cancelled Orders: ${data.cancelledOrdersCount}</li>
                <li>Reviewed Products: ${data.reviewedProductsCount}</li>
            </ul>
        `;

        summaryDiv.innerHTML = html;
    } catch (error) {
        console.error('Error fetching summary:', error);
    }
}