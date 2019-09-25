<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/header_footerCSS.css" />
	<link rel="stylesheet" type="text/css" href="css/homeCSS.css" />
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.9.0/css/all.css" />
	<title>Welcome to DocCentral!</title>
</head>
<body>
	<%@ include file="header.jsp"  %>
	<div class="tryProduct">
		<h1 class="item1">Work better, more efficiently, with your team.</h1>
		<p class="item2">DocCentral Business makes it easy to share your work with your team by providing a central location to store and access files.</p>
		<button class="item3">Try DocCentral Business</button>
		<a href="registerAccount" class="item4"><button>Get Bronze</button></a>
		<h1 class="item5">Do more with DocCentral Business</h1>
		<a href="#" class="item6"><h4>Or try Personal</h4></a>
		<img src="img/homeOffice.png" alt="background-img" class="item7" />
	</div>
	<div class="displayProducts">
		<a href="registerAccount" class="button3"><button>Contact Us</button></a>
		<a href="registerAccount" class="button2"><button>Purchase Now</button></a>
		<a href="registerAccount" class="button1"><button>Purchase Now</button></a>
		<section class="box1">
			<h3 class="item1">Gold</h3>
			<h1 class="item2">US $2.00</h1>
			<h4 class="item3">per month, starting at 2 users</h4>
			<h4 class="item4"><i class="fas fa-file-upload" id="fa"></i> 50 document uploads per month</h4>
			<h4 class="item4"><i class="fas fa-file-upload" id="fa"></i> Up to 20 documents uploaded per time</h4>
			<h4 class="item4"><i class="far fa-smile-beam" id="fa"></i> No advertisements</h4>
		</section>
		<section class="above2">
			<h4 class="item1">BEST VALUE</h4>
		</section>
		<section class="box2">
			<h3 class="item1">Unlimited</h3>
			<h1 class="item2">US $5.00</h1>
			<h4 class="item3">per month, starting at 10 users</h4>
			<h4 class="item4"><i class="fas fa-file-upload" id="fa"></i> Unlimited document uploads per month</h4>
			<h4 class="item4"><i class="fas fa-file-upload" id="fa"></i> Unlimited documents uploaded per time</h4>
			<h4 class="item4"><i class="far fa-smile-beam" id="fa"></i> No advertisements</h4>
			<h4 class="item4"><i class="fas fa-user-friends" id="fa"></i> Each additional 10 users for $1/month</h4>
		</section>
		<section class="box3">
			<h3 class="item1">Enterprise</h3>
			<h1 class="item2">US $15.00</h1>
			<h4 class="item3">per month, starting at 200 users</h4>
			<h4 class="item4"><i class="fas fa-file-upload" id="fa"></i> Unlimited document uploads per month</h4>
			<h4 class="item4"><i class="fas fa-file-upload" id="fa"></i> Unlimited documents uploaded per time</h4>
			<h4 class="item4"><i class="far fa-smile-beam" id="fa"></i> No advertisements</h4>
			<h4 class="item4"><i class="fas fa-users" id="fa"></i> Each additional 20 users for $1/month</h4>
		</section>
	</div>
	<div class="displayPicAndDescrip">
		<img src="img/homeOffice2.png" alt="Company Description" class="item1" />
		<section class="box1">
			<h1 class="item1">The best solution for cloud storage</h1>
		</section>
	</div>
	<%@ include file="footer.jsp"  %>
</body>
</html>