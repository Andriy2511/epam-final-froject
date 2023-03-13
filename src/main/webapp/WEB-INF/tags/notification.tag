<%@ tag import="com.example.finalproject.locale.InternationalizationMessage" %><%
    String notification = request.getParameter("NOTIFICATION");
    if(notification == null){
        notification = "";
    }

    notification= InternationalizationMessage.printMessage(request, notification);
    out.println(notification);
%>