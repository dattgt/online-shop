package dattgt.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
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
@WebServlet(name = "DeleteAccountServlet", urlPatterns = {"/DeleteAccountServlet"})
public class DeleteAccountServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITE_MAP");
        String url = (String) siteMaps.get(
                MyApplicationConstants.DeleteAccountServlet.ERROR_PAGE);
        String username = request.getParameter("pk");
        String searchValue = request.getParameter("lastSearchValue");
        HttpSession session = request.getSession(false);
        boolean result = false;
        try {
            //1. Check validation
            if (session != null) {
                RegistrationDTO dto = (RegistrationDTO) session.getAttribute("USER");
                if (!username.equals(dto.getUsername())) {
                    //2. Call DAO
                    RegistrationDAO dao = new RegistrationDAO();
                    result = dao.deleteAccount(username);
                } else {
                    result = true;
                }//end of check validation
                //3. refresh data grid --> call search
                if (result) {
                    url = "searchLastnameController"
                            + "?btAction=Search"
                            + "&txtSearchValue=" + searchValue; //apply url rewriting of session tracking
                }//end delete is successful
            }//end if session existed
        } catch (SQLException ex) {
            log("DeleteAccountServlet _ SQL _ " + ex.getMessage());
        } catch (NamingException ex) {
            log("DeleteAccountServlet _ Naming _ " + ex.getMessage());
        } finally {
            response.sendRedirect(url);//Dùng forward thì lỗi 408
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
