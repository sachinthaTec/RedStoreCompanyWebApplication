function loadData(){
    getUserData();
    getCityData();
}


async function getUserData(){
    const response = await fetch("MyAccount");
    if(response.ok){
        const json = await response.json();
        //console.log(json);
        document.getElementById("username").innerHTML = `Welcome, ${json.firstName} ${json.lastName}`;
        //document.getElementById("since").innerHTML = `Smart Trade Member Since  ${json.since}`;
        document.getElementById("firstName").value = json.firstName;
        document.getElementById("lastName").value = json.lastName;
        document.getElementById("email").value = json.email;
        document.getElementById("Password").value = json.Password;
        document.getElementById("mobile").value = json.mobile;
        document.getElementById("since").value = json.since;
        
        
        if (json.hasOwnProperty("addressList") && json.addressList !== undefined) {
            
            let lineOne ;
            let lineTwo ;
            let city;
            let postalCode ;
            let cityId;
            
            json.addressList.forEach(address =>{
                
                lineOne = address.lineOne;
                 lineTwo = address.lineTwo;
                 city = address.city.name;
                 postalCode = address.postalCode;
                
                cityId = address.city.id;
                
                
            });
            
            
            
           document.getElementById("lineOne").value = lineOne;
           document.getElementById("lineTwo").value = lineTwo;
           document.getElementById("postalCode").value = postalCode;
           document.getElementById("citySelect").value = parseInt(cityId);
            
        }
        
        
    }
    
}


async function getCityData(){
    const response = await fetch("CityData");
    if(response.ok){
        const json = await response.json();
        const citySelect = document.getElementById("citySelect");
        json.forEach(city => {
            let option = document.createElement("option");
            option.innerHTML = city.name;
            option.value = city.id;
            citySelect.appendChild(option);
            
        });
        
        
        
    }
}


async function saveChanges(){
    
      const firstName =  document.getElementById("firstName").value;
      const lastName =  document.getElementById("lastName").value;
      const mobile =  document.getElementById("mobile").value;
      const Password =  document.getElementById("Password").value;
      const lineOne =  document.getElementById("lineOne").value;
      const lineTwo =  document.getElementById("lineTwo").value;
      const cityId =  document.getElementById("citySelect").value;
      const postalCode =  document.getElementById("postalCode").value;
      
     
      
      const userDataObject = {
          firstName:firstName,
          lastName:lastName,
          mobile:mobile,
          Password:Password,
          lineOne:lineOne,
          lineTwo:lineTwo,
          cityId:cityId,
          postalCode:postalCode
          
          
      };
      
      const userDataJSON = JSON.stringify(userDataObject);
    
   const response = await fetch("MyAccount",{
       method:"PUT",
       headers:{
           "Content-Type":"application/json"
       },
       body:userDataJSON
   });
   if(response.ok){
       const json = await response.json();
       if(json.status){
           //getUserData();
           
       }else{
           document.getElementById("message").innerHTML = json.message;
           
       }
   }else{
       document.getElementById("message").innerHTML = "Profile details update failed!";
   }



}


