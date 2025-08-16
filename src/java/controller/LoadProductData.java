
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.Brand;
import hibernate.Category;
import hibernate.Color;
import hibernate.HibernateUtil;
import hibernate.Model;
import hibernate.Quality;
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

/**
 *
 * @author USER
 */
@WebServlet(name = "LoadProductData", urlPatterns = {"/LoadProductData"})
public class LoadProductData extends HttpServlet {

    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", false);
        
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        
        //get category
        Criteria c1 = s.createCriteria(Category.class);
        List<Category> categoryList = c1.list();
        
        //get category
        
        //get brands
        Criteria c2 = s.createCriteria(Brand.class);
        List<Brand> brandList = c2.list();
        
        //get brands
        
        //get model
        Criteria c3 = s.createCriteria(Model.class);
        List<Model> modelList = c3.list();
        
        //get model
        
        //get color
        Criteria c4 = s.createCriteria(Color.class);
        List<Color> colorList = c4.list();
        //get color
        
        //get color
        Criteria c5 = s.createCriteria(Quality.class);
        List<Quality> qualityList = c5.list();
          
        //get Storege
        
        s.close();
        
        Gson gson = new Gson();
        
        
        responseObject.add("categoryList", gson.toJsonTree(categoryList));
        responseObject.add("brandList", gson.toJsonTree(brandList));
        responseObject.add("modelList", gson.toJsonTree(modelList));
        responseObject.add("colorList", gson.toJsonTree(colorList));
        responseObject.add("qualityList", gson.toJsonTree(qualityList));
        
        responseObject.addProperty("status", true);
        
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseObject));
        
        
    }

   

}
