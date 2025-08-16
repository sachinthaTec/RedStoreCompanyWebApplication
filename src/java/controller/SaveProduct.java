
package controller;

import com.google.gson.JsonObject;
import hibernate.Category;
import hibernate.Color;
import hibernate.HibernateUtil;
import hibernate.Model;
import hibernate.Product;
import hibernate.Quality;
import hibernate.Status;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author USER
 */
@MultipartConfig
@WebServlet(name = "SaveProduct", urlPatterns = {"/SaveProduct"})
public class SaveProduct extends HttpServlet {

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String categoryId = request.getParameter("categoryId");
        String brandId = request.getParameter("brandId");
        String modelId = request.getParameter("modelId");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        
        String colorId = request.getParameter("colorId");
        String conditionId = request.getParameter("conditionId");
        String price = request.getParameter("price");
        String qty = request.getParameter("qty");
        
        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", false);
        
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        
        Category category = (Category) s.load(Category.class, Integer.parseInt(categoryId));
        Model model = (Model) s.load(Model.class, Integer.parseInt(modelId));
        
        Color color = (Color) s.load(Color.class, Integer.parseInt(colorId));
        Quality quality = (Quality) s.load(Quality.class, Integer.parseInt(conditionId));
        Status status = (Status) s.load(Status.class, 2);//Active
 
            Product p = new Product();
            p.setCategory(category);
            p.setModel(model);
            p.setTitle(title);
            p.setDescription(description);

            p.setColor(color);
            p.setQuality(quality);
            p.setPrice(Double.parseDouble(price));
            p.setQty(Integer.parseInt(qty));
            p.setStatus(status);

            p.setCreated_at(new Date());
            
             int id = (int) s.save(p);
             s.beginTransaction().commit();
             s.close();
             
             //image uploading
        Part part1 = request.getPart("image1");
        Part part2 = request.getPart("image2");
        Part part3 = request.getPart("image3");
        
        //Users\USER\Documents\NetBeansProjects\SmartTrade\build\web
        String appPath = getServletContext().getRealPath("");//full path of the Web Pages folder

        //Users\USER\Documents\NetBeansProjects\SmartTrade\web\product-images
        
        String newPath = appPath.replace("build" + File.separator + "web", "web" + File.separator + "product-images");

        File productFolder = new File(newPath,String.valueOf(id));
        productFolder.mkdir();
        
        File file1 = new File(productFolder, "image1.png");
        Files.copy(part1.getInputStream(), file1.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
        File file2 = new File(productFolder, "image2.png");
        Files.copy(part2.getInputStream(), file2.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
        File file3 = new File(productFolder, "image3.png");
        Files.copy(part3.getInputStream(), file3.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
        
        //image uploading

 
             
             
        
    }

    

}
