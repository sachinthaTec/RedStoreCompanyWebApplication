
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.Cart;
import hibernate.HibernateUtil;
import hibernate.Product;
import hibernate.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author USER
 */
@WebServlet(name = "AddToCart", urlPatterns = {"/AddToCart"})
public class AddToCart extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String prId = request.getParameter("prId");
        String qty = request.getParameter("qty");

        Gson gson = new Gson();
        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", false);
        
        
        if (!Util.isInteger(prId)) {
            responseObject.addProperty("message", "Invalid product Id!");
        } else if (!Util.isInteger(qty)) {
            responseObject.addProperty("message", "Invalid product Quantity!");
        } else {

            //add to cart process
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session s = sf.openSession();
            Transaction tr = s.beginTransaction();

            Product product = (Product) s.get(Product.class, Integer.valueOf(prId));

            if (product == null) {
                responseObject.addProperty("message", "Product Not Found!");
            } else { // product availabel in data bade

                User user = (User) request.getSession().getAttribute("user");
                if (user != null) {// add product to database cart - user available

                    Criteria c1 = s.createCriteria(Cart.class);
                    c1.add(Restrictions.eq("user", user));
                    c1.add(Restrictions.eq("product", product));

                    if (c1.list().isEmpty()) {//product not available in same product id
                        if (Integer.parseInt(qty) <= product.getQty()) {

                            Cart cart = new Cart();
                            cart.setQty(Integer.parseInt(qty));
                            cart.setUser(user);
                            cart.setProduct(product);

                            s.save(cart);
                            tr.commit();
                            responseObject.addProperty("status", true);
                            responseObject.addProperty("message", "Product add to cart successfully");
                        } else {
                            responseObject.addProperty("message", "Oops... Insufficient Product quantity!!");
                        }
                    } else {// product availabel
                        Cart cart = (Cart) c1.uniqueResult();
                        int newQty = cart.getQty() + Integer.parseInt(qty);
                        if (newQty <= product.getQty()) {
                            cart.setQty(newQty);
                            s.update(cart);
                            tr.commit();
                            responseObject.addProperty("status", true);
                            responseObject.addProperty("message", "Product cart successfully pudated..");
                        } else {
                            responseObject.addProperty("message", "Oops... Insufficient Product quantity!!");
                        }
                    }

                } else { // add product to session cart - not user

                    HttpSession ses = request.getSession();

                    if (ses.getAttribute("sessionCart") == null) {
                        if (Integer.parseInt(qty) <= product.getQty()) {

                            ArrayList<Cart> sessCarts = new ArrayList<>();
                            Cart cart = new Cart();
                            cart.setQty(Integer.parseInt(qty));
                            cart.setUser(null);
                            cart.setProduct(product);
                            sessCarts.add(cart);

                            ses.setAttribute("sessionCart", sessCarts);
                            responseObject.addProperty("status", true);
                            responseObject.addProperty("message", "Product added to the cart");
                        } else {
                            responseObject.addProperty("message", "Oops... Insufficient Product quantity!!");
                        }
                    } else { // sessionCart availabe

                        ArrayList<Cart> sessionList = (ArrayList<Cart>) ses.getAttribute("sessionCart");
                        Cart foundedCart = null;
                        for (Cart cart : sessionList) {
                            if (cart.getProduct().getId() == product.getId()) {
                                foundedCart = cart; // reassigned by using exists cart
                                break;
                            }
                        }
                        if (foundedCart != null) {
                            int newQty = foundedCart.getQty() + Integer.parseInt(qty);
                            if (newQty <= product.getQty()) {
                                foundedCart.setUser(null);
                                foundedCart.setQty(newQty);
                                responseObject.addProperty("status", true);
                                responseObject.addProperty("message", "Product cart updated");
                            } else {
                                responseObject.addProperty("message", "Oops... Insufficient Product quantity!!");
                            }
                        } else {
                            if (Integer.parseInt(qty) <= product.getQty()) {
                                foundedCart = new Cart();// asign a new cart object
                                foundedCart.setQty(Integer.parseInt(qty));
                                foundedCart.setUser(null);
                                foundedCart.setProduct(product);
                                sessionList.add(foundedCart);

                                responseObject.addProperty("status", true);
                                responseObject.addProperty("message", "Product add to the cart");
                            } else {
                                responseObject.addProperty("message", "Oops... Insufficient Product quantity!!");
                            }
                        }

                    }

                }

            }

        }
        //responseObject.addProperty("status", Boolean.TRUE);
        //add to cart process end

        response.setContentType("application/json");
        String toJson = gson.toJson(responseObject);
        response.getWriter().write(toJson);
        
    }

   

}
