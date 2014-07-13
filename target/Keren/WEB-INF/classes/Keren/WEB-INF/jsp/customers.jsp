<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h2>Customer List</h2>

	<form action="customer/save" method="post">
		<label for="name">Person Name:</label> <input type="text" id="name"
			name="name" /> <label for="creditCard">Credit Card:</label> <input
			type="text" id="creditCard" name="creditCard" /> <label
			for="address">Address:</label> <input type="text" id="address"
			name="address" /> <input type="submit" value="Add/Update" />
	</form>


	<table border="1">
		<tr>
			<td>Customer Name</td>
			<td>Credit Card</td>
			<td>Address</td>
		</tr>
		<c:forEach var="customer" items="${customerList}">
			<tr>
				<td>${customer.name}</td>
				<td>${customer.creditCard}</td>
				<td>${customer.address}</td>
				<td><input type="button" value="delete"
					onclick="window.location='customer/delete?name=${customer.name}'" />
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>