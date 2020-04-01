<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>1:1질문 답글 쓰기</title>
<script type="text/javascript" src="/js/regExUtil2.js"></script>
<script type="text/javascript">
$(function(){
	 $("#answerForm").submit(function(){

		 if(!inputDataCheck(title_RegEx, "#title", title_err_msg))
				return false;
			//내용- 데이터가 유효하지 않으면(!) 더이상 진행하지 않고 false 를 리턴한다.	
			if(!inputDataCheck(content_RegEx,"#content",content_err_msg))		 
				return false;
});
});
</script>

</head>
<body>
<h1 align="center"> 1:1대 질문 답변</h1>

<form action="answer.do" method="post" id="answerForm">


<div class="form-group">
    <input type="hidden" class="form-control" id="refNo" name="refNo" value="${dto.refNo}">
    <input type="hidden" class="form-control" id="ordNo" name="ordNo" value="${dto.ordNo}">
    <input type="hidden" class="form-control" id="levNo" name="levNo" value="${dto.levNo}">
    <input type="hidden" class="form-control" id="parentNo" name="parentNo" value="${dto.no}">
    <input type="hidden" class="form-control" id="id1" name="id1" value="${dto.id1}">
   
  </div>
<div class="form-group">
<label for="title">제목</label>
<input type="text" class="form-control" id="title" name="title" title="제목은 2글자 이상 입력해야합니다" value="[답변]${dto.title }">

</div>
<div class="form-group">
<label for="content">내용</label>

<textarea class="form-control" rows="10" cols="130" id="content" name="content">

${dto.content }
---------------------------------------------------
---------------------------------------------------
답변내용:

</textarea>

</div>
		<button>등록</button>
		<button type="reset">다시입력</button>
		<button type="button" onclick="history.back()">취소</button>
</form>
</body>
</html>