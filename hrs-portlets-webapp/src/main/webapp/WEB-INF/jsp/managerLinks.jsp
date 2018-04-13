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

<!-- <p>Your emplid is ${emplid}.</p> -->

<p>

  <sec:authorize
    ifNotGranted="ROLE_VIEW_MANAGED_ABSENCES,ROLE_VIEW_MANAGED_TIMES,ROLE_VIEW_TIME_ABS_DASHBOARD">
    You do not appear to have any manager roles.
  </sec:authorize>

  <ul>

    <sec:authorize ifAnyGranted="ROLE_VIEW_TIME_ABS_DASHBOARD">
      <li>
        <a href="${approvalsDashboardUrl}"
          target="_blank" rel="noopener noreferrer">
          ${approvalsDashboardLabel}
        </a>
      </li>
    </sec:authorize>

    <sec:authorize ifAnyGranted="ROLE_VIEW_MANAGED_ABSENCES">
      <li>
        <a href="${hrsUrls['Approve Absence']}"
          target="_blank" rel="noopener noreferrer">
          Approve absence
        </a>
      </li>
    </sec:authorize>

    <sec:authorize ifAnyGranted="ROLE_VIEW_MANAGED_TIMES">
      <li>
        <a href="${hrsUrls['Approve Payable time']}"
          target="_blank" rel="noopener noreferrer">
          Approve time
        </a>
      </li>
    </sec:authorize>

    <li>
      <a href="${helpUrl}"
        target="_blank" rel="noopener noreferrer">
        Help
      </a>
    </li>
  </ul>


</p>

  <%@ include file="/WEB-INF/jsp/footer.jsp"%>

</div>
