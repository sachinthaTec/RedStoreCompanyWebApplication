/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.HibernateUtil;
import hibernate.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 *
 * @author USER
 */
@WebServlet(name = "LoadAllCustomers", urlPatterns = {"/LoadAllCustomers"})
public class LoadAllCustomers extends HttpServlet {

   

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
         response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(User.class);
            criteria.addOrder(Order.desc("id"));

            List<User> users = criteria.list();

            json.addProperty("status", true);
            json.add("userList", gson.toJsonTree(users));

        } catch (Exception e) {
            e.printStackTrace();
            json.addProperty("status", false);
            json.addProperty("message", "Failed to load customers.");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        response.getWriter().write(gson.toJson(json));
        
    }

}
