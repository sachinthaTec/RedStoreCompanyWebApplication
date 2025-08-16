
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.HibernateUtil;
import hibernate.Product;
import hibernate.Status;
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
@WebServlet(name = "LoadHomeData", urlPatterns = {"/LoadHomeData"})
public class LoadHomeData extends HttpServlet {

   
private static final int ACTIVE_STATUS_ID = 2;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Gson gson = new Gson();
        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", false);
        
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        
        Criteria c2 = s.createCriteria(Product.class);
        c2.addOrder(Order.desc("id"));
        
        Status status = (Status)s.get(Status.class, LoadHomeData.ACTIVE_STATUS_ID);
        c2.add(Restrictions.eq("status", status));
        
        c2.setFirstResult(0);
        c2.setMaxResults(9);
        
        List<Product> productList = c2.list();
//        for (Product product : productList) {
//            product.setUser(null);
//        }
        
        responseObject.add("productList", gson.toJsonTree(productList));
        
        responseObject.addProperty("status", true);
        response.setContentType("application/json");
        String toJson = gson.toJson(responseObject);
        response.getWriter().write(toJson);
        
    }

    

}
