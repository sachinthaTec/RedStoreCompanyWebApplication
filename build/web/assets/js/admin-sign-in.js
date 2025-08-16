
document.querySelector("form").addEventListener("submit", async function (e) {
    e.preventDefault();

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    const response = await fetch("AdminLoginServlet", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
    });

    const result = await response.json();
    const message = document.getElementById("message");

    if (result.status) {
        message.classList.remove("text-danger");
        message.classList.add("text-success");
        message.textContent = "Redirecting to dashboard...";
        window.location.href = "Dashboard.html"; // 🔁 update this if needed
    } else {
        message.classList.remove("text-success");
        message.classList.add("text-danger");
        message.textContent = result.message;
    }
});

