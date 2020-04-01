<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/js/regExUtil2.js"></script>
<script type="text/javascript" src="/js/jquery-3.4.1.min.js"></script>
<style type="text/css">
#allCheck, #inputId{
	float: right;
}
</style>
<script type="text/javascript">
$(function(){
	// admin계정일 경우에만 전체메세지 체크박스 show
	// id에 저장 할 변수는 session에서 받은 id, EL객체를 ""로 묶으니까 undefined 해결. 이유는 잘 모름
	var id = "${login.id}";
// 	alert(id);
	if(id == "admin"){
		$("#allCheckDiv").show();
	} else {
		$("#allCheckDiv").hide();
	}
	
	var check = "${param.nosend }";
	if(check == 1){
		alert("존재하지 않는 회원의 아이디를 입력하셨습니다.\n받는 사람의 아이디를 다시 확인해 주세요.");
		$("#accepter").focus();
	}

	$("#allCheck").click(function(){
		// 전체메세지 보내기를 체크하면 받는사람 숨기기
		if($("#allCheck").prop("checked")){
			$("input[name=accepter]").hide();
			$("#pageType").val("all");
		} else {
			// 체크가 해제되면 받는사람 보이기
			$("input[name=accepter]").show();
		}
	});

	//전체 메시지 체크박스가 체크되면 accCheck 변수의 value를 1로 바꿈
	$("#allCheck").change(function(){
		$("#accCheck").val("1");
	});
	
	// reg 검사
	$("#writeForm").submit(function() {
		if(!inputDataCheck(title_RegEx, "#title", title_err_msg))
			return false;
		if(!inputDataCheck(content_RegEx, "#content", content_err_msg))
			return false;
		if($("#accCheck").val() != 1){
			if(!inputDataCheck(acc_RegEx, "#accepter", acc_err_msg))
				return false;			
		} else {
			return true;
		}
	});
	
	var resend = "${param.resend }";
	var accepter = "${param.sender }"
	if(resend == 1){
		$("#accepter").val(accepter);
		$("#accepter").attr("readonly", "readonly");
	}
	
});
</script>
</head>
<body>
<div class="container">
  <h2>메시지 작성</h2>
  <form action="write.do" method="post" id="writeForm">
  	<%-- 전체 메시지를 보내는 경우 체크박스를 체크한다. 체크하면 accCheck변수의 value=1, 체크하지 않으면 value=0(default) --%>
  	<input type="hidden" id="accCheck" name="accCheck" value="0"/>
  	<input type="hidden" id="pageType" name="pageType"/>
	<input name="perPageNum" value="${param.perPageNum }" type="hidden">
    <div class="form-group">
      <%-- 제목을 입력 --%>
      <label for="title">제목:</label>
      <input type="text" class="form-control" id="title" name="title" placeholder="Enter title"/>
    </div>
    <%-- 받는사람 입력 --%>
    <div class="form-group">
      <div>
      	<label for="accepter">받는 사람:</label>
      	<%-- 전체메세지를 보내는 경우 체크박스를 체크 --%>
      	<div id="allCheckDiv"><small id="inputId">전체메시지</small><input type="checkbox" id="allCheck" name="allCheck"/></div>
      </div>
      <input type="text" class="form-control" id="accepter" name="accepter" placeholder="여러 명은 ,(쉼표)를 이용해서 구분 "/>
    </div>
    <%-- 내용 입력 --%>
    <div class="form-group">
      <label for="content">내용:</label>
      <textarea rows="10" cols="50" class="form-control" id="content" name="content" placeholder="Enter content"></textarea>
    </div>
    <%-- 버튼 --%>
    <button type="submit" class="btn btn-default">보내기</button>
    <button type="reset" class="btn btn-default">다시 입력</button>
    <button type="button" class="btn btn-default" onclick="history.back()">목록</button>
  </form>
</div>
</body>
</html>