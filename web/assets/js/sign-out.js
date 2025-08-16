async function signOut(){
    const response = await fetch("SignOut");
    if(response.ok){
        const json = await response.json();
        if(json.ststus){
            window.location="SignIn.html";
        }else{
            window.location.reload();
        }
    }else{
        console.log("Logout Faild!");
    }
    
}


