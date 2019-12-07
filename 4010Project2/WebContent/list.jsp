<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="edu.umsl.math.beans.*"%>
<%@ page import="edu.umsl.math.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script type="text/javascript"
	src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML">
	
</script>
<script type="text/javascript" src="js/MathBank.js"></script>
<script type="text/javascript">
	window.MathJax = {
		tex2jax : {
			inlineMath : [ [ '$', '$' ], [ "\\(", "\\)" ] ],
			processEscapes : true
		}
	};
</script>
<title>Math Question Bank</title>
</head>
<body>
	<input id="crtpg" type="hidden" value="${crtpg}" />
	<%
		List<Problem> myproblist = (List<Problem>) request.getAttribute("problist");
	%>
	
	
	<div class="container">
		<div class="row">
			<div class="col-md-offset-2 col-md-8">
				<table width="100%" class="table table-bordered table-striped">
					<tr>
						<td colspan="3">
							<div class="input-group">
								
										<form name= "problemform" onsubmit="return validation()" action= "InsertMathServlet"  method= "post"  >
	     									 <input id="problem" name="problem" class="form-control" type="text"  placeholder="Enter your Problem"/> <br>
	     									 <span id="first" class="text-danger"></span>
	     									 <input id="category" name="category" class="form-control" type="text"  placeholder="Enter category of the problem"/>
	     									 <input type="submit" class="btn btn-success" value="Submit Button">
	                       					 <input type="reset" class="btn btn-info" value="Reset Button"> <br> 
										</form>
										<c:choose>
											<c:when test="${as==0}">
												<button type="button" style="height: 2.4em"
												class="btn btn-default">
												<b>All Problems</b>
											
												</button>
        									</c:when>
										<c:otherwise>
												<button type="button" style="height: 2.4em"
													onclick="loadProblemsInAss(0)" class="btn btn-default">All
													Problems</button>
										</c:otherwise>
										</c:choose>
									<c:forEach var="ass" items="${asslist}">
									<c:choose>
										<c:when test="${as==ass.aid}">
											<button type="button" style="height: 2.4em"
												class="btn btn-default">
												<b>${ass.title}</b>
											</button>
										</c:when>
										<c:otherwise>
											<button type="button" style="height: 2.4em"
												onclick="loadProblemsInAss(${ass.aid})" class="btn btn-default">
												${ass.title}</button>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<table width="100%">
								<tr>
									<td width="70%" class="text-center"></td>
									<td width="20%">
										<div class="input-group">
											<input id="probpage" type="text" class="form-control"
												style="width: 5em" placeholder="${crtpg}/${maxpg}">
											<button type="button" onclick="goToProblemsAtPage()"
												style="height: 2.4em" class="btn btn-default btn-md">
												<span class="glyphicon glyphicon-share-alt"></span>
											</button>
										</div>
									</td>
									<td width="10%">
										<div class="input-group-btn">
											<c:if test="${crtpg > 1}">
												<button id="upleftpagebtn" type="button"
													style="height: 2.4em" onclick="loadProblemsAtPage(0)"
													class="btn btn-default">
													<span class="glyphicon glyphicon-triangle-left"
														aria-label="Left"></span>
												</button>
											</c:if>
											<c:if test="${crtpg < maxpg}">
												<button id='uprightpagebtn' type="button"
													style="height: 2.4em" onclick="loadProblemsAtPage(1)"
													class="btn btn-default">
													<span class="glyphicon glyphicon-triangle-right"
														aria-label="Right"></span>
												</button>
											</c:if>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>

					<%
						for (Problem prob : myproblist) {
					%>
				
						<tr>
							<td id="pid<%=prob.getPid()%>" width="8%" class="text-center"><%=prob.getPid()%></td>
							<td width="84%" ><%=prob.getContent()%></td>
							<td><input type="text" id="keywordtext" name="keyword" placeholder="Add Category"/>
	                                <button type="submit" value="Submit">Add </button>
	                        </td>
						</tr>
					
					<%
						}
					%>
				
					<tr>
						<td colspan="3">
							<table width="100%">
								<tr>
									<td width="70%" class="text-center"></td>
									<td width="20%">
										<div class="input-group">
											<input id="probpage2" type="text" class="form-control"
												style="width: 5em" placeholder="${crtpg}/${maxpg}">
											<button type="button" onclick="goToProblemsAtPage()"
												style="height: 2.4em" class="btn btn-default btn-md">
												<span class="glyphicon glyphicon-share-alt"></span>
											</button>
										</div>
									</td>
									<td width="10%">
										<div class="input-group-btn">
											<c:if test="${crtpg > 1}">
												<button id="upleftpagebtn" type="button"
													style="height: 2.4em" onclick="loadProblemsAtPage(0)"
													class="btn btn-default">
													<span class="glyphicon glyphicon-triangle-left"
														aria-label="Left"></span>
												</button>
											</c:if>
											<c:if test="${crtpg < maxpg}">
												<button id='uprightpagebtn' type="button"
													style="height: 2.4em" onclick="loadProblemsAtPage(1)"
													class="btn btn-default">
													<span class="glyphicon glyphicon-triangle-right"
														aria-label="Right"></span>
												</button>
											</c:if>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>