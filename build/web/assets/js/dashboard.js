async function loadDashboardData() {
  try {
    const response = await fetch("DashboardData");

    if (response.ok) {
      const data = await response.json();

      document.getElementById("customerCount").innerText = data.customerCount;
      document.getElementById("itemCount").innerText = data.itemCount;
      document.getElementById("orderCount").innerText = data.orderCount;
      document.getElementById("revenueTotal").innerText = `Rs. ${Number(data.totalRevenue).toLocaleString("en-LK", {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
      })}`;

      const tbody = document.getElementById("customerTableBody");
      tbody.innerHTML = "";

      data.recentCustomers.forEach(c => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${c.id}</td>
          <td>${c.name}</td>
          <td>${c.email}</td>
          <td>${c.joined}</td>
        `;
        tbody.appendChild(row);
      });

    } else {
      console.error("Failed to fetch dashboard data.");
    }
  } catch (error) {
    console.error("Error:", error);
  }
}

// Call function after DOM is ready
document.addEventListener("DOMContentLoaded", loadDashboardData);



