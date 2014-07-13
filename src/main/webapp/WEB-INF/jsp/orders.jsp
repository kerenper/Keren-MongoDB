<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<link href="<c:url value='/resources/css/page.css' />" rel="stylesheet">
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/common.js" />"></script>
<script>
	function filterOrders() {
		var selected = $('#customerSelect').find('option:selected').text(),
			redirect = "orders";
		
		// redirect the page according to the selected customer
		if (selected != "ALL") {
			redirect += "?customer=" + selected;
		}
		
		window.location.replace(redirect);
	}
</script>
</head>
<body>
	<div class="mainDiv ordersDiv">
		<label for="name">Customer Name:</label> 
		<select id="customerSelect" name="customer">
		 	<optgroup>
				<option value="ALL" 
				<c:if test="${selectedCustomer == ALL}">
					selected
				</c:if>
				>ALL</option>
			</optgroup>
			<optgroup label="_____________">
			    <c:forEach items="${customerList}" var="customer">
			        <option value="${customer}" 
					<c:if test="${customer == selectedCustomer}">
						selected
					</c:if>
					>${customer}</option>
			    </c:forEach>
		    </optgroup>
   		</select>
		<input type="button" value="Search" onclick="filterOrders()" class="button gray"/>
	</div> 	
	<table class="mainTable orders">
		<thead>
			<tr>
				<th scope="col">Customer Name</th>
				<th scope="col" width="120px">Date</th>
				<th scope="col" width="70px">Product</th>
				<th scope="col" width="40px">Price</th>
				<th scope="col" width="65px">Amount</th>
				<th scope="col">Total Price</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="order" items="${orderList}">
				<tr>
					<th scope="row" class="column1">${order.customer.name}</th>
					<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${order.date}" /></td>
					<td colspan="3">
						<table class="subTable">
							<c:forEach var="details" items="${order.details}">
								<tr>
									<td width="67px">${details.product.name}</td>
									<td width="44px">${details.product.price}</td>
									<td width="63px">${details.amount}</td>
								</tr>
							</c:forEach>
						</table>
					</td>
					<th scope="row" class="column1 column3">${order.total}</th>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>