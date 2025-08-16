
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.Address;
import hibernate.City;
import hibernate.HibernateUtil;
import hibernate.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Util;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author USER
 */
@WebServlet(name = "MyAccount", urlPatterns = {"/MyAccount"})
public class MyAccount extends HttpServlet {

    

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession ses = request.getSession(false);
        if(ses != null && ses.getAttribute("user") != null){
       User user = (User) ses.getAttribute("user");
       JsonObject responseObject = new JsonObject();
       responseObject.addProperty("firstName", user.getFirst_name());
       responseObject.addProperty("lastName", user.getLast_name());
       responseObject.addProperty("email", user.getEmail());
       responseObject.addProperty("Password", user.getPassword());
       responseObject.addProperty("mobile", user.getMobile());
       
       String since = new SimpleDateFormat("MMM yyyy").format(user.getCreated_at());
          responseObject.addProperty("since", since);
          
           Gson gson = new Gson();
           
           SessionFactory sf = HibernateUtil.getSessionFactory();
         Session s = sf.openSession();
         Criteria c = s.createCriteria(Address.class);
         c.add(Restrictions.eq("user", user));
         if(!c.list().isEmpty()){
             List<Address> addressList = c.list();
             responseObject.add("addressList", gson.toJsonTree(addressList));
         }
           
           
       String toJson = gson.toJson(responseObject);
       response.setContentType("application/json");
       response.getWriter().write(toJson);
        
       
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        Gson gson = new Gson();
        JsonObject userData = gson.fromJson(request.getReader(), JsonObject.class);
        
        String firstName = userData.get("firstName").getAsString();
        String lastName = userData.get("lastName").getAsString();
        String mobile = userData.get("mobile").getAsString();
        String Password = userData.get("Password").getAsString();
        String lineOne = userData.get("lineOne").getAsString();
        String lineTwo = userData.get("lineTwo").getAsString();
        int cityId = userData.get("cityId").getAsInt();
        String postalCode = userData.get("postalCode").getAsString();
        
         JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", false);
        
        if (firstName.isEmpty()) {
            responseObject.addProperty("message", "First Name can not be Empty!");
        } else if (lastName.isEmpty()) {
            responseObject.addProperty("message", "Last Name can not be Empty!");
        
        } else if (mobile.isEmpty()) {
            responseObject.addProperty("message", "Mobile Number can not be Empty!");
        } else if (!Util.isMobileValid(mobile)) {
            responseObject.addProperty("message", "Please enter a Valide Mobile Number!");
        } else if (Password.isEmpty()) {
            responseObject.addProperty("message", "Password can not be Empty!");
        } else if (!Util.isPasswordValid(Password)) {
            responseObject.addProperty("message", "The Pssword must contains at least uppercase,lowecase,number,special characters and to be minimum eight characters long!");
        } else if (lineOne.isEmpty()) {
            responseObject.addProperty("message", "Enter Addres line one");
        } else if (lineTwo.isEmpty()) {
            responseObject.addProperty("message", "Enter Addres line two");
        } else if (cityId == 0) {
            responseObject.addProperty("message", "Select a city");
        } else if (postalCode.isEmpty()) {
            responseObject.addProperty("message", "Enter youe Postal code");
        }else if (!Util.isCodeValid(postalCode)) {//check is number
            responseObject.addProperty("message", "Enter correct postal code");
        } else {
        
        
            HttpSession ses = request.getSession();
            
            if(ses.getAttribute("user") != null){
            
                User u = (User) ses.getAttribute("user");//get session user
                
                SessionFactory sf = HibernateUtil.getSessionFactory();
                Session s = sf.openSession();
                
                Criteria c = s.createCriteria(User.class);
                c.add(Restrictions.eq("email", u.getEmail()));//session user email
                if(!c.list().isEmpty()){
                
                    User u1 = (User)c.list().get(0);//db user
                    
                    u1.setFirst_name(firstName);
                    u1.setLast_name(lastName);
                    u1.setMobile(mobile);
                    u1.setPassword(Password);
                    
                    City city = (City) s.load(City.class, cityId);//primery key seach
                    
                    Address address = new Address();
                    address.setLineOne(lineOne);
                    address.setLineTwo(lineTwo);
                    address.setCity(city);
                    address.setPostalCode(postalCode);
                    address.setMobile(mobile);
                    address.setUser(u1);
                    
                    //session-management
                   ses.setAttribute("user", u1);
                   //session-management
                   
                   s.merge(u1);
                   s.save(address);
                   
                    s.beginTransaction().commit();
                   responseObject.addProperty("status", true);
                   responseObject.addProperty("message", "User profile details update successfully!");
                   s.close();
                   
                   
                
                }
            
            }
            
            
        }
        
        String toJson = gson.toJson(responseObject);
       response.setContentType("application/json");
       response.getWriter().write(toJson);
        
    }
    
    
   
}
