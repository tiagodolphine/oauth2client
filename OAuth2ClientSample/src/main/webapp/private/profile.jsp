<%@page import="com.dolphine.oauth2.client.service.OAuth2Context"%>
<%@page import="com.dolphine.oauth2.client.service.Constants"%>
<%@page import="com.dolphine.oauth2.client.service.OAuth2ClientService"%>
<html>
<head>
 	<script type="text/javascript" src="../js/jquery-1.11.0.min.js"></script>
 	<script type="text/javascript" src="../js/json-view/jquery.jsonview.js"></script>
    <link rel="stylesheet" href="../js/json-view/jquery.jsonview.css" />
    
    <script type="text/javascript">

	$(function() {
		var json = <%
		OAuth2ClientService service = new OAuth2ClientService();
		OAuth2Context context=(OAuth2Context) session.getAttribute(Constants.OAUTH2_CONTEXT_PARAMETER);
		String r = service.performGet(context.getConfiguration().getProfileUrl(),context.getOauth2Token());
		out.print(r);
		%>
		
	  $("#json").JSONView(json);
	   });
	</script>    
</head>

<body>
	<a href="../index.jsp">Home</a>
	<br/>
	<div id="json"/>	
</body>
</html>