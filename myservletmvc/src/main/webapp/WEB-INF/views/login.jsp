<%--
  ~ Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
  ~ Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
  ~ Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
  ~ Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
  ~ Vestibulum commodo. Ut rhoncus gravida arcu.
  --%>

<%--
  Created by IntelliJ IDEA.
  User: guojun.wang
  Date: 2017/6/15
  Time: 11:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
    <title>zp login</title>
    <script src="/js/jquery-1.8.2.js"></script>
    <script>
        var uuid = "";
        $(document).ready(function () {
            var url = "http://192.168.7.227:8013/passport/qrid?jsonpcallback=?";

            $.getJSON(url, function (data) {
                console.log(data);
                uuid = data;

                var qrurl = "http://192.168.7.227:8013/passport/qrimage?size=180&uuid=" + uuid;

                $("#img1").attr("src", qrurl);
                setTimeout(checkQRStatus, 5000);
            });
        });
        function checkQRStatus() {
            var checkUrl = "http://192.168.7.227:8013/passport/qrauthstatus?jsonpcallback=?&uuid=" + uuid;
            $.getJSON(checkUrl, function (model) {
                console.log(model)
                if (model.code == 3) {
                    console.log(model.data);


                    window.location.href = "http://ihr.zhaopin.com";

                    return;
                }
                setTimeout(checkQRStatus, 5000);
            });
        }
    </script>
</head>
<body>
<h1>this is login page</h1>
<h2>server name is : ${serverName}</h2>
QR:
<img id="img1"/>

</body>
</html>
