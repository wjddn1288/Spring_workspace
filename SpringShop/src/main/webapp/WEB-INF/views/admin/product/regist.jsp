<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>AdminLTE 3 | Dashboard</title>
<%@ include file="../inc/header_link.jsp"%>
<style type="text/css">
.box-style{
	width:90px;
	height:95px;
	border:1px solid #ccc;
	display:inline-block;
	margin:5px;
}
</style>
</head>
<body class="hold-transition sidebar-mini layout-fixed">
	<div class="wrapper">

		<!-- Preloader -->
		<%@ include file="../inc/preloader.jsp" %>
		
		<!-- Navbar -->
		<%@ include file="../inc/navbar.jsp" %>
		<!-- /.navbar -->

		<!-- Main Sidebar Container -->
		<%@ include file="../inc/sidebar_left.jsp" %>
		
		
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<div class="content-header">
				<div class="container-fluid">
					<div class="row mb-2">
						<div class="col-sm-6">
							<h1 class="m-0">상품등록</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Home</a></li>
								<li class="breadcrumb-item active">상품관리> 상품등록</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

			<!-- Main content -->
			<section class="content" id="app1">
				<div class="container-fluid">
				
					<!-- Main row -->
					<div class="row">
						<div class="col">
							
							<div class="form-group row">
								<div class="col">
									<select class="form-control" name="category_idx"></select>
								</div>
							</div>							
							
							<div class="form-group row">
								<div class="col">
									<input type="email" class="form-control" name="product_name" placeholder="상품명">
								</div>
							</div>							
							<div class="form-group row">
								<div class="col">
									<input type="email" class="form-control" name="brand" placeholder="브랜드명">
								</div>
							</div>							
							<div class="form-group row">
								<div class="col">
									<input type="number" class="form-control" name="price">
								</div>
							</div>					
									
							<div class="form-group row">
								<div class="col">
									<input type="number" class="form-control" name="discount">
								</div>
							</div>
								
							<div class="form-group row">
								<div class="col">
									<input type="file" class="form-control" name="file" multiple>
								</div>
							</div>
							
							<div class="form-group row">
								<div class="col">
									<template v-for="i in count">
										<imagebox />
									</template>
								</div>
							</div>
							
							
							<div class="form-group row">
								<div class="col">
									<textarea class="form-control" id="detail" name="detail"></textarea>
								</div>
							</div>
														
							<div class="form-group row">
								<div class="col-sm-1">
									<button type="button" class="btn btn-block btn-danger btn-lg">등록</button>							
								</div>
								<div class="col-sm-1">
									<button type="button" class="btn btn-block btn-danger btn-lg">목록</button>									
								</div>
							</div>							
							
						</div>
					</div>
					<!-- /.row (main row) -->
				</div>
				<!-- /.container-fluid -->
			
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		
		<%@ include file="../inc/footer.jsp" %>		

		<!-- Control Sidebar -->
		<%@ include file="../inc/sidebar_right.jsp" %>
		<!-- /.control-sidebar -->
	</div>
	<!-- ./wrapper -->
	<%@ include file="../inc/footer_link.jsp" %>
	<script type="text/javascript">
		let app1;
		
		const imagebox={
			template:`
				<div class="box-style">
					<div>X</div>
					<img src="" />
				</div>
			`,
			data(){
				return{
					
				};
			}
		};
		
		app1=new Vue({
			el:"#app1",
			components:{
				imagebox
			},
			data:{
				count:5,
				imageList:[]  //files(read only) 배열의 정보를  담아놓을 배열
			}
		});
		
		function preview(files){
			//정보를 분석하여  json으로 바꾼다...
			let json=[];
			json["key"]=0; //삭제시 사용..
			
		}
		
		//서머노트 적용하기 
		$(function(){
			$("#detail").summernote({
				height:200
			});
			
			//파일에 이벤트 연결 
			$("input[name='file']").change(function(){
				console.log(this.files);
				preview(this.files);
			});
			
		});
	
	</script>
</body>
</html>








