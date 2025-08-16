async function loadCartItems() {
    const popup = new Notification();

    const response = await fetch("LoadCartItems");
    if (response.ok) {
        const json = await response.json();
        console.log(json);
        if (json.status) {
            const cart_item_container = document.getElementById("cart-item-container");
            cart_item_container.innerHTML = "";

            let total = 0;
            let totalQty = 0;

            json.cartItems.forEach(cart => {
                let productSubTotal = cart.product.price * cart.qty;
                total += productSubTotal;
                totalQty += cart.qty;
                let tableData = ` <div class="card mb-3">
        <div class="card-body cart-item d-flex justify-content-between align-items-center flex-wrap">
          <div class="d-flex flex-wrap">
            <img src="product-images\\${cart.product.id}\\image1.png" class="product-image me-3">
            <div>
              <h6><b>${cart.product.title}</b></h6>
              
              <p><span class="price-label">Price:</span> Rs. <span>${new Intl.NumberFormat(
                        "en-US",
                        {minimumFractionDigits: 2})
                        .format(cart.product.price)}</span></p>
              <label><strong>Quantity :</strong></label>
              <input type="number" class="form-control d-inline-block w-auto ms-2" value="${cart.qty}" min="1">
            </div>
          </div>
          <div class="text-end">
            
            <button class="btn btn-remove w-100" data-id="${cart.id}">Remove</button>
            
            <div class="mt-3"><span class="price-label">SUB Total: </span><strong>Rs. <span>${new Intl.NumberFormat(
                        "en-US",
                        {minimumFractionDigits: 2})
                        .format(productSubTotal)}</span></strong></div>
          </div>
        </div>
      </div>`;

                cart_item_container.innerHTML += tableData;
            });


            // Attach event listeners for remove buttons
            document.querySelectorAll('.btn-remove').forEach(btn => {
                btn.addEventListener('click', async function () {
                    const cartId = this.getAttribute('data-id');

                    Swal.fire({
                        title: "Are you sure?",
                        text: "This item will be removed from your cart.",
                        icon: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#d33",
                        cancelButtonColor: "#3085d6",
                        confirmButtonText: "Yes, remove it!",
                        cancelButtonText: "Cancel"
                    }).then(async (result) => {
                        if (result.isConfirmed) {
                            const res = await fetch("RemoveCartItem", {
                                method: "POST",
                                headers: {"Content-Type": "application/x-www-form-urlencoded"},
                                body: `cartId=${cartId}`
                            });
                            const data = await res.json();

                            if (data.status) {
                                Swal.fire({
                                    icon: "success",
                                    title: "Removed!",
                                    text: data.message,
                                    timer: 1500,
                                    showConfirmButton: false
                                });
                                loadCartItems(); // Reload cart
                            } else {
                                Swal.fire({
                                    icon: "error",
                                    title: "Error",
                                    text: data.message
                                });
                            }
                        }
                    });
                });
            });




            document.getElementById("order-total-quantity").innerHTML = totalQty;
            document.getElementById("order-total-amount").innerHTML = new Intl.NumberFormat(
                    "en-US",
                    {minimumFractionDigits: 2})
                    .format(total);

            document.getElementById("order-total-amount1").innerHTML = new Intl.NumberFormat(
                    "en-US",
                    {minimumFractionDigits: 2})
                    .format(total);


        } else {
            popup.error({
                message: json.message
            });

        }
        console.log(json);
    } else {

        popup.error({
            message: "Cart Items loading failed..."
        });
    }

}

