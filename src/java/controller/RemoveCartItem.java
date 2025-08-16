/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.Cart;
import hibernate.HibernateUtil;
import hibernate.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author USER
 */
@WebServlet(name = "RemoveCartItem", urlPatterns = {"/RemoveCartItem"})
public class RemoveCartItem extends HttpServlet {

    

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        Gson gson = new Gson();
        JsonObject res = new JsonObject();
        res.addProperty("status", false);
        
        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                res.addProperty("message", "Please log in first.");
            } else {
        
                 String cartIdStr = request.getParameter("cartId");
                if (cartIdStr != null && !cartIdStr.isEmpty()) {
                    int cartId = Integer.parseInt(cartIdStr);

                    Session session = HibernateUtil.getSessionFactory().openSession();
                    Transaction tx = session.beginTransaction();

                    Cart cart = (Cart) session.get(Cart.class, cartId);
                    if (cart != null && cart.getUser().getId() == user.getId()) {
                        session.delete(cart);
                        tx.commit();
                        res.addProperty("status", true);
                        res.addProperty("message", "Item removed successfully.");
                    } else {
                        res.addProperty("message", "Cart item not found or not yours.");
                        tx.rollback();
                    }

                    session.close();
                } else {
                    res.addProperty("message", "Invalid cart ID.");
                }
            }
        } catch (Exception e) {
            res.addProperty("message", "Error: " + e.getMessage());
        }

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(res));
    }

    
}
