/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.HibernateUtil;
import hibernate.OrderItems;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 *
 * @author USER
 */
@WebServlet(name = "LoadAdminOrderItems", urlPatterns = {"/LoadAdminOrderItems"})
public class LoadAdminOrderItems extends HttpServlet {

    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
         Session s = HibernateUtil.getSessionFactory().openSession();
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        
        try {
            Criteria c = s.createCriteria(OrderItems.class);
            c.addOrder(Order.desc("id"));
            List<OrderItems> list = c.list();

            json.addProperty("status", true);
            json.add("orderList", gson.toJsonTree(list));
        } catch (Exception e) {
            json.addProperty("status", false);
        } finally {
            s.close();
        }

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(json));
        
    }

}
