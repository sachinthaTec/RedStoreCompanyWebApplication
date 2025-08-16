async function signIn(event) {
    event.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const signIn = {
        email: email,
        password: password
    };

    const signInJson = JSON.stringify(signIn);
    
    const response = await fetch(
            "SignIn1",
    {
        method: "POST",
        body: signInJson,
        header:{
            "Content-Type": "application/json"
        }
    }
            );
    
    
     if(response.ok){//success
       const json = await response.json();
        if(json.status){
            
            if(json.message === "1"){
                window.location = "VerifyAccount.html"; 
            }else{
                window.location = "/RrdStore/index.html";
            }
           
        }else{//wen status false
                document.getElementById("message").innerHTML= json.message;
        }
    }else{
        document.getElementById("message").innerHTML= "Sign In fialed. Please try again";
    }
    
    
}




