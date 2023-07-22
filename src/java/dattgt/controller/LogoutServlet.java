package dattgt.controller;

import java.io.IOException;
import java.util.Properties;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dattgt.registration.RegistrationDTO;
import dattgt.utils.MyApplicationConstants;

/**
 *
 * @author DATTGT
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/LogoutServlet"})
public class LogoutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITE_MAP");
        String url = (String) siteMaps.get(
                MyApplicationConstants.LogoutServlet.LOGIN_PAGE);
        String username = "";
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                RegistrationDTO result = (RegistrationDTO) session.getAttribute("USER");
                if (result != null) {
                    username = result.getUsername();
                }
                session.invalidate();
            }
            Cookie cookies[] = request.getCookies();
            Cookie cookie = null;
            if (cookies != null) {
                for (Cookie ck : cookies) {
                    if (ck.getName().equals(username)) {
                        cookie = new Cookie(username, "");
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }
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
