<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Your Password</title>
    <link rel="stylesheet" href="../CSS/resetPassword.css"/>
</head>
<body>
    <div class="reset-container">
        <h1>Reset Your Password</h1>
        <form action="<%=request.getContextPath()%>/HandleResetPasswordServlet" method="post">
            <div class="form-group">
                <label for="email">Email</label>
                <input 
                    type="email" 
                    id="email" 
                    name="email" 
                    placeholder="Enter your registered email"
                    required
                    autocomplete="email"
                >
            </div>

            <div class="form-group">
                <label for="new-password">New Password</label>
                <input 
                    type="password" 
                    id="new-password" 
                    name="newPassword"
                    placeholder="Enter new password" 
                    required
                    autocomplete="new-password"
                >
                <div class="password-strength">
                    <div class="password-strength-bar"></div>
                </div>
            </div>

            <div class="form-group">
                <label for="confirm-password">Confirm Password</label>
                <input 
                    type="password" 
                    id="confirm-password" 
                    name="confirmPassword"
                    placeholder="Confirm new password" 
                    required
                    autocomplete="new-password"
                >
            </div>

            <button type="submit">Reset Password</button>
        </form>
        <a href="login.jsp" class="back-link">Back to Login</a>
    </div>

    <script>
        // Password strength indicator
        const passwordInput = document.getElementById('new-password');
        const strengthBar = document.querySelector('.password-strength-bar');

        passwordInput.addEventListener('input', function() {
            const password = this.value;
            let strength = 0;
            
            if (password.length >= 8) strength += 25;
            if (password.match(/[A-Z]/)) strength += 25;
            if (password.match(/[0-9]/)) strength += 25;
            if (password.match(/[^A-Za-z0-9]/)) strength += 25;

            strengthBar.style.width = strength + '%';
            
            if (strength <= 25) {
                strengthBar.style.backgroundColor = '#EF4444';
            } else if (strength <= 50) {
                strengthBar.style.backgroundColor = '#F59E0B';
            } else if (strength <= 75) {
                strengthBar.style.backgroundColor = '#10B981';
            } else {
                strengthBar.style.backgroundColor = '#059669';
            }
        });

        // Password match validation
        const confirmInput = document.getElementById('confirm-password');
        const form = document.querySelector('form');

        form.addEventListener('submit', function(e) {
            if (passwordInput.value !== confirmInput.value) {
                e.preventDefault();
                alert('Passwords do not match!');
            }
        });
    </script>
</body>
</html>