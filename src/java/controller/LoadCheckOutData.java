/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.Address;
import hibernate.Cart;
import hibernate.City;
import hibernate.DeliveryTypes;
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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author USER
 */
@WebServlet(name = "LoadCheckOutData", urlPatterns = {"/LoadCheckOutData"})
public class LoadCheckOutData extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Gson gson = new Gson();
        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", false);
        //operation
        User sessionUser = (User) request.getSession().getAttribute("user");
        
        
        if (sessionUser == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        } else {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session s = sf.openSession();

            Criteria c1 = s.createCriteria(Address.class);
            c1.add(Restrictions.eq("user", sessionUser));
            c1.addOrder(Order.desc("id"));

            if (c1.list().isEmpty()) {
                responseObject.addProperty("message",
                        "Your account details are incomplete. Please filling your shipping address");
            } else {
                Address address = (Address) c1.list().get(0);
                // retrive user first name and last name
                address.getUser().setEmail(null);
                address.getUser().setPassword(null);
                address.getUser().setVerification(null);
                address.getUser().setId(-1);
                address.getUser().setCreated_at(null);
                responseObject.addProperty("status", true);
                responseObject.add("userAddress", gson.toJsonTree(address));
            }

            // all-city-data
            Criteria c2 = s.createCriteria(City.class);
            c2.addOrder(Order.asc("name"));
            List<City> cityList = c2.list();
            responseObject.add("cityList", gson.toJsonTree(cityList));

            // get user carts
            Criteria c3 = s.createCriteria(Cart.class);
            c3.add(Restrictions.eq("user", sessionUser));
            List<Cart> cartList = c3.list();
            if (cartList.isEmpty()) {
                responseObject.addProperty("message", "empty-cart");
            } else {
                for (Cart cart : cartList) {
                    cart.setUser(null); // ignore user details in cart
                    //cart.getProduct().setUser(null); // ignore seller information
                }
                Criteria c4 = s.createCriteria(DeliveryTypes.class);
                List<DeliveryTypes> deliveryTypes = c4.list();
                responseObject.add("deliveryTypes", gson.toJsonTree(deliveryTypes));
                responseObject.add("cartList", gson.toJsonTree(cartList));
                responseObject.addProperty("status", true);
            }
            s.close();
        }

        response.setContentType("application/json");
        String toJson = gson.toJson(responseObject);
        response.getWriter().write(toJson);
        
    }

}
