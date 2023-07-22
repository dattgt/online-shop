package dattgt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dattgt.registration.RegistrationDAO;
import dattgt.registration.RegistrationDTO;
import dattgt.utils.MyApplicationConstants;

/**
 *
 * @author DATTGT
 */
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITE_MAP");
        String url = (String) siteMaps.get(
                MyApplicationConstants.LoginServlet.INVALID_PAGE);
        
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");

        try {
            //Call Model/DAO ==> new object & call method of that object
            RegistrationDAO dao = new RegistrationDAO();
            RegistrationDTO result = dao.checkLogin(username, password);

            if (result != null) {
                url = MyApplicationConstants.LoginServlet.SEARCH_PAGE;
                
                HttpSession session = request.getSession();
                session.setAttribute("USER", result);
                Cookie cookie = new Cookie(username, password);
                cookie.setMaxAge(60 * 3);
                response.addCookie(cookie);

            }//end user is existed

        } catch (NamingException ex) {
            log("LoginServlet _ Naming _ " + ex.getMessage());
        } catch (SQLException ex) {
            log("LoginServlet _ SQL _ " + ex.getMessage());
        } finally {
            response.sendRedirect(url); 
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
