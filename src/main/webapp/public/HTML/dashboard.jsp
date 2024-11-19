<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
        rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
        crossorigin="anonymous" />
    <link rel="stylesheet"
        href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
    <!-- Corrected Google Fonts link -->
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&family=Raleway:ital,wght@0,100..900;1,100..900&display=swap"
        rel="stylesheet">
    <link rel="stylesheet" href="../CSS/dashboard.css" />
</head>
<body>
    <%--Navbar --%>
    <jsp:include page="navbar.jsp" />

    <!-- Main content -->
    <div class="container-fluid d-flex flex-column vh-100 mx-0">
        <div class="row my-5 h-100">
            <%-- Filter Sidepanel --%>
            <div class="col-3">
	            <section id="filter" class="border-end h-100">
	                <h3 class="border-bottom mx-3 pb-5 mb-5 primaryFont">Filter By Category</h3>
	                <div class="mx-3">
	                	<!-- Filter Categories Form Here -->
	                	<h4 class="secondaryFont">Categories</h4>
	                	<form class="lh-lg">
	                		<input type="checkbox" id="home" name="home" value="home">
							<label for="home"> Home Cleaning</label><br>
							<input type="checkbox" id="office" name="office" value="office">
							<label for="office"> Office Cleaning</label><br>
							<input type="checkbox" id="tapestry" name="tapestry" value="tapestry">
							<label for="tapestry"> Tapestry Cleaning</label><br> 
	                	</form>
	                </div>
                </section>
            </div>

            <%-- Display Services --%>
            <div class="col-9 pr-5 ml-5">
	            <section id="services" class="h-100">
                	<h3 class="border-bottom mx-3 pb-5 primaryFont">Services</h3>
	                <div class="mx-3">
	                	<!-- Services Offered Displayed Here -->
	                	<%-- Dummy Modal Service --%>
	                	
	                </div>
                </section>
            </div>
        </div>
    </div>

    <%-- Footer --%>
    <footer>
        <jsp:include page="footer.jsp" />
    </footer>

    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>