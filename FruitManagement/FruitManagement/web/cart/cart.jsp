<%-- 
    Document   : cart
    Created on : Feb 25, 2025, 10:08:19 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Giỏ hàng của bạn</h1>
        <a href="<%= request.getContextPath()%>/product/ProductListCart.jsp">Tiếp tục mua hàng</a>
    <c:set var="cart" value="${sessionScope.cart}"/>
    <c:choose>
        <c:when test="${empty cart}">
            <p>Giỏ hàng trống!</p>
        </c:when>
        <c:otherwise>
            <c:set var="totalPrice" value="0"/>
            <div class="site-blocks-table">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th></th>
                            <th class="product-thumbnail">Image</th>
                            <th class="product-name">Product</th>
                            <th class="product-price">Price</th>
                            <th class="product-quantity">Quantity</th>
                            <th class="product-total">Total</th>
                            <th class="product-remove">Remove</th>
                        </tr>
                    </thead>

                    <tbody>
                    <c:forEach items="${cart.items}" var="item">
                        <tr data-id="${item.product.productId}">
                            <td class="product-select">
                                <input type="checkbox" class="product-checkbox" name="product-select"/>
                            </td>
                            <td class="product-thumbnail">
                                <img src="data:image/jpg;base64,${item.product.imageURL}" alt="Image" class="img-fluid">
                            </td>
                            <td>
                                <input name="product-name" class="form-control-plaintext h5 text-black"
                                       value="${item.product.productName}" style="text-align: center" readonly>
                            </td>
                            <td>
                                <input name="product-price" class="form-control-plaintext h5 text-black"
                                       value="${item.product.price}" style="text-align: center" readonly>
                            </td>
                            <td style="min-width: 180px">
                                <div class="input-group" style="max-width: fit-content; margin: 0;">
                                    <div class="input-group-prepend">
                                        <button class="btn btn-outline-primary js-btn-minus" type="button">&minus;</button>
                                    </div>
                                    <input name="product-quantity" type="text" class="form-control text-center"
                                           value="${item.quantity}">
                                    <div class="input-group-append">
                                        <button class="btn btn-outline-primary js-btn-plus" type="button">&plus;</button>
                                    </div>
                                </div>
                            </td>
                        <c:set var="subTotal" value="${item.product.price * item.quantity}"/>
                        <td>
                            <input name="product-price-total" class="form-control-plaintext h5 text-black"
                                   value="${subTotal}" style="text-align: center" readonly>
                        </td>
                        <td><a href="cart?remove-product-id=${item.product.productId}" class="btn btn-primary btn-sm">X</a></td>
                        </tr>
                        <c:set var="totalPrice" value="${subTotal + subTotal}"/>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <h3>Tổng thanh toán: ${totalPrice}</h3>
            <form action="<%=  request.getContextPath()%>/checkout" method="post">
                <button type="submit">Mua hàng</button>
            </form>
        </c:otherwise>
    </c:choose>
</body>
</html>
