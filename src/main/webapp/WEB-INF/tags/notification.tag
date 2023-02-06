<%
    String notification = request.getParameter("NOTIFICATION");
    if(notification == null){
        notification = "";
    }
    out.println(notification);
%>