<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div id="${n}dl-time-absence" class="fl-widget portlet dl-time-absence hrs">

  <portlet:renderURL var="personLookupUrl"></portlet:renderURL>
  <form class="user-email-search" action="${personLookupUrl}" method="post">
    <fieldset>
      <div>
        <label for="queriedEmplId">HR EmplID: </label><input type="text" name="queriedEmplId" />
      </div>
      <input type="submit" value="Lookup roles for employee by emplId" />
    </fieldset>
  </form>

<c:if test="${not empty queriedEmplId}">
<p>emlpId ${queriedEmplId} has these HRS Portlets (MyUW app) roles:</p>
<ul>
<c:forEach var="role" items="${roles}">
  <li>${role}</li>
</c:forEach>
<c:if test="${empty roles}">
  <li>(none)</li>
</c:if>
</ul>
</c:if>

  <%@ include file="/WEB-INF/jsp/footer.jsp"%>

</div>
