<%@ page language="java" contentType="text/html; charset=EUC-KR"
         pageEncoding="EUC-KR" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="EUC-KR">
    <title>URL ���� ����</title>
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<div>
    ������ URL:
    <input id="urlInput"/>
    <input type="button" onclick="convert();" value="�����ϱ�"/>
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
                "<p>- ���: ����</p>" +
                "<p>- ������ URL: " + response.response.originUrl + "</p>" +
                "<p>- ����� URL: " + response.response.shortenUrl + "</p> " +
                "<p>- ��û Ƚ��: " + response.response.requestedCount + "</p> "
            )
          } else {

            $("#response").html(
                "<p>- ���: ����</p>" +
                "<p>- ����: " + getErrorMessage(response.code) + "</p>"
            )
          }
        }
      });
    }
  }

  function getErrorMessage(errorCode) {

    switch (errorCode) {
      case 10000:
        return "URL ������ ��ȿ���� �ʽ��ϴ�."
      case 10001:
        return "�����͸� ã�� �� �����ϴ�."
      default:
        return "����ġ ���� ������ �߻��Ͽ����ϴ�."
    }
  }

</script>