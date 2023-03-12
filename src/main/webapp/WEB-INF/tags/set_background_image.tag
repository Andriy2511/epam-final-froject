<%@ attribute name="image" required="true" %>
<style>
    body {
        background-image: url('${pageContext.request.contextPath}/background_images/${image}');
        background-size: contain;
    }
</style>
