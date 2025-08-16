payhere.onCompleted = function onCompleted(orderId) {
    const popup = new Notification();
    popup.success({
        message: "Payment completed. OrderID:" + orderId
    });
};

// Payment window closed
payhere.onDismissed = function onDismissed() {
    // Note: Prompt user to pay again or show an error page
    console.log("Payment dismissed");
};

// Error occurred
payhere.onError = function onError(error) {
    // Note: show an error page
    console.log("Error:" + error);
};

async function loadCheckoutData() {
    const popup = new Notification();
    const response = await fetch("LoadCheckOutData");
    if (response.ok) { //200
        const json = await response.json();
        if (json.status) {
            console.log(json);
            const userAddress = json.userAddress;
            const cityList = json.cityList;
            const cartItems = json.cartList;
            const deliveryTypes = json.deliveryTypes;

            // load citites
            let city_select = document.getElementById("city-select");

            cityList.forEach(city => {
                let option = document.createElement("option");
                option.value = city.id;
                option.innerHTML = city.name;
                city_select.appendChild(option);
            });

            // load current address
            const current_address_checkbox = document.getElementById("checkbox1");
            current_address_checkbox.addEventListener("change", function () {

                let first_name = document.getElementById("first-name");
                let last_name = document.getElementById("last-name");
                let line_one = document.getElementById("line-one");
                let line_two = document.getElementById("line-two");
                let postal_code = document.getElementById("postal-code");
                let mobile = document.getElementById("mobile");
                if (current_address_checkbox.checked) {
                    first_name.value = userAddress.user.first_name;
                    last_name.value = userAddress.user.last_name;
                    city_select.value = userAddress.city.id;
                    city_select.disabled = true;
                    city_select.dispatchEvent(new Event("change"));
                    line_one.value = userAddress.lineOne;
                    line_two.value = userAddress.lineTwo;
                    postal_code.value = userAddress.postalCode;
                    mobile.value = userAddress.mobile;
                } else {
                    first_name.value = "";
                    last_name.value = "";
                    city_select.value = 0;
                    city_select.disabled = false;
                    city_select.dispatchEvent(new Event("change"));
                    line_one.value = "";
                    line_two.value = "";
                    postal_code.value = "";
                    mobile.value = "";
                }
            });

            // cart-details
            let st_tbody = document.getElementById("st-tbody");
            let st_item_tr = document.getElementById("st-item-tr");
            let st_subtotal_tr = document.getElementById("st-subtotal-tr");
            let st_order_shipping_tr = document.getElementById("st-order-shipping-tr");
            let st_order_total_tr = document.getElementById("st-order-total-tr");

            st_tbody.innerHTML = "";

            let total = 0;
            let item_count = 0;
            cartItems.forEach(cart => {
                let st_item_tr_clone = st_item_tr.cloneNode(true);
                st_item_tr_clone.querySelector("#st-product-title")
                        .innerHTML = cart.product.title;
                st_item_tr_clone.querySelector("#st-product-qty")
                        .innerHTML = cart.qty;
                item_count += cart.qty;
                let item_sub_total = Number(cart.qty) * Number(cart.product.price);

                st_item_tr_clone.querySelector("#st-product-price")
                        .innerHTML = new Intl.NumberFormat(
                                "en-US",
                                {minimumFractionDigits: 2})
                        .format(item_sub_total);
                st_tbody.appendChild(st_item_tr_clone);

                total += item_sub_total;
            });

            st_subtotal_tr.querySelector("#st-product-total-amount")
                    .innerHTML = new Intl.NumberFormat(
                            "en-US",
                            {minimumFractionDigits: 2})
                    .format(total);
            st_tbody.appendChild(st_subtotal_tr);

            let shipping_charges = 0;
            city_select.addEventListener("change", (e) => {
                let cityName = city_select.options[city_select.selectedIndex].innerHTML;
                if (cityName === "Colombo") {
                    shipping_charges = item_count * deliveryTypes[0].price;
                } else {
                    // out of colombo
                    shipping_charges = item_count * deliveryTypes[1].price;
                }

                st_order_shipping_tr.querySelector("#st-product-shipping-charges")
                        .innerHTML = new Intl.NumberFormat(
                                "en-US",
                                {minimumFractionDigits: 2})
                        .format(shipping_charges);
                st_tbody.appendChild(st_order_shipping_tr);

                st_order_total_tr.querySelector("#st-order-total-amount")
                        .innerHTML = new Intl.NumberFormat(
                                "en-US",
                                {minimumFractionDigits: 2})
                        .format(shipping_charges + total);
                st_tbody.appendChild(st_order_total_tr);
            });
        } else {
            if (json.message === "empty-cart") {
                popup.error({
                    message: "Empty cart. Please add some product"
                });
                window.location = "index.html";
            } else {
                popup.error({
                    message: json.message
                });
            }
        }
    } else {
        if (response.status === 401) {
            window.location = "SignIn.html";
        }
    }
}


async function checkout() {
    let checkbox1 = document.getElementById("checkbox1").checked;
    let first_name = document.getElementById("first-name");
    let last_name = document.getElementById("last-name");
    let city_select = document.getElementById("city-select");
    let line_one = document.getElementById("line-one");
    let line_two = document.getElementById("line-two");
    let postal_code = document.getElementById("postal-code");
    let mobile = document.getElementById("mobile");

    let data = {
        isCurrentAddress: checkbox1,
        firstName: first_name.value,
        lastName: last_name.value,
        citySelect: city_select.value,
        lineOne: line_one.value,
        lineTwo: line_two.value,
        postalCode: postal_code.value,
        mobile: mobile.value
    };
    let dataJSON = JSON.stringify(data);

    const response = await fetch("CheckOut", {
        method: "POST",
        header: {
            "Content-Type": "application/json"
        },
        body: dataJSON
    });

    const popup = new Notification();
    if (response.ok) {
        const json = await response.json();
        if (json.status) {
            console.log(json);
            // PayHere Process
            payhere.startPayment(json.payhereJson);
        } else {
            popup.error({
                message: json.message
            });
        }
    } else {
        popup.error({
            message: "Somthing went wrong. Please try again!"
        });
    }
}


