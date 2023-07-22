package dattgt.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
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
@WebServlet(name = "FisrtTimeRequestServlet", urlPatterns = {"/FisrtTimeRequestServlet"})
public class FisrtTimeRequestServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITE_MAP");
        String url = (String) siteMaps.get(MyApplicationConstants.FirstTimeRequestServlet.LOGIN_PAGE);
        try {
            //1. Get cookies
            Cookie[] cookies = request.getCookies();
            //2. Read last cookies
            if (cookies != null) {
                Cookie lastCookies = cookies[cookies.length - 1];
                String username = lastCookies.getName();
                String password = lastCookies.getValue();
                //3. Call DAO to checkLogin
                RegistrationDAO dao = new RegistrationDAO();
                RegistrationDTO result = dao.checkLogin(username, password);
                HttpSession session = request.getSession();
                session.setAttribute("USER", result);

                //4. process
                if (result != null) {
                    url = MyApplicationConstants.FirstTimeRequestServlet.SEARCH_PAGE;
                }//end user has existed
            }//end cookies has existed
        } catch (SQLException ex) {
            log("FirstTimeRequestServlet _ SQL _ " + ex.getMessage());
        } catch (NamingException ex) {
            log("FirstTimeRequestServlet _ Naming _ " + ex.getMessage());
        } finally {
            response.sendRedirect(url);
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
