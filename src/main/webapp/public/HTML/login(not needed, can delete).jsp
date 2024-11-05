<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CleanLogin</title>

	
<link href="../CSS/loginStyles.css" rel="stylesheet" />

</head>
<body>


	<%-- Login Section --%>
<div class="container" id="container">
        <div class="form-container sign-up">
            <form>
                <h1>Create Account</h1>
                <div class="social-icons">
                    <a href="#" class="icons"><i class='bx bxl-google'></i></a>
                    <a href="#" class="icons"><i class='bx bxl-facebook'></i></a>
                    <a href="#" class="icons"><i class='bx bxl-github'></i></a>
                    <a href="#" class="icons"><i class='bx bxl-linkedin'></i></a>
                </div>
                <span>Register with E-mail</span>
                <input type="text" name="name" placeholder="Name">
                <input type="email" name="email" placeholder="Enter E-mail">
                <input type="password" name="password" placeholder="Enter Password">
                <%-- <input type="submit" name="btnSubmit" value="SignUp"> --%>
                <button>Sign Up</button>
            </form>
        </div>


        <div class="form-container sign-in">
            <form>
                <h1>Sign In</h1>
                <div class="social-icons">
                    <a href="#" class="icons"><i class='bx bxl-google'></i></a>
                    <a href="#" class="icons"><i class='bx bxl-facebook'></i></a>
                    <a href="#" class="icons"><i class='bx bxl-github'></i></a>
                    <a href="#" class="icons"><i class='bx bxl-linkedin'></i></a>
                </div>
                <span>Login With Email & Password</span>
                <input type="email" name="email" placeholder="Enter E-mail">
                <input type="password" name="password" placeholder="Enter Password">
                <%-- <input type="submit" name="btnSubmit" value="Login"> --%>
                <button>Sign In</button>
            </form>
        </div>


        <div class="toggle-container">
            <div class="toggle">
                <div class="toggle-panel toggle-left">
                    <h1>Welcome To <br>AllClean services</h1>
                    <p>Sign in With E-mail & Passowrd</p>
                    <button class="hidden" id="login">Sign In</button>
                </div>
                <div class="toggle-panel toggle-right">
                    <h1>Welcome to AllClean services</h1>
                    <p>Create your account here</p>
                    <button class="hidden" id="register">Sign Up</button>
                </div>
            </div>
        </div>
    </div>

	<%--<form method="post" action=verifyUser.jsp>
		Login ID: <input type="text" name=loginid> <br><br>
		Password: <input type="password" name=password> <br><br>
		<input type="submit" name="btnSubmit" value=Login>
		<input type="reset" name="btnReset" value=Reset>
	</form> --%>
	
	<script src="../JS/loginJS.js"></script>


</body>
</html>