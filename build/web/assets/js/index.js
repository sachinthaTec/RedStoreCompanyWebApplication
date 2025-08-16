function indexOnloadFunctions() {
    checkSessionCart();
    loadProductData();
}
async function checkSessionCart() {
    const popup = new Notification();
    const response = await fetch("CheckSessionCart");
    if (!response.ok) {
        popup.error({
            message: "Something went wrong! Try again shortly"
        });
    }
}

async function loadProductData() {

    const popup = new Notification();
    const response = await fetch("LoadHomeData");
    if (response.ok) {
        const json = await response.json();
        if (json.status) {
            console.log(json);
//            loadBrands(json);
            loadNewArrivals(json);
        } else {
            popup.error({
                message: "Something went wrong! Try again shortly"
            });
        }
    } else {
        popup.error({
            message: "Something went wrong! Try again shortly"
        });
    }
}

//function loadBrands(json) {
//    const product_brand_container = document.getElementById("product-brand-container");
//    let product_brand_card = document.getElementById("product-brand-card");
//    product_brand_container.innerHTML = "";
//    let card_delay = 200;
//    json.brandList.forEach(item => {
//        let product_brand_card_clone = product_brand_card.cloneNode(true);
//        product_brand_card_clone.querySelector("#product-brand-mini-card")
//                .setAttribute("data-sal", "zoom-out");
//        product_brand_card_clone.querySelector("#product-brand-mini-card")
//                .setAttribute("data-sal-delay", String(card_delay));
//        product_brand_card_clone.querySelector("#product-brand-a")
//                .href = "search.html";
//        product_brand_card_clone.querySelector("#product-brand-title")
//                .innerHTML = item.name;
//        product_brand_container.appendChild(product_brand_card_clone);
//        card_delay += 100;
//        sal();
//    });
//}

function loadNewArrivals(json) {
    const new_arrival_product_container = document.getElementById("new-arrival-product-container");
    new_arrival_product_container.innerHTML = "";

    json.productList.forEach(item => {
        let product_card = `   <div class="col-md-4">
      <div class="card product-card shadow-sm" >
          <a href="single-product.html?id=${item.id}"><img src="product-images\\${item.id}\\image1.png" class="card-img-top" alt="Product"></a>
        <div class="card-body">
          <h5 class="card-title">${item.title}</h5>
          
          <div class="d-flex justify-content-between">
            <span class="fw-bold text-primary">Rs. ${new Intl.NumberFormat(
                "en-US",
                {minimumFractionDigits: 2})
                .format(item.price)}</span>
            <div class="d-flex gap-2">
              <button class="btn btn-sm btn-outline-secondary" title="Quick View"><i class="bi bi-heart"></i></button>
              <button class="btn btn-sm btn-outline-dark" onclick="addToCart(${item.id},1);">Add to Cart</button>
            </div>
          </div>
        </div>
      </div>
    </div>`;
        new_arrival_product_container.innerHTML += product_card;
    });
}

async function addToCart(productId, qty) {
    const popup = new Notification();// link notification js in single-product.html
    const response = await fetch("AddToCart?prId=" + productId + "&qty=" + qty);
    if (response.ok) {
        const json = await response.json(); // await response.text();
        if (json.status) {
            popup.success({
                message: json.message
            });
        } else {
            popup.error({
                message: "Something went wrong. Try again"
            });

        }
    } else {
        popup.error({
            message: "Something went wrong. Try again"
        });
    }
}



