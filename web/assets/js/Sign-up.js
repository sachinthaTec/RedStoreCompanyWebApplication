async function signUp() {

    const firstName = document.getElementById("firstName").value;
    const lastName = document.getElementById("lastName").value;
    const email = document.getElementById("email").value;
    const mobile = document.getElementById("mobile").value;
    const password = document.getElementById("password").value;
    
    

    const user = {
        firstName: firstName,
        lastName: lastName,
        email: email,
        mobile: mobile,
        password: password

    };

    const userJson = JSON.stringify(user);

    const response = await fetch(
            "SignUp",
            {
                method: "POST",
                body: userJson,
                header: {
                    "Content-Type": "appliction/json"
                }
            }
    );


//    if(response.ok){//success
//       const json = await response.json();
//        if(json.status){
//            
//            window.location = "VerifyAccount.html";
//        }else{//wen status false
//           document.getElementById("message").innerHTML= json.message;
//        }
//    }else{
//        document.getElementById("message").innerHTML= "Registration fialed. Please try again";
//    }

    if (response.ok) {
        const json = await response.json();
        if (json.status) {
            Swal.fire({
                icon: 'success',
                title: 'Success!',
                text: json.message,
                confirmButtonText: 'Verify Account'
            }).then(() => {
                window.location = "VerifyAccount.html";
            });
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Oops!',
                text: json.message
            });
        }
    } else {
        Swal.fire({
            icon: 'error',
            title: 'Registration Failed',
            text: 'Please try again later.'
        });
    }


}

