<%@ page language="java" contentType="text/html; charset=EUC-KR"
         pageEncoding="EUC-KR" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="EUC-KR">
    <title>URL 단축 서비스</title>
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <style>
        html,body {margin:0; padding:0;, overflow-y: scroll; background-color: #F2ECD8}
        .shortener {padding: 30px 0px; background-color: #D9AE79;}
        .shortener .main_title {text-align: center; color: #13402F;}
        .shortener .content {text-align: center; margin-bottom:10px; height: 50px}
        .shortener .content input[type=text] {width:50%; height:40px;background:white; text-indent: 15px; font-size:17px;}
        .shortener .button {text-align: center; margin-bottom: 10px; height: 50px}
        .shortener .button input[type=button] {width: 20%; line-height:20px; padding:10px 60px;color:#22306B; font-size: 20px; font-weight:800; background:#F2ECD8; border-radius: 80px / 40px;}
        .shortener .response {text-align: center; margin-bottom:10px; heigth: 100px}
    </style>

</head>
<body>
<div class="shortener">
    <div>
        <h2 class="main_title">여러분의 URL을 단축시켜드립니다.</h2>
    </div>
    <div>
        <div class="content">
            <input type="text" name="urlInput" id="urlInput" placeholder="URL을 입력해주세요."/>
        </div>
        <div class="button">
            <input type="button" onclick="convert();" value="단축하기"/>
        </div>
        <div class="response">
            <div id="response"></div>
        </div>
    </div>
</div>
</body>
</html>

<script type="text/javascript">

  function convert() {
    const text = $("#urlInput").val();
    if (text != null) {

      $.ajax({
        type: "get",
        url: "/url/shorten",
        data: {originUrl: text},
        success: function (response) {

          if (response.code == 200) {
            $("#response").html(
                "<p>결과: 성공</p>" +
                "<p>단축전 URL: " + response.response.originUrl + "</p>" +
                "<p>단축된 URL: " + response.response.shortenUrl + "</p> " +
                "<p>요청 횟수: " + response.response.requestedCount + "</p> "
            )
          } else {
            $("#response").html(
                "<p>결과: 실패</p>" +
                "<p>사유: " + getErrorMessage(response.code) + "</p>"
            )
          }
        }
      });
    }
  }

  function getErrorMessage(errorCode) {

    switch (errorCode) {
      case 10000:
        return "URL 형식이 유효하지 않습니다."
      case 10001:
        return "데이터를 찾을 수 없습니다."
      default:
        return "예상치 못한 에러가 발생하였습니다."
    }
  }

</script>