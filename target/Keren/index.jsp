<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link href="<c:url value='/resources/css/main.css' />" rel="stylesheet">
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<meta charset="windows-1255">
<script>
	$(document).ready(function() {
		// set the selected tab as the first
		var selected = $("#navigation ul").children().first(),
			selectedClass = "selected";
		selected.addClass(selectedClass);
		
		// whenever a tab is clicked
		$("#navigation ul li a").click(function() {
			var $this = $(this);
			
			// remove the old tab from being selected
			if (selected != null) {
				$(selected).removeClass(selectedClass);
			}
			
			// set this tab as selected
			selected =  $this.parent();
			$this.parent().addClass(selectedClass);
		});
	});
	
	// resize the window according to the height of the iframe
	// this is to avoid double scrolling bars
	function resizeIframe(obj) {
	    obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
	  } 
</script>
<title>Keren's Store</title>
</head>
<body>
	<div class="indexMainDiv">
		<h2>Keren's Store</h2>
		<div id="navigation">
			<ul>
				<li><a href="customers" target="frame">Customers</a></li>
				<li><a href="inventory" target="frame">Inventory</a></li>
				<li><a href="orders" target="frame">Orders</a></li>
			</ul>
		</div>
		<iframe name="frame" src="customers" width="1000" scrolling="no" onload='javascript:resizeIframe(this);'></iframe>
	</div>
</body>
</html>
