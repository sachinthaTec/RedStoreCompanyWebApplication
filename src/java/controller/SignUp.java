package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.HibernateUtil;
import hibernate.User;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Mail;
import model.Util;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author USER
 */
@WebServlet(name = "SignUp", urlPatterns = {"/SignUp"})
public class SignUp extends HttpServlet {

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Gson gson = new Gson();
        JsonObject user = gson.fromJson(request.getReader(), JsonObject.class);

        String firstName = user.get("firstName").getAsString();
        String lastName = user.get("lastName").getAsString();
        final String email = user.get("email").getAsString();
        String mobile = user.get("mobile").getAsString();
        String password = user.get("password").getAsString();
        
        

        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", false);

        if (firstName.isEmpty()) {
            responseObject.addProperty("message", "First Name can not be Empty!");
        } else if (lastName.isEmpty()) {
            responseObject.addProperty("message", "Last Name can not be Empty!");
        } else if (email.isEmpty()) {
            responseObject.addProperty("message", "Email can not be Empty!");
        } else if (!Util.isEmailValid(email)) {
            responseObject.addProperty("message", "Please enter a Valide Email!");
        } else if (mobile.isEmpty()) {
            responseObject.addProperty("message", "Mobile Number can not be Empty!");
        } else if (!Util.isMobileValid(mobile)) {
            responseObject.addProperty("message", "Please enter a Valide Mobile Number!");
        } else if (password.isEmpty()) {
            responseObject.addProperty("message", "Password can not be Empty!");
        } else if (!Util.isPasswordValid(password)) {
            responseObject.addProperty("message", "The Pssword must contains at least uppercase,lowecase,number,special characters and to be minimum eight characters long!");
        } else {

            //hibenate Save
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session s = sf.openSession();

            Criteria criteria = s.createCriteria(User.class);
            criteria.add(Restrictions.eq("email", email));

            if (!criteria.list().isEmpty()) {
                responseObject.addProperty("message", "User with this Email already exixts!");
            } else {

                User u = new User();
                u.setFirst_name(firstName);
                u.setLast_name(lastName);
                u.setEmail(email);
                u.setMobile(mobile);
                u.setPassword(password);
                //genarate verifiction cod
                final String varification = Util.generateCode();
                u.setVerification(varification);

                //genarate verifiction cod
                u.setCreated_at(new Date());

                s.save(u);
                s.beginTransaction().commit();

                //hibenate Save
                //send email
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Mail.sendMail(email, "RedStore - Varification", "<h1>" + varification + "</h1>");
                    }
                }).start();
                //send email
                
                // Session Management
                
                HttpSession ses = request.getSession();
                ses.setAttribute("email", email);
                
                // Session Management

                responseObject.addProperty("status", true);
                responseObject.addProperty("message", "Registration success. Please check your email for the verification code");
            }
            
            s.close();
            
        }

        String responseText = gson.toJson(responseObject);
        response.setContentType("appliction/json");
        response.getWriter().write(responseText);
        
    }

    
}
