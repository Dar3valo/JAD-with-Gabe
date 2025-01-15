<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="Models.User.User"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>User Profile | AllClean</title>
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@6.6.0/css/fontawesome.min.css">
<link rel="stylesheet" href="../CSS/profile.css" />
<link rel="stylesheet" href="../CSS/profilePic.css" />
</head>
<body>
	<%--Navbar --%>
	<jsp:include page="navbar.jsp" />
	<%
	{ // check permission
		request.setAttribute("pageAccessLevel", "2");
		RequestDispatcher rd = request.getRequestDispatcher("/checkAccessServlet");
		rd.include(request, response);

		Boolean hasAccess = (Boolean) session.getAttribute("accessCheckResult");

		if (hasAccess == null || !hasAccess) {
			response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
			return;
		}
	}
	%>

	<div class="profile-container">
		<%
		User user = (User) session.getAttribute("loggedInUser");

		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
			return;
		} else {
		%>
		<div class="profile-header text-center">


			<div class="wrapper"
				style="background-image: url('<%=user.getProfile_photo_url() != null ? user.getProfile_photo_url() : "../Image/defaultpic.png"%>');">
			</div>

			<form action="<%=request.getContextPath()%>/UpdateProfilePicServlet"
				method="post" enctype="multipart/form-data">
				<div class="file-upload-wrapper">
					<input type="file" name="profileImage" id="file-upload"
						class="file-upload-input" accept="image/*" required> <label
						for="file-upload" class="file-upload-label"> <i
						class="bi bi-cloud-arrow-up-fill upload-icon"></i> <span
						class="upload-text">Choose a file</span> <span
						class="selected-file"></span>
					</label>
					<button type="submit" class="btn btn-dark btn-outline-light mt-3">
						<i class="bi bi-camera-fill me-2"></i>Change Photo
					</button>
				</div>
			</form>


			<h1 class="mb-2"><%=user.getName()%></h1>
			<span
				class="role-badge <%=user.getRole_id() == 1 ? "role-admin" : "role-client"%>">
				<%=user.getRole_id() == 1 ? "Administrator" : "Client"%>
			</span>
		</div>

		<div class="profile-card">
			<h2 class="mb-4">Profile Information</h2>

			<div class="row">
				<div class="col-md-6">
					<div class="mb-4">
						<div class="info-label">
							<i class="bi bi-envelope-fill me-2"></i>Email
						</div>
						<div class="info-value"><%=user.getEmail()%></div>
					</div>
				</div>

				<div class="col-md-6">
					<div class="mb-4">
						<div class="info-label">
							<i class="bi bi-person-fill me-2"></i>Gender
						</div>
						<div class="info-value text-capitalize"><%=user.getGender()%></div>
					</div>
				</div>
			</div>

			<div class="divider"></div>

			<div class="d-flex justify-content-between align-items-center">
				<button class="btn btn-outline-dark text-dark"
					data-bs-toggle="modal" data-bs-target="#changePasswordModal">
					<i class="bi bi-key-fill me-2"></i>Change Password
				</button>
				<button class="btn btn-dark me-2" data-bs-toggle="modal"
					data-bs-target="#editProfileModal">
					<i class="bi bi-pencil-fill me-2"></i>Edit Profile
				</button>
			</div>
		</div>
		<%
		}
		%>
	</div>

	<!-- change password modal -->
	<div class="modal fade" id="changePasswordModal" tabindex="-1"
		aria-labelledby="changePasswordModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="changePasswordModalLabel">Change
						Password</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form id="changePasswordForm"
						action="<%=request.getContextPath()%>/UpdateUserPasswordByIdServlet"
						method="POST">
						<div class="mb-3">
							<label for="currentPassword" class="form-label">Current
								Password</label>
							<div class="input-group">
								<input type="password" class="form-control" id="currentPassword"
									name="currentPassword" required>
								<button class="btn btn-outline-secondary" type="button"
									id="toggleCurrentPassword">
									<i class="bi bi-eye"></i>
								</button>
							</div>
						</div>
						<div class="mb-3">
							<label for="newPassword" class="form-label">New Password</label>
							<div class="input-group">
								<input type="password" class="form-control" id="newPassword"
									name="newPassword" required>
								<button class="btn btn-outline-secondary" type="button"
									id="toggleNewPassword">
									<i class="bi bi-eye"></i>
								</button>
							</div>
						</div>
						<div class="mb-3">
							<label for="confirmPassword" class="form-label">Confirm
								New Password</label>
							<div class="input-group">
								<input type="password" class="form-control" id="confirmPassword"
									name="confirmPassword" required>
								<button class="btn btn-outline-secondary" type="button"
									id="toggleConfirmPassword">
									<i class="bi bi-eye"></i>
								</button>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Cancel</button>
							<button type="submit" class="btn btn-primary">Change
								Password</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- edit profile information modal -->
	<div class="modal fade" id="editProfileModal" tabindex="-1"
		aria-labelledby="editProfileModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="editProfileModalLabel">Edit
						Profile</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form id="editProfileForm"
						action="<%=request.getContextPath()%>/UpdateProfileServlet"
						method="POST">
						<div class="mb-3">
							<label for="editName" class="form-label">Name</label> <input
								type="text" class="form-control" id="editName" name="input_name"
								value="<%=user.getName()%>" required>
						</div>
						<div class="mb-3">
							<label for="editEmail" class="form-label">Email</label> <input
								type="email" class="form-control" id="editEmail"
								name="input_email" value="<%=user.getEmail()%>" required>
						</div>
						<div class="mb-3">
							<label for="editGender" class="form-label">Gender</label> <select
								class="form-select" id="editGender" name="input_gender" required>
								<option value="M" <%=user.getGender() == 'M' ? "selected" : ""%>>Male</option>
								<option value="F" <%=user.getGender() == 'F' ? "selected" : ""%>>Female</option>
								<option value="N" <%=user.getGender() == 'N' ? "selected" : ""%>>Other</option>
							</select>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Cancel</button>
							<button type="submit" class="btn btn-primary">Save
								Changes</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<%-- Footer --%>
	<jsp:include page="footer.jsp" />

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>

	<!-- Password Toggle Script -->
	<script>
		// Function to toggle password visibility
		function createPasswordToggle(inputId, toggleButtonId) {
			const toggleButton = document.getElementById(toggleButtonId);
			const input = document.getElementById(inputId);
			const icon = toggleButton.querySelector('i');

			toggleButton.addEventListener('click', function() {
				if (input.type === 'password') {
					input.type = 'text';
					icon.classList.remove('bi-eye');
					icon.classList.add('bi-eye-slash');
				} else {
					input.type = 'password';
					icon.classList.remove('bi-eye-slash');
					icon.classList.add('bi-eye');
				}
			});
		}

		// Initialize password toggles
		createPasswordToggle('currentPassword', 'toggleCurrentPassword');
		createPasswordToggle('newPassword', 'toggleNewPassword');
		createPasswordToggle('confirmPassword', 'toggleConfirmPassword');

		// Form validation
		document
				.getElementById('changePasswordForm')
				.addEventListener(
						'submit',
						function(e) {
							const newPassword = document
									.getElementById('newPassword').value;
							const confirmPassword = document
									.getElementById('confirmPassword').value;

							if (newPassword !== confirmPassword) {
								e.preventDefault();
								alert('New password and confirm password do not match!');
							}
						});
	</script>
	<script>
		document.getElementById('file-upload').addEventListener('change', function(e) {
		    const fileName = e.target.files[0]?.name;
		    const selectedFileSpan = e.target.nextElementSibling.querySelector('.selected-file');
		    if (fileName) {
		        selectedFileSpan.textContent = fileName;
		    } else {
		        selectedFileSpan.textContent = '';
		    }
		});
</script>
</body>
</html>