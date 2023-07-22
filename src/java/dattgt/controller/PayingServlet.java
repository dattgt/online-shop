package dattgt.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dattgt.cart.CartObject;
import dattgt.orders.OrdersCreateError;
import dattgt.orders.OrdersDAO;
import dattgt.ordersdetail.OrdersDetailDAO;
import dattgt.utils.MyApplicationConstants;

/**
 *
 * @author DATTGT
 */
@WebServlet(name = "PayingServlet", urlPatterns = {"/PayingServlet"})
public class PayingServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITE_MAP");
        String url = (String) siteMaps.get(
                MyApplicationConstants.PayingServlet.VIEW_CHECKOUT_PAGE);
        String username = request.getParameter("txtUsername");
        String total = request.getParameter("txtTotal");
        int key;
        HttpSession session = request.getSession();
        CartObject cart = (CartObject) session.getAttribute("CART");
        OrdersCreateError error = new OrdersCreateError();
        try {
            //1. Insert to DB
            OrdersDAO dao1 = new OrdersDAO();
            key = dao1.addToOrders(username, total);
            if (key != 0) {
                OrdersDetailDAO dao2 = new OrdersDetailDAO();
                dao2.addToOrdersDetail(cart, key);
            }
            //2. System remove cart
            session.getAttribute("CHECKOUT_RESULT");
            session.invalidate();
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("PayingServlet _ SQL _ " + msg);
            if (msg.contains("conflicted")){
                error.setUsernameNullError("Please input your name first");
                request.setAttribute("NULL_ERROR", error);
            }
        } catch (NamingException ex) {
            log("PayingServlet _ Naming _ " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
