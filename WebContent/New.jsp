<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Explorient | New</title>
	<link rel="stylesheet" href="CSS/New.css" type="text/css">

</head>
<body>
	<%@include file="Website Template.jsp"%>
		
	<div style="margin-left:13.5%;padding:1px 16px;height:100%;">
	
		<div>
			<form>
				<input class="new_buttons" type="text" placeholder="Agent" />
				<div class="dropdown">
				    <input type="text" />
				    <select  onchange="this.previousElementSibling.value=this.value; this.previousElementSibling.focus()">
				        <option>This is option 1</option>
				        <option>Option 2</option>
				    </select>
				</div>
				<input class="new_buttons" type="text" placeholder="Customer id" />
				<input class="new_buttons" type="text" placeholder="Destination" />
				<input class="new_buttons" type="text" placeholder="Tour Pacakge" />
				<input class="new_buttons" type="text" placeholder="Air" />
				<div>DOD<input class="new_buttons" type="date" /></div>
				DOR<input class="new_buttons" type="date" />
			</form>
		</div>
		

		
	</div>

	
</body>
</html>