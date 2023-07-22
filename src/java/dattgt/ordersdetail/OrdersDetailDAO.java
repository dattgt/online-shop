
package dattgt.ordersdetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import dattgt.cart.CartObject;
import dattgt.utils.DBHelper;

/**
 *
 * @author DATTGT
 */
public class OrdersDetailDAO {
    public boolean addToOrdersDetail(CartObject cart, int key)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm1 = null;
        PreparedStatement stm2 = null;
        PreparedStatement stm3 = null;
        ResultSet rs = null;
        int effectedRows = 0;
        try {
            //1. connect DB
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Create SQL String (For selected price)
                String sql1 = "Select sku, price "
                        + "From Product "
                        + "Where sku = ?";
                //3. 
                stm1 = con.prepareStatement(sql1);
                Map<String, Integer> items = cart.getItems();
                for (String carts : items.keySet()) {
                    stm1.setString(1, carts);
                    rs = stm1.executeQuery();
                    if (rs.next()) {
                        float price = rs.getFloat("price");
                        float total = price * items.get(carts);
                        String sql2 = "Insert into OrdersDetail("
                                + " orderID, sku, quantity, price, total"
                                + ") "
                                + "Values("
                                + " ?, ?, ?, ?, ?"
                                + ")";
                        stm2 = con.prepareStatement(sql2);
                        stm2.setInt(1, key);
                        stm2.setString(2, carts);
                        stm2.setInt(3, items.get(carts));
                        stm2.setFloat(4, price);
                        stm2.setFloat(5, total);
                        effectedRows = stm2.executeUpdate();
                    }//end of insert cart on DB
                }//end of traverse cart
                if (effectedRows > 0) {
                    return true;
                }//end of return result
            }//end of order is inserted
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm1 != null) {
                stm1.close();
            }
            if (stm2 != null) {
                stm2.close();
            }
            if (stm3 != null) {
                stm3.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
}
