
package controller;

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
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author USER
 */
@WebServlet(name = "CheckSessionCart", urlPatterns = {"/CheckSessionCart"})
public class CheckSessionCart extends HttpServlet {

   
   
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
       User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            ArrayList<Cart> sessionCarts = (ArrayList<Cart>) request.getSession().getAttribute("sessionCart");
            if (sessionCarts != null && !sessionCarts.isEmpty()) {

                SessionFactory sf = HibernateUtil.getSessionFactory();
                Session s = sf.openSession();
                Transaction tr = s.beginTransaction();

                for (Cart sessionCart : sessionCarts) {
                    Product product = (Product) s.get(Product.class, sessionCart.getProduct().getId());

                    Criteria c1 = s.createCriteria(Cart.class);
                    c1.add(Restrictions.eq("user", user));
                    c1.add(Restrictions.eq("product", product));

                    Cart dbCart = (Cart) c1.uniqueResult();

                    if (dbCart == null) {
                        // product not in cart, save new cart item
                        Cart newCart = new Cart();
                        newCart.setProduct(product);
                        newCart.setQty(sessionCart.getQty());
                        newCart.setUser(user);

                        s.save(newCart);

                    } else {
                        // product already in cart, update qty
                        int newQty = sessionCart.getQty() + dbCart.getQty();
                        if (newQty <= product.getQty()) {
                            dbCart.setQty(newQty);
                            s.update(dbCart);
                        }
                        // else skip or handle insufficient quantity
                    }
                }

                tr.commit();
                s.close();
                request.getSession().removeAttribute("sessionCart");
            }
        }

        
    }

   

}
