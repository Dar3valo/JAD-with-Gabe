<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-123" crossorigin="anonymous">
    <link rel="stylesheet" href="../CSS/forgetPassword.css">
    <title>Forget Password</title>
</head>
<body class="text-dark">
    <!-- Navbar -->
    <jsp:include page="navbar.jsp" />

    <div class="container bg-white d-flex justify-content-center align-items-center vh-100">
        <div class="card bg-dark text-white shadow-lg border-0"
             style="max-width: 700px; width: 100%; height: auto; border-radius: 20px;">
            <div class="card-body p-5">
                <!-- Title Section -->
                <h2 class="card-title text-center fw-bold mb-4">Reset Your Password</h2>
                <p class="card-text text-center text-light mb-4">
                    Enter your email address, and we'll send a password reset link to your inbox. Please check your email and follow the instructions to reset your password.
                </p>

                <!-- Form -->
                <form action="<%=request.getContextPath()%>/HandleForgetPasswordServlet" method="POST">
                    <div class="mb-4">
                        <label for="email" class="form-label text-white fw-semibold">Email Address</label>
                        <div class="input-group">
                            <span class="input-group-text bg-light text-dark"><i class="bi bi-envelope"></i></span>
                            <input type="email" class="form-control" id="email" name="email"
                                   placeholder="example@gmail.com" required>
                        </div>
                    </div>
                    <div class="d-grid">
                        <button type="submit" class="btn btn-light text-dark fw-bold">
                            Send Reset Email
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <jsp:include page="footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-123" crossorigin="anonymous"></script>
</body>
</html>
