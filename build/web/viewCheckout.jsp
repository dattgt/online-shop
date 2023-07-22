<%-- 
    Document   : viewCheckout
    Created on : Oct 17, 2022, 5:37:08 PM
    Author     : Chau Nhat Truong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check out</title>
    </head>
    <body>
        <h1>Check out</h1>
        <h2>Your order includes</h2>

        <%--<%
            //1. Cust goes to his/her cart place
            if (session != null) {
                //2. Cust take his/her cart
                CartObject cart = (CartObject) session.getAttribute("CART");
                if (cart != null) {
                    //3. Cust take the items
                    Map<String, Integer> items = cart.getItems();
                    if (items != null) {
                        //4. Cust traverse each item
        %>--%>

        <c:if test="${not empty sessionScope}">
            <c:set var="list" value="${sessionScope.CHECKOUT_RESULT}"/>
            <c:if test="${not empty list}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>

                        <%--<%
                            int count = 0;
                            for (String key : items.keySet()) {
                                int value = items.get(key);
                        %>--%>

                    <form action="payingController">
                        <c:set var="total" value="${0}"/>
                        <c:forEach var="item" items="${list}" varStatus="counter">
                            <c:set var="cart" value="${sessionScope.CART.items}"/>
                            <c:if test="${not empty cart}">
                                <c:forEach var="itemOnCart" items="${cart}">
                                    <c:if test="${item.sku == itemOnCart.key}">
                                        <tr>
                                            <td>
                                                ${counter.count}
                                            </td>
                                            <td>
                                                ${item.name}
                                            </td>
                                            <td>
                                                ${itemOnCart.value}
                                            </td>
                                            <td>
                                                ${item.price}
                                            </td>
                                            <td>
                                                ${itemOnCart.value * item.price}
                                                <c:set var="total" value="${itemOnCart.value * item.price + total}"/>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </c:if>

                            <%--<%
                                }//end travese map
                            %>--%>

                        </c:forEach>

                        <tr>
                            <td colspan="4" style="text-align: center">
                                Total bill
                            </td>
                            <td>
                                <font color="red">
                                ${total}
                                <input type="hidden" name="txtTotal" 
                                       value="${total}" />
                                </font>
                            </td>
                        </tr>
                        </tbody>
                </table>
                <a href="ShowItemsServlet">Click here to buy more</a><br/><br/>
                <c:set var="error" value="${requestScope.NULL_ERROR}"/>
                Name <input type="text" name="txtUsername" value="" />
                <br/><c:if test="${not empty error.usernameNullError}">
                    <font color="red">
                        ${error.usernameNullError}<br/>
                    </font>
                </c:if>
                <input type="submit" value="Pay" name="btAction" />
            </form>
        </c:if>
    </c:if>

    <c:if test="${empty sessionScope}">

        id: <br/>

        name: <br/>

        day: <br/>

        <h2>Thank you for choosing us</h2>
        <a href="ShowItemsServlet">Click here to buy more</a>
    </c:if>

    <%--<%
            return;
        }//end items has been existed
    }//end cart has been existed
    }//end session has been existed
    %>--%>

</body>
</html>
