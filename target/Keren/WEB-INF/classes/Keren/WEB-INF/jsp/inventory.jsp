<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script>
	$(document).ready(function() {
		var $topFields = $('#price, #amount');
		// find all amount fields, with the condition that they are not empty
		var $orderAmountFields = $("[id^='amount_']");
		
		// Adding parameters to the form before it submits
		// when place order button is clicked
		$('#orderForm').submit( function(event) {
			var $orderAmountFieldsFiltered = 
				$orderAmountFields.filter(function() { 
											return $.trim($(this).val()) != ""; 
										});
		
			if (!checkAllOrderAmounts($orderAmountFieldsFiltered)) {
				event.preventDefault();
			} else {
				var map = '', 
					chosenAmounts = 
						$orderAmountFieldsFiltered
						// for each amount field - find the corresponding row and store as string
						.each(function() {
							var $this = $(this);
							map += $this.parent().parent().children().first().text() + ":" + $this.val() + ",";
						});
				
				// add parameter to form's post method - put product list as the hidden input
				$('#productAmounts').attr('value', map);
			}
		});
		
		// Adding validation checks on number fields before submit
		$('#addProductForm').submit(function(event) {
			if (!checkTopNumFileds($topFields) || !checkProductNameField()) {
				event.preventDefault();
			}
		});
		
		// Adding validation checks on number fields on focus out
		$topFields.focusout(function() {
			checkTopNumFileds($topFields);
		});
		
		$orderAmountFields.focusout(function() {
			checkAllOrderAmounts($orderAmountFields);
		})
	});
	
	function checkProductNameField() {
		var $msg = $('#validationMsg');
		if (!$('#name').val().trim()) {
			$msg.text('Please enter a product name');
			$msg.show();
			return false;
		} else {
			$msg.hide();
			return true;
		}	
	}
	
	function checkTopNumFileds($fields) {
		var allValid = true,
			$msg = $('#validationMsg');
		
		$fields.each(function() {
			var value = $(this).val().trim();
		
			// if value is not empty and not numeric - show error message
			if ((value) && !$.isNumeric(value)) {
				allValid = allValid && false;
			} else {
				allValid = allValid && true;
			}
		});
		
		$('#addProductFormSubmit').attr("disabled", !allValid);
		if (!allValid) {
			$msg.text('Please enter numbers only');
			$msg.show();
		} else if ($msg.is(":visible")) {
			$msg.hide();
		}
		
		return allValid;
	}
	
	function checkAllOrderAmounts($fields) {
		var allValid = true,
			submitButton = $('#placeorder');
		
		$fields.each(function() {
			allValid = allValid && checkOrderAmounts($(this));
		});
		
		$('#placeorder').attr("disabled", !allValid);
		
		return allValid;
	}
	
	function checkOrderAmounts(currAmount) {
		$this = currAmount;
		var value = $this.val().trim(),
			msg = $("#validationMsg_" + $this.attr("id")),
			price = $this.closest('tr').children()[2].innerHTML,
			valid = true;
		
		if ((value) && !$.isNumeric(value)) {
			msg.text('Please enter numbers only');
			msg.show();
			valid = false;
		} else if (value) {
			if ((parseInt(value) > parseInt(price)) || (parseInt(value) <= 1)) {
				msg.text('Please enter a number between 1 and ' + price);
				msg.show();
				valid = false;
			} else {
				msg.hide();
				valid = true;
			}
		} else {
			msg.hide();
			valid = true;
		}
		
		return valid;
	}
</script>
</head>
<body>
	<h2>Inventory List</h2>

	<form id="addProductForm" action="inventory/save" method="post">
		<label for="name">Product Name:</label> 
		<input type="text" id="name" name="name" /> 
		<label for="price">Price:</label> 
		<input type="text" id="price" name="price" /> 
		<label for="amount">Amount:</label> 
		<input type="text" id="amount" name="amount" /> 
		<input id="addProductFormSubmit" type="submit" value="Add/Update" />
		<label id="validationMsg" style="color:red; display:none;"></label>
	</form>

	<table border="1" id="products">
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
					<label style="color:red; display:none;" id="validationMsg_amount_<c:out value="${inventory.product.name}" />"></label>
				</td>
			</tr>
		</c:forEach>
	</table>
	<br/>
	<form id="orderForm" action="inventory/order" method="post">
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