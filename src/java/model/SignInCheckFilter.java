/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author USER
 */
@WebFilter(urlPatterns = {"/Profile.html"})
public class SignInCheckFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        
        HttpSession ses = request.getSession(false);
        if(ses != null && ses.getAttribute("user") != null){
            chain.doFilter(req, res);
        }else {
            response.sendRedirect("SignIn.html");
        }
        
    }

    @Override
    public void destroy() {
       
    }
    
}
