<%--
  Created by IntelliJ IDEA.
  User: ttc
  Date: 2018/7/6
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>查查谁</title>
  <style>
    html {
      height: 100%;
      overflow-y: scroll;
    }

    body {
      margin: 0px;
      padding: 0px;
      height: 100%;
    }

    .a-none-decoration {
      text-decoration: none;
    }

    #wrapper {
      height: 100%;
      min-width: 1000px;
    }

    #s-skin-container {
      height: 100%;
      width: 100%;
      min-width: 1000px;
      -webkit-background-size: cover; /*随浏览器大小改变背景图大小*/
      background-size:cover;/*随浏览器大小改变背景图大小*/
      background-color: rgb(255,255,255);
      background-image: url("https://ss3.bdstatic.com/iPoZeXSm1A5BphGlnYG/skin/486.jpg") ;
      background-repeat:no-repeat;
      position: fixed;
      left: 0;
      top: 0;
      z-index: -10;
    }

    #s-container {
      width: 641px;
      margin: 0 auto;
      text-align: center;
      padding-top: 80px;
    }

    #s-container #s_kw_wrap {
      position: relative;
    }

    #s-container .s_ipt {
      width: 480px;
      padding: 10px 50px 10px 7px;
    }

    #s-container .s_btn {
      width: 100px;
      height: 38px;
      cursor: pointer;
      font-size: 16px;
    }

    #s-container .soutu-btn {
      display: inline-block;
      height: 16px;
      width: 18px;
      position: absolute; /*默认绝对定位是相对浏览器，只有当父元素设置为相对定位，那么绝对定位才能针对父元素*/
      right: 10px;
      top: 1px;
      background-image: url(https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/static/protocol/https/soutu/img/camera_new_5606e8f.png);
    }

    #s-container .soutu-btn:hover {
      background-position: 0 -20px;
    }
  </style>
</head>
<body>
<div id="wrapper">
  <div id="s-skin-container"></div>
  <div id="s-container">
    <img id="s_lg_img" src="${pageContext.request.contextPath}/static/images/jianshubg.png" width="225" height="168"><br/><br />
    <form action="${pageContext.request.contextPath}/user/showUser.action" method="post">
      <input type="text" id="url" name="slug" class="s_ipt" maxlength="100" placeholder="将你的简书用户的ID贴在这里，然后提交">
      <a href="javascript:;" id="quickdelete" title="清空" class="quickdelete" style="top: 0px; right: 0px;"></a>
<!--       <select name="moshi"> -->
<!--       <option value="ks">快速获取结果</option> -->
<!--       <option value="zx">最新结果</option> -->
<!--       <option></option> -->
<!--       </select> -->

      <input type="submit" value="提交" class="s_btn">

    </form>
  </div>
</div>
</body>
</html>
