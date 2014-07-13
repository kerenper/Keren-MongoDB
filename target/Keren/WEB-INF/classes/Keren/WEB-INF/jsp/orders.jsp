<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h2>Customer List</h2>

	<form action="orders/search" method="post">
		<label for="name">Customer Name:</label> 
		<select id="customer" name="customer">
			<option value="ALL">ALL</option>
			<c:forEach var="customer" items="${customerList}">
				<option value='<c:out value="${customer}" />'><c:out
						value="${customer}" /></option>
			</c:forEach>
		</select>
		<input type="submit" value="Search" />
	</form>


	<table border="1">
		<tr>
			<td>Customer Name</td>
			<td>Date</td>
			<td>Product</td>
			<td>Amount</td>
			<td>Total</td>
		</tr>
		<c:forEach var="order" items="${orderList}">
			<c:forEach var="details" items="${order.details}">
				<tr>
					<td>${order.customer.name}</td>
					<td>${order.date}</td>
					<td>${details.product.name}</td>
					<td>${details.product.amount}</td>
					<td>${order.total}</td>
				</tr>
			</c:forEach>
		</c:forEach>
	</table>
</body>
</html>