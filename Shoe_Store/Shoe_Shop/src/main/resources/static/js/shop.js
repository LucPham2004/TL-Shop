// Pagination
const itemsPerPage = 8;
const items = [
    'Item 1', 'Item 2', 'Item 3', 'Item 4', 'Item 5', 'Item 6',
    'Item 7', 'Item 8', 'Item 9', 'Item 10', 'Item 11', 'Item 12',
    'Item 13', 'Item 14', 'Item 15', 'Item 16', 'Item 17', 'Item 18',
    'Item 19', 'Item 20'
];

let currentPage = 1;
const totalPages = Math.ceil(items.length / itemsPerPage);

function displayItems(page) {
    const start = (page - 1) * itemsPerPage;
    const end = start + itemsPerPage;
    const itemsToDisplay = items.slice(start, end);
    const itemsContainer = document.getElementById('onSale-items');
    itemsContainer.innerHTML = '';

    itemsToDisplay.forEach(item => {
        const itemElement = document.createElement('div');
        itemElement.className = 'onSale-item';
        itemElement.textContent = item;
        itemsContainer.appendChild(itemElement);
    });
}

function setupPagination() {
    const paginationContainer = document.getElementById('pagination');
    paginationContainer.innerHTML = '';

    const prevLi = document.createElement('li');
    prevLi.className = 'page-item';
    prevLi.innerHTML = `
        <a class="page-link" href="#" aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
        </a>
    `;
    prevLi.addEventListener('click', () => {
        if (currentPage > 1) {
            currentPage--;
            displayItems(currentPage);
            updatePagination();
        }
    });
    paginationContainer.appendChild(prevLi);

    for (let i = 1; i <= totalPages; i++) {
        const li = document.createElement('li');
        li.className = `page-item ${i === currentPage ? 'active' : ''}`;
        li.innerHTML = `<a class="page-link" href="#">${i}</a>`;
        li.addEventListener('click', () => {
            currentPage = i;
            displayItems(currentPage);
            updatePagination();
        });
        paginationContainer.appendChild(li);
    }

    const nextLi = document.createElement('li');
    nextLi.className = 'page-item';
    nextLi.innerHTML = `
        <a class="page-link" href="#" aria-label="Next">
            <span aria-hidden="true">&raquo;</span>
        </a>
    `;
    nextLi.addEventListener('click', () => {
        if (currentPage < totalPages) {
            currentPage++;
            displayItems(currentPage);
            updatePagination();
        }
    });
    paginationContainer.appendChild(nextLi);
}

function updatePagination() {
    const paginationItems = document.querySelectorAll('.pagination .page-item');
    paginationItems.forEach((item, index) => {
        if (index > 0 && index <= totalPages) {
            item.classList.remove('active');
            if (index === currentPage) {
                item.classList.add('active');
            }
        }
    });
}

// Initialize
displayItems(currentPage);
setupPagination();
