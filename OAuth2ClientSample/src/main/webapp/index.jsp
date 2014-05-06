<%@page import="com.dolphine.oauth2.client.service.OAuth2Context"%>
<%@page import="com.dolphine.oauth2.client.service.Constants"%>
<%@page import="com.dolphine.oauth2.client.service.OAuth2ClientService"%>
<html>
<head>
 	<script type="text/javascript" src="js/jquery-1.11.0.min.js"></script>
</head>

<body>
	<a href="private/profile.jsp?authorizationServer=google">Autenticar Google</a>
	<br/>	
	<a href="private/profile.jsp?authorizationServer=facebook">Autenticar Facebook</a>
</body>
</html>
