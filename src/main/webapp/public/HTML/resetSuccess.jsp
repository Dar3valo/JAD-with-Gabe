<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Password Reset Successful</title>
    <link rel="stylesheet" href="../CSS/resetPasswordSuccess.css" />
</head>
<body>
    <div class="container">
        <div class="success-icon-container">
            <div class="success-icon">✔</div>
        </div>
        <h2>Password Reset Successful!</h2>
        <p>Your password has been successfully reset.</p>
        <p>You can now <a href="login.jsp" class="button">Log in</a> to your account.</p>
    </div>

    <script>
        // Create confetti effect
        function createConfetti() {
            const colors = ['#10B981', '#3B82F6', '#6366F1', '#8B5CF6'];
            for (let i = 0; i < 50; i++) {
                const confetti = document.createElement('div');
                confetti.classList.add('confetti');
                confetti.style.left = Math.random() * 100 + 'vw';
                confetti.style.backgroundColor = colors[Math.floor(Math.random() * colors.length)];
                confetti.style.animationDelay = Math.random() * 3 + 's';
                document.body.appendChild(confetti);

                // Remove confetti after animation
                setTimeout(() => {
                    confetti.remove();
                }, 3000);
            }
        }

        // Start confetti when page loads
        window.onload = createConfetti;
    </script>
</body>
</html>