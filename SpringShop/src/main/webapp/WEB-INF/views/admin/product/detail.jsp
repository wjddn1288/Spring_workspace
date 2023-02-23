<%@page import="com.edu.springshop.domain.Category"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
	List<Category> categoryList = (List)request.getAttribute("categoryList");
%>
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
.box-style img{
	width:75px;
	height:70px;
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
							<h1 class="m-0">상품상세 정보</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Home</a></li>
								<li class="breadcrumb-item active">상품관리> 상품상세 정보</li>
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
									<select class="form-control" name="category_idx">
										<option value="0">카테고리 선택</option>
										<%for(Category category : categoryList){ %>
										<option value="<%=category.getCategory_idx()%>"><%=category.getCategory_name() %></option>
										<%} %>
									</select>
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
									<template v-for="json in imageList">
										<imagebox :key="json.key" :obj="json"/> <!-- json자체를 넘겨버림 -->
									</template>
								</div>
							</div>
							
							
							<div class="form-group row">
								<div class="col">
									<textarea class="form-control" id="detail" name="detail"></textarea>
								</div>
							</div>
														
							<div class="form-group row">
								<div class="col">
									<button type="button" class="btn btn-danger" id="bt_regist">등록</button>
									<button type="button" class="btn btn-danger" id="bt_list">목록</button>									
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
		let key=0; //이미지라서 디비연동 x
		
		const imagebox={
			template:`
				<div class="box-style">
					<div>X</div>
					<img :src="json.binary" />
				</div>
			`,
			props:["obj"],
			data(){
				return{
					json:this.obj
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
		
		/*------------------------------------------
		중복된 이미지 체크
		------------------------------------------*/
		function checkDuplicate(filename){
			let count=0;
			
			for(let i=0; i<app1.imageList.length;i++){
				let json=app1.imageList[i];
				if(json.name==filename){ //중복발견
					count++;
					break;
				}
			}
			
			return count;
		}
		
		/*------------------------------------------
		미리보기
		------------------------------------------*/
		function preview(files){
			
			//이미지 화면에 출력
			for(let i=0;i<files.length;i++){
				let file = files[i];
				
				if(checkDuplicate(file.name)<1){ //중복된 이미지가 없을때만...
					let reader = new FileReader();//스트림 생성
					reader.onload=(e)=>{
						
						key++; //사용자가 이미지를 선택할때마다 1씩 증가하여 중복을 불허한다
						
						let json=[]; // imageList배열에 복합적인 정보를 담아놓기 위해 
						json['key']=key;//추후 이미지 삭제시 기준값으로 사용예정 
						json['name']=file.name; //중복이미지가 추가되지 않도록... 
						json['binary']=e.target.result; //src에 대입할 바이너리 정보 
						json['file']=file; //전송할때 파라미터에 심을 파일
						
						app1.imageList.push(json);
					};
					reader.readAsDataURL(file);//파일읽기
				
				}
			}
		}
		
		/*------------------------------------------
		등록
		------------------------------------------*/
		function regist(){
			//파일업로드를 커스터마이징 시켰기 때문에...
			let formData = new FormData();
			
			formData.append("category.category_idx", $("select[name='category_idx']").val());
			formData.append("product_name", $("input[name='product_name']").val());
			formData.append("brand", $("input[name='brand']").val());
			formData.append("price", $("input[name='price']").val());
			formData.append("discount", $("input[name='discount']").val());
			formData.append("detail", $("textarea[name='detail']").val());
			
			//선택한 이미지 수만큼  formData 에 추가 
			for(let i=0;i<app1.imageList.length;i++){
				let json=app1.imageList[i];
				formData.append("photo", json.file);
			}
			
			$.ajax({
				url:"/admin/rest/product", 
				type:"post", 
				data:formData, 
				processData:false, /*query string 사용여부*/
				contentType:false, /* application/x-www-form~~ 사용여부*/
				success:function(result, status, xhr){
					alert(result.msg);
				}
			});
			
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
			
			//등록 이벤트 연결 
			$("#bt_regist").click(function(){
				regist();
			});
			
			$("#bt_list").click(function(){
				location.href="/admin/product/list";
			});
			
		});
	
	</script>
</body>
</html>








