async function verifyAccount(event) {
    event.preventDefault();
    const code = document.getElementById("code").value;

    const verifyData = {
        code: code
    };

    const response = await fetch("/RrdStore/VerifyAccount", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(verifyData)
    });

    if (response.ok) {
        const json = await response.json();

        if (json.status) {
            Swal.fire({
                icon: 'success',
                title: 'Success!',
                text: 'Redirecting to Sign In Page...',
                confirmButtonText: 'OK'
            }).then(() => {
                window.location = "index.html";
            });

        } else {
            if (json.message === "Email not found") {
                Swal.fire({
                    icon: 'error',
                    title: 'Email Not Found',
                    text: 'Please Register in again!',
                    confirmButtonText: 'OK'
                }).then(() => {
                    window.location = "SignUp.html";
                });
            } else {
                Swal.fire({
                    icon: 'warning',
                    title: 'Verification Failed',
                    text: json.message,
                    confirmButtonText: 'Try Again'
                });
            }
        }

    } else {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Verification Unsuccessful! Try Again Later!',
            confirmButtonText: 'OK'
        });
    }
}
