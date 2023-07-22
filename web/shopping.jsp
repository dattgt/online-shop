<%-- 
    Document   : shopping
    Created on : Oct 18, 2022, 2:24:17 PM
    Author     : Chau Nhat Truong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Web Mart</title>
    </head>
    <body>
        <h1>Web Mart</h1>

        <%--<%
            List<ProductDTO> result
                    = (List<ProductDTO>) request.getAttribute("SEARCH_RESULT");
            //get Attribute phải ép kiểu tường minh
            if (result != null) {
        %>--%>

        <c:set var="result" value="${requestScope.ITEMS_RESULT}"/>
        <c:if test="${not empty result}">
            <form action="viewCartPage">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Sku</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Choose</th>
                        </tr>
                    </thead>
            </form>

            <%--<%
                for (ProductDTO dto : result) {
            %>--%>

            <c:forEach var="dto" items="${result}" varStatus="counter">
                <form action="addItemsToCartController">
                    <tr>
                        <td>
                            ${counter.count}
                            .</td>
                        <td>
                            ${dto.sku}
                            <input type="hidden" name="txtSku" 
                                   value="${dto.sku}" />
                        </td>
                        <td>
                            ${dto.name}
                            <input type="hidden" name="txtName" 
                                   value="${dto.name}" />
                        </td>
                        <td>
                            ${dto.description}
                        </td>
                        <td>
                            ${dto.price}
                        </td>
                        <td>
                            <input type="number" name="txtQuantity" value="1" />
                        </td>
                        <td>
                            <input type="submit" value="Add To Cart" name="btAction" />
                        </td>
                    </tr>
                </form>

                <%--<%
                    }//end traverse each row in result
                %>--%>

            </c:forEach>
        </tbody>
    </table>
    <input type="submit" value="View Your Cart" name="btAction" />
</c:if>

<%--<%
    } else {// no record is matched
%>--%>

<c:if test="${empty result}">
    <h2>
        Sold out!!!
    </h2>

    <%--<%
    }//search Value is not existed
    %>--%>

</c:if>
</body>
</html>
