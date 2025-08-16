
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.HibernateUtil;
import hibernate.User;
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
@WebServlet(name = "VerifyAccount", urlPatterns = {"/VerifyAccount"})
public class VerifyAccount extends HttpServlet {

    
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Gson gson = new Gson();
        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", false);
        
        HttpSession ses = request.getSession();
        
         if (ses.getAttribute("email") == null) {
            responseObject.addProperty("message", "Email not found");
        } else {

            String email = ses.getAttribute("email").toString();

            JsonObject code = gson.fromJson(request.getReader(), JsonObject.class);
            String verificationCode = code.get("code").getAsString();

            SessionFactory factory = HibernateUtil.getSessionFactory();

            Session session = factory.openSession();
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("email", email));
            criteria.add(Restrictions.eq("varification", verificationCode));

            if (criteria.list().isEmpty()) {
                responseObject.addProperty("message", "Verification failed!");
            } else {
                User u = (User) criteria.list().get(0);
                u.setVerification("Verified");

                session.update(u);
                session.beginTransaction().commit();
                session.close();
                
                //user object
                ses.setAttribute("user", u);

                //status activated
                responseObject.addProperty("status", true);
                System.out.println("ok");

            }
        }
        
        String respoText = gson.toJson(responseObject);
        response.setContentType("application/json");
        response.getWriter().write(respoText);
        
    }

   
}
