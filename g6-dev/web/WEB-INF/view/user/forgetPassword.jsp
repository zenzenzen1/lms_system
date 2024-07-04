<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <%
            String contextPath = request.getContextPath() + "/resource/";                   
        %>
        <title>Forget Password</title>
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
                <div class="account-head" style="background-image:url(<%=contextPath%>images/background/bg2.jpg);">
                    <a href="home"><img src="<%=contextPath%>images/logo-white-2.png" alt=""></a>
                </div>
                <div class="account-form-inner">
                    <div class="account-container">
                        <div class="heading-bx left">
                            <h2 class="title-head">Forget <span>Password</span></h2>
                            <p>Login Your Account <a href="login">Click here</a></p>
                        </div>
                        <form class="contact-bx">
                            <div class="row placeani">
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label>Your Email Address</label>
                                            <input name="dzName" type="email" required="" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-12 m-b30">
                                    <button name="submit" type="submit" value="Submit" class="btn button-md">Submit</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
