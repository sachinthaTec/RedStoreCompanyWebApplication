
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.Brand;
import hibernate.Category;
import hibernate.Color;
import hibernate.HibernateUtil;
import hibernate.Model;
import hibernate.Product;
import hibernate.Quality;
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
@WebServlet(name = "LoadData", urlPatterns = {"/LoadData"})
public class LoadData extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
         JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", false);
        
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        
        //get Category
        Criteria c1 = s.createCriteria(Category.class);
        List<Category> categoryList = c1.list();
        
        //getCategory
        
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
        
        //get Quality
        Criteria c5 = s.createCriteria(Quality.class);
        List<Quality> qualityList = c5.list();
          
        //get Quality
        
        
        //loadproductdata
         Status status = (Status) s.get(Status.class, 2);
         Criteria c6 = s.createCriteria(Product.class);
         c6.addOrder(Order.desc("id"));
         c6.add(Restrictions.eq("status", status));
         responseObject.addProperty("allProductCount", c6.list().size());
         c6.setFirstResult(0);
         c6.setMaxResults(6);
         
        List<Product> productList = c6.list();
        
//        for(Product product : productList){//user i can cut this
//            product.setUser(null);
//        }
        
        //loadproductdata-end
        
        
        Gson gson = new Gson();
        
        
        responseObject.add("categoryList", gson.toJsonTree(categoryList));
        responseObject.add("brandList", gson.toJsonTree(brandList));
        responseObject.add("modelList", gson.toJsonTree(modelList));
        responseObject.add("colorList", gson.toJsonTree(colorList));
        
        responseObject.add("qualityList", gson.toJsonTree(qualityList));
        
        
        responseObject.add("productList", gson.toJsonTree(productList));
        
        responseObject.addProperty("status", true);
        
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseObject));
        s.close();
        
        
    }

   

}
