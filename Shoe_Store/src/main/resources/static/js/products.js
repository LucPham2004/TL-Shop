// document.getElementById('shoe-form').addEventListener('submit', function(event) {
//     event.preventDefault();
    
//     const color = document.getElementById('color').value;
//     const size = document.getElementById('size').value;
//     const quantity = document.getElementById('quantity').value;
    
//     const resultDiv = document.getElementById('selection-result');
//     resultDiv.textContent = `You have selected ${quantity} pair(s) of shoes of color ${color} and size ${size}.`;
// });

document.getElementById('increase').addEventListener('click', function() {
    const quantityInput = document.getElementById('quantity');
    let currentValue = parseInt(quantityInput.value);
    quantityInput.value = currentValue + 1;
});

document.getElementById('decrease').addEventListener('click', function() {
    const quantityInput = document.getElementById('quantity');
    let currentValue = parseInt(quantityInput.value);
    if (currentValue > 1) {
        quantityInput.value = currentValue - 1;
    }
});


// document.addEventListener('DOMContentLoaded', function() {
//     const stars = document.querySelectorAll('.star');
//     stars.forEach(star => {
//         star.addEventListener('click', setRating);
//         star.addEventListener('mouseover', hoverRating);
//         star.addEventListener('mouseout', resetRating);
//     });

//     function setRating(event) {
//         const selectedValue = event.target.getAttribute('data-value');
//         stars.forEach(star => {
//             star.classList.remove('selected');
//             if (star.getAttribute('data-value') <= selectedValue) {
//                 star.classList.add('selected');
//             }
//         });
//     }

//     function hoverRating(event) {
//         const hoverValue = event.target.getAttribute('data-value');
//         stars.forEach(star => {
//             star.classList.remove('hover');
//             if (star.getAttribute('data-value') <= hoverValue) {
//                 star.classList.add('hover');
//             }
//         });
//     }

//     function resetRating() {
//         stars.forEach(star => {
//             star.classList.remove('hover');
//         });
//     }
// });
