<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>つぶやきの編集</title>
		<link href="./css/style.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="main-contents">
		<c:if test="${ not empty errorMessages }">
				<div class="errorMessages">
					<ul>
						<c:forEach items="${errorMessages}" var="errorMessage">
							<li><c:out value="${errorMessage}" />
						</c:forEach>
					</ul>
				</div>
			</c:if>
			<div class="message">
				<form action="edit" method="post"><br />
					<input name="id" value="${message.id}" id="id" type="hidden"/>
					<label for="text">つぶやき</label>
					<textarea name="text" cols="100" rows="5">${message.text}</textarea><br />
					<input type="submit" value="更新" />（140文字まで）<br />
					<a href="./">戻る</a>
				</form>
				<div class="copyright"> Copyright(c)KobayashiMiku</div>
			</div>
		</div>
	</body>
</html>