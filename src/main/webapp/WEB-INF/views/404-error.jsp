<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="partials/header.jsp"/>
<body>
<div class="container">
    <div class="jumbotron">
        <h1><i class="fa fa-frown-o red"></i>404 Page Not Found</h1>
        <p class="lead">We couldn't find what you're looking for on <em><span id="display-domain"></span></em>.</p>
        <p><a class="btn btn-default btn-lg" href="/university"><span class="green">Take Me To The Homepage</span></a>
        </p>
    </div>
</div>
<div class="container">
    <div class="body-content">
        <div class="row">
            <div class="col-md-6">
                <h2>What happened?</h2>
                <p class="lead">A 404 error status implies that the file or page that you're looking for could not be
                    found.</p>
            </div>
            <div class="col-md-6">
                <h2>What can I do?</h2>
                <p class="lead">If you're a site visitor</p>
                <p>Please use your browser's back button and check that you're in the right place. If you need immediate
                    assistance, please send us an email instead.</p>
                <p class="lead">If you're the site owner</p>
                <p>Please check that you're in the right place and get in touch with your website provider if you
                    believe this to be an error.</p>
            </div>
        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</body>
</html>
