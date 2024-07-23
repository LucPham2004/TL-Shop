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
});
