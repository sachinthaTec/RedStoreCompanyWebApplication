/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.HibernateUtil;
import hibernate.OrderItems;
import hibernate.OrderStatus;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author USER
 */
@WebServlet(name = "UpdateOrderItemStatus", urlPatterns = {"/UpdateOrderItemStatus"})
public class UpdateOrderItemStatus extends HttpServlet {

   
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        
         JsonObject body = gson.fromJson(request.getReader(), JsonObject.class);
        int orderId = body.get("orderId").getAsInt();
        String newStatus = body.get("status").getAsString();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        
         try {
            OrderItems orderItem = (OrderItems) session.get(OrderItems.class, orderId);
            OrderStatus status = (OrderStatus) session.createCriteria(OrderStatus.class)
                    .add(Restrictions.eq("value", newStatus)).uniqueResult();

            if (orderItem != null && status != null) {
                orderItem.setOrderStatus(status);
                session.update(orderItem);
                tx.commit();
                json.addProperty("success", true);
            } else {
                json.addProperty("success", false);
            }
        } catch (Exception e) {
            tx.rollback();
            json.addProperty("success", false);
        } finally {
            session.close();
        }

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(json));
        
        
    }

}
