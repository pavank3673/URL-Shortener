<%@page import="com.ex.url.URL"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
body {
	font-family: Segoe UI;
	text-align: center;
}

h1 {
	color: #284243;
	font-size: 3em;
	font-weight: 600;
	text-shadow: 0 0 0.3em rgba(0, 0, 0, .3);
}

input {
	font-weight: 600;
	width: 40vw;
	color: #495057;
	padding: 1.5em 1em;
	border: 0.1em solid;
	border-radius: 0.5em;
	box-shadow: 0 0 1em rgba(0, 0, 0, .3);
}

#submit {
	background-color: #284243;
	color: #fff;
	width: 9vw;
}

div {
	text-align: left;
	width: 50vw;
	padding: 0.1em 1em;
	margin-left: 22.5vw;
	margin-bottom: 3vh; 
	background-color: #d1ecf1;
	border-radius: .25rem;
	color: #34499ec0;
	box-shadow: 0 1em 3em rgba(0, 0, 0, .175); 
}

p {
	word-wrap: break-word;
}

span {
	color: #062c33;
	font-weight: 700;
}
</style>
</head>
<body>
	<h1>URL Shortener</h1>
	<form action="shorten" method="post">
		<input type="url" name="url" placeholder="Paste URL here to shorten: ">
		<input id="submit" type="submit" value="shorten">
	</form>
	<hr>
	<br>
	<%
		List<URL> urls = (List<URL>) request.getAttribute("urls");
	
	if(urls != null) {
		for(URL url : urls){
	%>
	<div>
		<p>
			<span>Short URL:</span><%= url.getShort_url() %></p>
		<p>
			<span>Original URL:</span>
			<%= url.getOriginal_url() %></p>
	</div>

	<%
		}
	}
	%>
</body>
</html>