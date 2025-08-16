
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
@WebServlet(name = "SearchProducts", urlPatterns = {"/SearchProducts"})
public class SearchProducts extends HttpServlet {

    
    private static final int MAX_VALUE = 6;
    private static final int ACTIVE_ID = 2;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Gson gson = new Gson();
        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", false);
        
        JsonObject requestJsonObject = gson.fromJson(request.getReader(), JsonObject.class);

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        
        Criteria c1 = s.createCriteria(Product.class);
        
        if (requestJsonObject.has("brandName")) {

            String brandName = requestJsonObject.get("brandName").getAsString();

            //get brand details
            Criteria c2 = s.createCriteria(Brand.class);
            c2.add(Restrictions.eq("name", brandName));
            Brand brand = (Brand) c2.uniqueResult();

            //filter model by using brand details
            Criteria c3 = s.createCriteria(Model.class);
            c3.add(Restrictions.eq("brand", brand));
            List<Model> modelList = c3.list();

            //filter product by using modelList
            c1.add(Restrictions.in("model", modelList));
        }

        //--------------condition---------------------
        if (requestJsonObject.has("conditionName")) {

            String qualityValue = requestJsonObject.get("conditionName").getAsString();
            //get qulity details
            Criteria c4 = s.createCriteria(Quality.class);
            c4.add(Restrictions.eq("value", qualityValue));
            Quality quality = (Quality) c4.uniqueResult();

            //filter product by using quality
            c1.add(Restrictions.eq("quality", quality));

        }
        //---------------condition-end--------------------

        //--------------color---------------------
        if (requestJsonObject.has("colorName")) {
            String colorName = requestJsonObject.get("colorName").getAsString();
            //get qulity details
            Criteria c5 = s.createCriteria(Color.class);
            c5.add(Restrictions.eq("value", colorName));
            Color color = (Color) c5.uniqueResult();

            //filter product by using quality
            c1.add(Restrictions.eq("color", color));

        }
        //---------------color-end--------------------

        //--------------category---------------------
        if (requestJsonObject.has("categoryValue")) {
            String categoryValue = requestJsonObject.get("categoryValue").getAsString();
            //get qulity details
            Criteria c6 = s.createCriteria(Category.class);
            c6.add(Restrictions.eq("name", categoryValue));
            Category category = (Category) c6.uniqueResult();

            //filter product by using quality
            c1.add(Restrictions.eq("category", category));

        }
        //---------------category-end--------------------
        
        
        if(requestJsonObject.has("priceStart") & requestJsonObject.has("priceEnd")){
           double priceStart = requestJsonObject.get("priceStart").getAsDouble();
           double priceEnd = requestJsonObject.get("priceEnd").getAsDouble();
           
           c1.add(Restrictions.ge("price", priceStart));
           c1.add(Restrictions.le("price", priceEnd));
           
        }
        
        
        if(requestJsonObject.has("sortValue")){
          String sortValue = requestJsonObject.get("sortValue").getAsString();
          if(sortValue.equals("Sort by Latest")){
              c1.addOrder(Order.desc("id"));
          }else if(sortValue.equals("Sort by Oldest")){
              c1.addOrder(Order.asc("id"));
          }else if(sortValue.equals("Sort by Name")){
              c1.addOrder(Order.asc("title"));
          }else if(sortValue.equals("Sort by Price")){
              c1.addOrder(Order.asc("price"));
          }
          
        }
        
        responseObject.addProperty("allProductCount", c1.list().size());
        
        if(requestJsonObject.has("firstResult")){
        int firstResult = requestJsonObject.get("firstResult").getAsInt();
        c1.setFirstResult(firstResult);
        c1.setMaxResults(SearchProducts.MAX_VALUE);
        }
        

        //get filteres product list
        Status status = (Status) s.get(Status.class, SearchProducts.ACTIVE_ID);//get active product 2= active
        c1.add(Restrictions.eq("status", status));
        List<Product> productList = c1.list();

//        for (Product product : productList) {//user 
//            product.setUser(null);
//        }

        //hibernate session close
        s.close();

        responseObject.add("productList", gson.toJsonTree(productList));
        responseObject.addProperty("status", true);
        response.setContentType("application/json");
        String toJson = gson.toJson(responseObject);
        response.getWriter().write(toJson);
        
        
    }

    

}
