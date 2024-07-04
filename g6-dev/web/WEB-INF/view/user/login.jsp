<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Login</title>
        <%
            // Define the CSS path
            String contextPath = request.getContextPath() + "/resource/";                   
        %>
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
        <script>
            $("#lms-login-btn").click(function(e) {
                var jwt = getCookie("jwt");
                if(jwt){
                    alert('You are already logged in');
                    e.preventDefault();
                }
            });
        </script>
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
                            <h2 class="title-head">Login to your <span>Account</span></h2>
                            <p>Don't have an account? <a href="${pageContext.request.contextPath}/register?type=STUDENT">Create one here</a></p>
                        </div>
                        <c:if test="${not empty message}">
                            <div class="alert alert-${alert}">
                                <strong><span class="content">${message}</span></strong>
                            </div>
                            <script>
                                $(document).ready(function() {
                                    // show the alert
                                    setTimeout(function() {
                                        $(".alert").alert('close');
                                    }, 5000);
                                });
                            </script>
                        </c:if>
                        <form class="contact-bx" action="login" method="post">
                            <div class="row placeani">
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label>Your User Name</label>
                                            <input name="username" id="username" type="text" required="" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label>Your Password</label>
                                            <input name="password" id="password" type="password" class="form-control" required="">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-12">
                                    <div class="form-group form-forget">
                                        <div class="custom-control custom-checkbox">
                                            <input type="checkbox" class="custom-control-input" id="customControlAutosizing">
                                        </div>
                                        <a href="forget-password" class="ml-auto">Forgot Password?</a>
                                    </div>
                                </div>
                                <div class="col-lg-12 m-b30">
                                    <button id="lms-login-btn" name="submit" type="submit" value="Submit" class="btn button-md">Login</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>