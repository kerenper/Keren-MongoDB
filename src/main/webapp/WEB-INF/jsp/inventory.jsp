<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link href="<c:url value='/resources/css/page.css' />" rel="stylesheet">
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/common.js" />"></script>
<script>
	$(document).ready(function() {
		var $topForm = $('#addProductForm'),
			$topFields = $('#price, #amount'),
			$name = $('#name'),
			$topButton = $('#addProductFormSubmit'),
			$orderAmountFields = $("[id^='amount_']"),
			$msg = $('#validationMsg');
		
		initVariables($topForm, $topFields, $name, $topButton, $msg);
		// Adding validation checks on top fields before submit
		topFormValidationBeforeSubmit();
		// Adding validation checks on top number fields on focus out
		focusOutNumberValidate();
		
		// Adding validation checks on table fields on focus out
		$orderAmountFields.focusout(function() {
			var allValid = checkAllOrderAmountsWithButton($orderAmountFields),
				$totalLabel = $("#totalPrice"),
				$currencyLbl = $("#currency");
			
			// if the amounts are alright - updating total price of the products
			if (allValid) {
				var totalAmount = 0;
				
				// go over all the selected amounts
				$orderAmountFields.filter(function() { 
					return $.trim($(this).val()) != ""; 
				}).each(function() {
					// get the price and selected amount for every item
					var $this = $(this),
						price = parseInt($this.parent().siblings().next().html()),
						amount = parseInt($this.val());
					
					// calculate the total price
					totalAmount += price*amount;
				});
				
				// update the total price label
				$totalLabel.text(totalAmount);
				// show label
				$totalLabel.show();
				$currencyLbl.show();
			}
			// if the amounts are invalid - hide the total price label
			else if ($totalLabel.is(":visible")){
				$totalLabel.hide();
				$currencyLbl.hide();
			}
		});
		
		// Adding parameters to the form before it submits
		// when place order button is clicked
		$('#orderForm').submit( function(event) {
			// filter amount fields, with the condition that they are not empty spaces
			var $orderAmountFieldsFiltered = 
				$orderAmountFields.filter(function() { 
											return $.trim($(this).val()) != ""; 
										});

			// check only the fields that weren't empty
			if (!checkAllOrderAmounts($orderAmountFieldsFiltered)) {
				// if validity check didn't pass - don't submit the form
				event.preventDefault();
			} else {
				var map = '', 
					chosenAmounts = 
						$orderAmountFieldsFiltered
						// for each amount field - find the corresponding row and store as string
						.each(function() {
							var $this = $(this);
							map += $this.parent().siblings().first().html() + 
							":" +
							$this.val() + 
							",";
						});
				
				// add the product list to the form's post method - as the hidden input
				$('#productAmounts').attr('value', map);
			}
		});
	});
	
	function checkAllOrderAmounts($fields) {
		var allValid = true,
			submitButton = $('#placeorder');
		
		// go over each of the amount fields and check for validity
		$fields.each(function() {
			var $this = $(this),
				value = $this.val(),
				$msg = $("#validationMsg_" + $this.attr("id")),
				isValid = true;
			
			// if there was actually a value entered - check it
			if (value) {
				isValid = checkOrderAmounts($this, $msg);
				allValid = allValid && isValid;
			} else {
				$msg.hide();
			}
		});
		
		return allValid;
	}
	
	function checkOrderAmounts($currAmount, $msg) {
		var value = $currAmount.val(),
			price = $currAmount.closest('tr').children("[id^='available_']").first().text(),
			valid = true;
		
		// if the value is not numeric
		if (!innerCheckNumField($currAmount, $msg)) {
			valid = false;
		} 
		// if the value is not between 1 and the amount in the inventory
		else if ((parseInt(value) > parseInt(price)) || (parseInt(value) < 1)) {
			// show different error message
			$msg.text('Please enter a number between 1 and ' + price);
			$msg.show();
			valid = false;
		}
		// otherwise - everything is alright, hide error message
		else if ($msg.is(":visible")) {
			$msg.hide();
		}
		
		return valid;
	}
	
	// check order form's amount fields, and disable\enable submit button accordingly
	function checkAllOrderAmountsWithButton($fields) {
		var allValid = checkAllOrderAmounts($fields);
		disableOrderButton($('#placeorder'), allValid);
		return allValid;
	}
</script>
</head>
<body>
	<div class="mainDiv">
		<form id="addProductForm" action="inventory/save" method="post">
			<label for="name">Product Name:</label> 
			*<input type="text" id="name" name="name" /> 
			<label for="price">Price:</label> 
			<input type="text" id="price" name="price" size="8"/> 
			<label for="amount">Amount:</label> 
			<input type="text" id="amount" name="amount" size="8" />
			<br/> 
			<div class="buttonDiv inventoryDiv">
				<label id="validationMsg" style="color:red; display:none;"></label>
				<input id="addProductFormSubmit" type="submit" value="Add/Update" class="button gray"/>
			</div>
		</form>
	</div>
	
	<table id="products" class="mainTable">
		<thead>
			<tr>
				<th scope="col">Product Name</th>
				<th scope="col">Price</th>
				<th scope="col">Available</th>
				<th scope="col" class="column2">Delete?</th>
				<th scope="col" width="150px">Amount to Purchase</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="inventory" items="${inventoryList}">
				<tr>
					<th scope="row" class="column1">${inventory.product.name}</th>
					<td>${inventory.product.price}</td>
					<td id="available_<c:out value="${inventory.amount}" />">${inventory.amount}</td>
					<th scope="row" class="column2"><input type="button" value="Delete" class="button blue"
						onclick="window.location='inventory/delete?product=${inventory.product.name}'" />
					</th>
					<td>
						<input type="text" id="amount_<c:out value="${inventory.product.name}" />" size="2" />
					</td>
					<td class="msgCol">
						<label id="validationMsg_amount_<c:out value="${inventory.product.name}" />"></label>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<th scope="col" colspan="4" class="strong">
					<strong>Total price:</strong>
				</th>
				<th>
					<em id="totalPrice"></em>
					<em id="currency">$</em>
				</th>
			</tr>
		</tfoot>
	</table>
	<br/>
	<div class="bottomDiv">
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
			<input id="placeorder" type="submit" value="Place Order" class="button gray"/>
		</form>
	</div>
</body>
</html>