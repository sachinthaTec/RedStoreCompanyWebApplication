/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.HibernateUtil;
import hibernate.OrderItems;
import hibernate.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author USER
 */
@WebServlet(name = "LoadCustomerOrders", urlPatterns = {"/LoadCustomerOrders"})
public class LoadCustomerOrders extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        JsonObject res = new JsonObject();

        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM OrderItems oi WHERE oi.orders.user.id = :uid";
            Query query = session.createQuery(hql);
            query.setParameter("uid", user.getId());

            List<OrderItems> orderList = query.list();

            res.addProperty("status", true);
            res.add("orders", new Gson().toJsonTree(orderList));
        } catch (Exception e) {
            e.printStackTrace();
            res.addProperty("status", false);
            res.addProperty("message", "Database error");
        } finally {
            if (session != null) {
                session.close();
            }
        }

        response.setContentType("application/json");
        response.getWriter().write(res.toString());

    }

}
