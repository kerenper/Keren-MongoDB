<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<html>
<head>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script>
	$(document).ready(function() {
		// when place order button is clicked
		$('#orderForm').submit( function() {
			var map = {};
			
			// find all amount fields, with the condition that they are not empty
			var chosenAmounts = 
				$("[id^='amount_']").filter(function() { 
						return $.trim($(this).val()) != ""; 
					})
					// for each amount field - find the corresponding row and store as map
					.each(function() {
						var $this = $(this);
// 						alert($.trim($this.val()));
// 						alert($this.parent().parent().children().first().text());
						map[$this.parent().parent().children().first().text()] = $.trim($this.val());
					});
			
			alert(map);
// 			$('#orderForm').append(map);
// 			$('#orderForm').post();
			$('#productAmounts').attr('value', map);
// 			window.location = 'inventory/order?customer=' + $('#customer').val() + '&productAmounts=' + map;
		});
	});
</script>
</head>
<body>
	<h2>Inventory List</h2>

	<form action="inventory/save" method="post">
		<label for="name">Product Name:</label> 
		<input type="text" id="name" name="name" /> 
		<label for="price">Price:</label> 
		<input type="text" id="price" name="price" /> 
		<label for="amount">Amount:</label> 
		<input type="text" id="amount" name="amount" /> 
		<input type="submit" value="Add/Update" />
	</form>

	<table border="1">
		<tr>
			<td>Product Name</td>
			<td>Price</td>
			<td>Available</td>
			<td>Delete?</td>
			<td>Amount to Purchase</td>
		</tr>
		<c:forEach var="inventory" items="${inventoryList}">
			<tr>
				<td>${inventory.product.name}</td>
				<td>${inventory.product.price}</td>
				<td>${inventory.amount}</td>
				<td><input type="button" value="delete"
					onclick="window.location='inventory/delete?product=${inventory.product.name}'" />
				</td>
				<td>
					<input type="text" id="amount_<c:out value="${inventory.product.name}" />" size="2" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<br/>
	<form id="orderForm" action="inventory/save" method="post">
		<label>Choose customer: </label>
		<select id="customer" name="customer">
			<c:forEach var="customer" items="${customerList}">
				<option value='<c:out value="${customer}"/>'>
					<c:out value="${customer}" />
				</option>  
  			</c:forEach>  
		</select>
 		<input type="hidden" id="productAmounts" name="productAmounts"/>
		<input id="placeorder" type="submit" value="Place Order"/>
	</form>
</body>
</html>