<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Register User</title>
        <%
            String contextPath = request.getContextPath() + "/resource/";
        %>
        <!-- META ============================================= -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="keywords" content="" />
        <meta name="author" content="" />
        <meta name="robots" content="" />

        <!-- All PLUGINS CSS ============================================= -->
        <link rel="stylesheet" type="text/css" href="<%=contextPath%>css/assets.css">

        <!-- TYPOGRAPHY ============================================= -->
        <link rel="stylesheet" type="text/css" href="<%=contextPath%>css/typography.css">

        <!-- SHORTCODES ============================================= -->
        <link rel="stylesheet" type="text/css" href="<%=contextPath%>css/shortcodes/shortcodes.css">

        <!-- STYLESHEETS ============================================= -->
        <link rel="stylesheet" type="text/css" href="<%=contextPath%>css/style.css">
        <link class="skin" rel="stylesheet" type="text/css" href="<%=contextPath%>css/color/color-1.css">
        <!-- External JavaScripts -->
        <script src="<%=contextPath%>js/jquery.min.js"></script>
        <script src="<%=contextPath%>vendors/bootstrap/js/popper.min.js"></script>
        <script src="<%=contextPath%>vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="<%=contextPath%>vendors/bootstrap-select/bootstrap-select.min.js"></script>
        <script src="<%=contextPath%>vendors/bootstrap-touchspin/jquery.bootstrap-touchspin.js"></script>
        <script src="<%=contextPath%>vendors/magnific-popup/magnific-popup.js"></script>
        <script src="<%=contextPath%>vendors/counter/waypoints-min.js"></script>
        <script src="<%=contextPath%>vendors/counter/counterup.min.js"></script>
        <script src="<%=contextPath%>vendors/imagesloaded/imagesloaded.js"></script>
        <script src="<%=contextPath%>vendors/masonry/masonry.js"></script>
        <script src="<%=contextPath%>vendors/masonry/filter.js"></script>
        <script src="<%=contextPath%>vendors/owl-carousel/owl.carousel.js"></script>
        <script src="<%=contextPath%>js/functions.js"></script>
        <script src="<%=contextPath%>js/contact.js"></script>
        <script src='<%=contextPath%>vendors/switcher/switcher.js'></script>	
    </head>
    <body id="bg">
        <div class="page-wraper">
            <div id="loading-icon-bx"></div>
            <div class="account-form">
                <div class="account-head" style="background-image:url(template/<%=contextPath%>/images/background/bg2.jpg);">
                    <a href="index.html"><img src="template/<%=contextPath%>/images/logo-white-2.png" alt=""></a>
                </div>
                <div class="account-form-inner">
                    <div class="account-container">
                        <div class="heading-bx left">
                            <h2 class="title-head">Sign Up <span>Now</span></h2>
                            <p>Login Your Account <a href="${pageContext.request.contextPath}/login">Click here</a></p>
                        </div>	
                        <%
                            String type = request.getParameter("type");
                        %>

                        <form class="contact-bx" action="/register" method="POST">
                            <input type="hidden" name="type" value="<%= type%>">                         
                            <div class="row placeani">
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label>Your Username</label>
                                            <input name="username" id="username" type="text" required class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label>Your Fullname</label>
                                            <input name="fullname" id="fullname" type="text" required class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label>Your Email Address</label>
                                            <input id="email" name="email" type="text" required class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label>Your Mobile Number</label>
                                            <input name="phone" id="phone" type="text" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <div class="input-group"> 
                                            <label>Your Password</label>
                                            <input name="password" id="password" type="password" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-12 m-b30">
                                    <button name="submit" type="submit" value="Submit" class="btn button-md">Sign Up</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

