<%@ include file="/WEB-INF/jsp/include.jsp"%>

<%@ tag dynamic-attributes="attributes" isELIgnored="false" %>

<c:if test="${not empty notification}">
  <c:forEach var='note' items="${notification}">
   <div class="fl-widget hrs-notification-wrapper alert alert-info">
       <div class="hrs-notification-content">${note}</div>
   </div>
  </c:forEach>
</c:if>
