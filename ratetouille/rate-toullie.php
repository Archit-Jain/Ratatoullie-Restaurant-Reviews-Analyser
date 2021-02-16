<!doctype html>
<html lang="en">
  <head>
    <title>Restaurant Rating</title>
    

    <link href="https://fonts.googleapis.com/css?family=Playfair+Display:300,400,700,800|Open+Sans:300,400,700" rel="stylesheet">
    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/animate.css">
    <link rel="stylesheet" href="css/magnific-popup.css">
    <link rel="stylesheet" href="css/aos.css">
    <link rel="stylesheet" href="fonts/ionicons/css/ionicons.min.css">
    <link rel="stylesheet" href="fonts/fontawesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="fonts/flaticon/font/flaticon.css">

    <!-- Theme Style -->
    <link rel="stylesheet" href="css/style.css">
	 
	<script type="text/javascript">
		  var imageURLs = [
			   "img/img_1.jpg"
			 , "img/img_2.jpg"
			 , "img/img_3.jpg"
			 , "img/img_4.jpg"
			 , "img/img_5.jpg"
			 , "img/img_6.jpg"
			 , "img/img_7.jpg"
			 , "img/img_8.jpg"
			 , "img/img_9.jpg"
			 , "img/img_10.jpg"
		  ];
		  function getImageTag() {
			var img = '<img src=\"';
			var randomIndex = Math.floor(Math.random() * imageURLs.length);
			img += imageURLs[randomIndex];
			img += '\" alt=\"Image placeholder\" class=\"img-fluid\" />';
			return img;
		  }
		</script>
  </head>
  <body onload="myFunction()">    
		
		<p id="img1"></p>
    <div class="site-wrap">
        <header class="site-header">
        <div class="row align-items-center">
          <div class="col-5 col-md-3">
             <ul class="list-unstyled social">
              <li><a href="#"><span class="fa fa-facebook"></span></a></li>
              <li><a href="#"><span class="fa fa-twitter"></span></a></li>
              <li><a href="#"><span class="fa fa-instagram"></span></a></li>
            </ul>
          </div>
          <div class="col-2 col-md-6 text-center">
            <a href="index.html" class="site-logo">Rate-touille</a>
          </div>
          <div class="col-5 col-md-3 text-right menu-burger-wrap">
            <a href="#" class="site-nav-toggle js-site-nav-toggle"><i></i></a>

          </div>
        </div>
       
      </header> <!-- site-header -->
      
      <div class="main-wrap">
        <div class="cover_1">
          <div class="img_bg" style="background-image: url(img/slider-1.jpg);" data-stellar-background-ratio="0.5">
            <div class="container">
              <div class="row align-items-center">
                <div class="col-md-12" data-aos="fade-up">
                  <h2 class="heading">This application aims at helping the connoisseur of food and the consumers. Here is list of restaurants with top restaurant ratings.</h2>
                </div>
              </div>
            </div>
          </div>
        </div> 
            

        <div class="section">
          <div class="container">
            <div class="row justify-content-center mb-5" data-aos="fade-up">
              <div class="col-md-8  text-center">
                <h2 class="mb-3">Our Top 10 Picks</h2>
                <p class="lead">Rating of all the restaurants at one place based on reviews.</p>
				<?php
				$servername = "localhost";
				$username = "root";
				$password = "";
				$dbname = "ratetouille";

				// Create connection
				$conn = new mysqli($servername, $username, $password, $dbname);
				// Check connection
				if ($conn->connect_error) {
					die("Connection failed: " . $conn->connect_error);
				}

				$sql = "SELECT rid, lid, cuisine, review_rating, star FROM restaurant ORDER BY review_rating desc ";
				$result = $conn->query($sql);

				if ($result->num_rows > 0) {
					//echo "<table><tr><th>ID</th><th>Name</th></tr>";
					// output data of each row
					while($row = $result->fetch_assoc()) {
						
						//echo "<tr><td>" . $row["rid"]. "</td><td>" . $row["lid"]. "</td><td>" . $row["cuisine"]. "</td><td>" . $row["star"]. "</td></tr>";
						?>
						<div class="media d-block d-lg-flex mb-5"  data-aos="fade-up" >
                  <figure class="mr-4 horizontal">
							
					<script type="text/javascript">
					  document.write(getImageTag());
					</script>
                  </figure>
                  <div class="media-body">
                    <h3><a href="#">Restaurant: <?php echo $row["rid"]; ?></a></h3>
					<p id="img1"></p>
                    <p class="post-meta"><span><span class="fa fa-calendar"></span>Location @ <?php echo $row["lid"]; ?></span></p>
                    <p>Cuisine: <?php echo $row["cuisine"]; ?></p>
                    <p><a href="#" class="btn btn-primary btn-outline-primary btn-sm">Our Rating: <?php echo $row["star"]; ?></a></p>

                  </div>
                </div>
				<?php
					}
					echo "</table>";
				} else {
					echo "0 results";
				}

				?>	
              </div>
            </div>
      </div> <!-- .main-wrap -->
      

      <footer>
        <div class="site-footer">
          <div class="container">
            <div class="row small-gutters mb-5">
              
              <div class="col-md-6 col-lg-3 mb-5" data-aos="fade" data-aos-delay="100">
                <div class="footer-widget footer-contact-widget">
                 
                </div>
              </div>
            </div>
          </div>
          
        </div>
      </footer>

      <div class="site-footer-copyright">
        <div class="container">
          <div class="row">
			<p <p class="footer-text col-md-12">Archit Jain | Ayush Arora | Sidharth Chauhan | Karthik<p>
						<p <p class="footer-text col-md-12">Knowlegde Process Technology Project<p>

          </div>
        </div>
      </div>

    </div>

    <!-- loader -->

    <script src="js/jquery-3.2.1.min.js"></script>
    <script src="js/jquery-migrate-3.0.1.min.js"></script>
    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/owl.carousel.min.js"></script>
    <script src="js/jquery.waypoints.min.js"></script>
    <script src="js/bootstrap-datepicker.js"></script>
    <script src="js/jquery.timepicker.min.js"></script>
    <script src="js/jquery.stellar.min.js"></script>
    <script src="js/aos.js"></script> 
    <script src="js/main.js"></script>
  </body>
</html>