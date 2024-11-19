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
    <div class="container d-flex flex-column vh-100 mx-0">
        <div class="row my-5 h-100">
            <%-- Filter Sidepanel --%>
            <section id="filter" class="col-3">
	            <div class="border-end h-100">
	                <h3 class="border-bottom mx-3 pb-5 primaryFont">Filter By Category</h3>
                </div>
            </section>

            <%-- Display Services --%>
            <section class="col-9 pr-5 ml-5">
	            <div class="h-100">
                	<h3 class="border-bottom mx-3 pb-5 primaryFont">Services</h3>
                </div>
            </section>
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