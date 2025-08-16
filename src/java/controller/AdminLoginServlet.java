/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.AdminSignIn;
import hibernate.HibernateUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author USER
 */
@WebServlet(name = "AdminLoginServlet", urlPatterns = {"/AdminLoginServlet"})
public class AdminLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();
        JsonObject inputJson = gson.fromJson(request.getReader(), JsonObject.class);
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("status", false);

        String email = inputJson.get("email").getAsString().trim();
        String password = inputJson.get("password").getAsString().trim();

        if (email.isEmpty()) {
            responseJson.addProperty("message", "Email cannot be empty!");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            responseJson.addProperty("message", "Invalid email format!");
        } else if (password.isEmpty()) {
            responseJson.addProperty("message", "Password cannot be empty!");
        } else {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session s = null;

            try {
                s = sf.openSession();
                Criteria c = s.createCriteria(AdminSignIn.class);
                c.add(Restrictions.eq("email", email));
                c.add(Restrictions.eq("password", password));

                AdminSignIn admin = (AdminSignIn) c.uniqueResult();

                if (admin != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("admin", admin);
                    responseJson.addProperty("status", true);
                    responseJson.addProperty("message", "Login successful!");
                } else {
                    responseJson.addProperty("message", "Invalid email or password!");
                }

            } catch (Exception e) {
                responseJson.addProperty("message", "Server error: " + e.getMessage());
            } finally {
                if (s != null) {
                    s.close();
                }
            }
        }

            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(responseJson));

        }

    }
