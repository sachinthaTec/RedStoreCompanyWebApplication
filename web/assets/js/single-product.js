async function loadData(){
     console.log("ok");
  const searchParams =  new URLSearchParams(window.location.search);
  if(searchParams.has("id")){
      const productId =  searchParams.get("id");
        const response = await fetch("LoadSingleProduct?id=" + productId);
      
      if(response.ok){
          const json = await response.json();
          if(json.status){
              console.log("ok");
                console.log(json);
                //product-image
                document.getElementById("image1").src = "product-images\\"+json.product.id+"\\image1.png";
                //document.getElementById("image2").src = "product-images\\"+json.product.id+"\\image2.png";
                //document.getElementById("image3").src = "product-images\\"+json.product.id+"\\image3.png";
                document.getElementById("thumb-image1").src = "product-images\\"+json.product.id+"\\image1.png";
                document.getElementById("thumb-image2").src = "product-images\\"+json.product.id+"\\image2.png";
                document.getElementById("thumb-image3").src = "product-images\\"+json.product.id+"\\image3.png";
                //product-image end
                
                document.getElementById("product-title").innerHTML = json.product.title;
                document.getElementById("published-on").innerHTML = json.product.created_at;
                document.getElementById("product-price").innerHTML = new Intl.NumberFormat(
                        "en-US",
                        {minimumFractionDigits: 2})
                        .format(json.product.price);
                document.getElementById("category-name").innerHTML = json.product.category.name;
                document.getElementById("brand-name").innerHTML = json.product.model.brand.name;
                document.getElementById("model-name").innerHTML = json.product.model.name;
                document.getElementById("product-quality").innerHTML = json.product.quality.value;
                document.getElementById("producct-stock").innerHTML = json.product.qty;
                
                //product-color
                document.getElementById("color-border").innerHTML = json.product.color.value;
               // document.getElementById("color-background").style.backgroundColor = json.product.color.value;
                
                document.getElementById("product-description").innerHTML = json.product.description;
                
                //add- to-cart-main button
                const addToCartMain = document.getElementById("add-to-cart-main");
                addToCartMain.addEventListener(
                        "click", (e) => {
                            addToCart(json.product.id, document.getElementById("add-to-cart-qty").value);
                            e.preventDefault();
                        });
                 //add- to-cart-main-button-end
                 
                 
                 
                 //similer-product
                 
                 let smiler_product_main = document.getElementById("smiler-product-main");
                 let productHtml = document.getElementById("similer-product");
                 smiler_product_main.innerHTML = "";
                 json.productList.forEach(item=>{
                    let productCloneHtml =  productHtml.cloneNode(true);
                    productCloneHtml.querySelector("#similer-product-a1").href="single-product.html?id=" + item.id;
                    productCloneHtml.querySelector("#similer-product-image").src = "product-images\\" + item.id + "\\image1.png";
                    productCloneHtml.querySelector("#similer-product-add-to-cart").addEventListener(
                        "click", (e) => {
                            addToCart(item.id, 1);
                            e.preventDefault();
                        });
                        
                      productCloneHtml.querySelector("#similer-product-a2").href="single-product.html?id=" + item.id;
                      
                      productCloneHtml.querySelector("#similer-product-title").innerHTML=item.title;
                      //productCloneHtml.querySelector("#similer-product-storage").innerHTML=item.storage.value;
                      productCloneHtml.querySelector("#similer-product-price").innerHTML= "Rs. " + new Intl.NumberFormat(
                        "en-US",
                        {minimumFractionDigits: 2})
                        .format(item.price);
                
                
                      //productCloneHtml.querySelector("#similer-product-color-border").style.borderColor = "black";
                      //productCloneHtml.querySelector("#similer-product-color-background").style.backgroundColor = item.color.value;
                      
                      smiler_product_main.appendChild(productCloneHtml);
                        
                 });
                 
                 //similer-product-end
                 
                 
                 
                
          }else{
              window.location = "index.html";
          }
      }else{
          window.location = "index.html";
      }
      
  }
  
}


async function  addToCart(productId, qty){
    const popup = new Notification();
    const response = await fetch("AddToCart?prId=" + productId + "&qty=" + qty);
    if (response.ok){
        const json = await response.json();
        if (json.status){
            popup.success({
                message: json.message
            });
        }else{
             popup.error({
                message: "Something went wrong. Try again"
            });
        }
    }else {
         popup.error({
                message: "Something went wrong. Try again"
            });
    }
}



