
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author USER
 */
@WebServlet(name = "SignOut", urlPatterns = {"/SignOut"})
public class SignOut extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        JsonObject  responseObject = new JsonObject();
        responseObject.addProperty("status", false);
        
        HttpSession ses = request.getSession(false);
        if(ses != null && ses.getAttribute("user") != null){
        ses.invalidate();
        responseObject.addProperty("status", true);
        
        }
        
        Gson gson = new  Gson();
        String toJson = gson.toJson(responseObject);
        response.setContentType("application/json");
        response.getWriter().write(toJson);
        
        
    }

    

}
