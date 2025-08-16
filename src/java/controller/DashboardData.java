/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import hibernate.HibernateUtil;
import hibernate.Product;
import hibernate.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;



/**
 *
 * @author USER
 */
@WebServlet(name = "DashboardData", urlPatterns = {"/DashboardData"})
public class DashboardData extends HttpServlet {

   

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        JsonObject res = new JsonObject();
        Gson gson = new Gson();

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        
        try {
            // Total Customers
            Long customerCount = (Long) session.createQuery("SELECT COUNT(u.id) FROM User u").uniqueResult();
            res.addProperty("customerCount", customerCount);

            // Total Items
            Long itemCount = (Long) session.createQuery("SELECT COUNT(p.id) FROM Product p").uniqueResult();
            res.addProperty("itemCount", itemCount);

            // Total Orders (using distinct orders ID from OrderItems)
            Long orderCount = (Long) session.createQuery("SELECT COUNT(DISTINCT oi.orders.id) FROM OrderItems oi").uniqueResult();
            res.addProperty("orderCount", orderCount);

            // Total Revenue (SUM of qty * price)
            Double revenue = (Double) session.createQuery(
                "SELECT SUM(oi.qty * p.price) FROM OrderItems oi JOIN oi.product p"
            ).uniqueResult();
            res.addProperty("totalRevenue", revenue != null ? revenue : 0.0);

            // Recent Customers (last 5 by created_at)
            Criteria userQuery = session.createCriteria(User.class);
            userQuery.addOrder(Order.desc("created_at"));
            
            userQuery.setMaxResults(5);
            List<User> recentUsers = userQuery.list();

            JsonArray recentCustomers = new JsonArray();
            for (User u : recentUsers) {
                JsonObject cu = new JsonObject();
                cu.addProperty("id", u.getId());
                cu.addProperty("name", u.getFirst_name() + " " + u.getLast_name());
                cu.addProperty("email", u.getEmail());
                cu.addProperty("joined", u.getCreated_at().toString());
                recentCustomers.add(cu);
            }

            res.add("recentCustomers", recentCustomers);
            res.addProperty("status", true);

        } catch (Exception e) {
            e.printStackTrace();
            res.addProperty("status", false);
            res.addProperty("error", "Internal Server Error");
        } finally {
            session.close();
        }

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(res));
    }


}
