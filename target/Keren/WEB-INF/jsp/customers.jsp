<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link href="<c:url value='/resources/css/page.css' />" rel="stylesheet">
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/common.js" />"></script>
<script>
	$(document).ready(function() {
		// find numeric field
		var $form = $('#addCustomerForm'),
			$topField = $('#creditCard'),
			$name = $('#name'),
			$button = $('#addCustomerFormSubmit'),
			$msg = $('#validationMsg');
		
		initVariables($form, $topField, $name, $button, $msg);
		// Adding validation checks on top fields before submit
		topFormValidationBeforeSubmit();
		// Adding validation checks on top number fields on focus out
		focusOutNumberValidate();
	});
</script>
</head>
<body>
	<div class="mainDiv">
		<form id="addCustomerForm" action="customers/save" method="post">
			<label for="name">Person Name:</label>
			*<input type="text" id="name"name="name" /> 
			<label for="creditCard">Credit Card:</label> 
			<input type="text" id="creditCard" name="creditCard" /> 
			<label for="address">Address:</label> 
			<input type="text" id="address" name="address" /> 
			<div class="buttonDiv customersDiv">
				<label id="validationMsg" style="color:red; display:none;"></label>
				<input type="submit" value="Add/Update" id="addCustomerFormSubmit" class="button gray"/>
			</div>
		</form>
	</div>
	<table class="mainTable customers">
		<thead>
			<tr>
				<th scope="col">Customer Name</th>
				<th scope="col">Credit Card</th>
				<th scope="col">Address</th>
				<th scope="col" class="column2">Delete?</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="customer" items="${customerList}">
				<tr>
					<th scope="row" class="column1">${customer.name}</th>
					<td>${customer.creditCard}</td>
					<td>${customer.address}</td>
					<th scope="row" class="column2"><input type="button" class="button blue" value="Delete"
						onclick="window.location='customers/delete?name=${customer.name}'" />
					</th>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>