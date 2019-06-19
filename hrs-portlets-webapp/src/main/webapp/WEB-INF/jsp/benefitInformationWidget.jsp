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
  <sec:authorize
    ifAnyGranted="ROLE_VIEW_NEW_HIRE_BENEFITS, ROLE_VIEW_OPEN_ENROLL_BENEFITS">
    <span class="tsc__title">You have
      a<sec:authorize ifAnyGranted="ROLE_VIEW_OPEN_ENROLL_BENEFITS">n annual</sec:authorize>
      benefit enrollment opportunity.</span>

    <div class="tsc__status">
      <p>
        <a
          class="md-button md-accent md-raised md-ink-ripple"
          href="${hrsUrls['Open Enrollment/Hire Event']}">
          Enroll now
        </a>
      </p>
    </div>
  </sec:authorize>
  <div class="tsc__extra-buttons layout-align-center-center layout-row">
    <c:if test="${not empty learnMoreUrl}">
      <a
        aria-label="Learn more about benefits"
        target="_blank" rel="noopener noreferrer"
        href="${learnMoreUrl}">
        Learn more
      </a>
    </c:if>
  </div>
</div>

<c:choose>
  <c:when test="${isMadisonUser}">
    <%-- Bug: will launch the wrong Benefit Information for Madison users
         in context of my.wisconsin --%>
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
