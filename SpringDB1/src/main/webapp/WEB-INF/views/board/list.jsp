<%@page import="com.edu.db.domain.Board"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	List boardList=(List)request.getAttribute("boardList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>

<!-- Popper JS -->
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#bt_regist").click(function(){
		location.href="/board/registform";	
	});
	
});
</script>

</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">
				<table class="table">
					<thead class="thead-dark">
						<tr>
							<th>Firstname</th>
							<th>Lastname</th>
							<th>Email</th>
							<th>Email</th>
							<th>Email</th>
						</tr>
					</thead>
					<tbody>
						<%for(int i=0;i<boardList.size();i++){%>
						<%Board board=(Board)boardList.get(i); %>
						<tr>
							<td><%=i%></td>
							<td><a href="/board/detail?board_idx=<%=board.getBoard_idx()%>"><%=board.getTitle() %></a></td>
							<td><%=board.getWriter() %></td>
							<td><%=board.getRegdate() %></td>
							<td><%=board.getHit() %></td>
						</tr>
						<%} %>
						<tr>
							<td colspan="5">
								<button type="button" class="btn btn-info" id="bt_regist">글등록</button>								
							</td>
						</tr>
						
					</tbody>
				</table>

			</div>
		</div>
	</div>
</body>
</html>