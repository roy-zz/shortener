<%@ page language="java" contentType="text/html; charset=EUC-KR"
         pageEncoding="EUC-KR" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="EUC-KR">
    <title>URL 단축 서비스</title>
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<div>
    단축할 URL:
    <input id="urlInput"/>
    <input type="button" onclick="convert();" value="단축하기"/>
    <div id="response" style="max-width: 1024px;"></div>
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
                "<p>- 결과: 성공</p>" +
                "<p>- 단축전 URL: " + response.response.originUrl + "</p>" +
                "<p>- 단축된 URL: " + response.response.shortenUrl + "</p> " +
                "<p>- 요청 횟수: " + response.response.requestedCount + "</p> "
            )
          } else {

            $("#response").html(
                "<p>- 결과: 실패</p>" +
                "<p>- 사유: " + getErrorMessage(response.code) + "</p>"
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