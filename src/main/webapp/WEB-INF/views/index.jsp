<%@ page language="java" contentType="text/html; charset=EUC-KR"
         pageEncoding="EUC-KR" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="EUC-KR">
    <title>URL ���� ����</title>
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
        <h2 class="main_title">�������� URL�� ������ѵ帳�ϴ�.</h2>
    </div>
    <div>
        <div class="content">
            <input type="text" name="urlInput" id="urlInput" placeholder="URL�� �Է����ּ���."/>
        </div>
        <div class="button">
            <input type="button" onclick="convert();" value="�����ϱ�"/>
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
                "<p>���: ����</p>" +
                "<p>������ URL: " + response.response.originUrl + "</p>" +
                "<p>����� URL: " + response.response.shortenUrl + "</p> " +
                "<p>��û Ƚ��: " + response.response.requestedCount + "</p> "
            )
          } else {
            $("#response").html(
                "<p>���: ����</p>" +
                "<p>����: " + getErrorMessage(response.code) + "</p>"
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