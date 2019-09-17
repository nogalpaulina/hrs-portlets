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

<portlet:defineObjects/>
<spring:htmlEscape defaultHtmlEscape="true" />

<div class="widget-body layout-align-center-center layout-column">
  <span>PLACEHOLDER CONTENT</span>
</div>

<c:choose>
  <c:when test="${isMadisonUser}">
    <a
      aria-label="Launch Benefit Information"
      href="/web/exclusive/university-staff-benefits-statement"
      class="launch-app-button">
      Launch full app
    </a>
  </c:when>
  <c:otherwise>
    <a
      aria-label="Launch Benefit Information"
      href="/web/exclusive/uw-system-university-staff-benefits-statement"
      class="launch-app-button">
      Launch full app
    </a>
  </c:otherwise>
</c:choose>
