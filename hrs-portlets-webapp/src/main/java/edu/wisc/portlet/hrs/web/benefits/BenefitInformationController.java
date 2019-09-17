/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package edu.wisc.portlet.hrs.web.benefits;

import edu.wisc.hr.service.benefits.AnnualBenefitEnrollmentDatesService;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import edu.wisc.hr.dao.bnsumm.BenefitSummaryDao;
import edu.wisc.hr.dao.roles.HrsRolesDao;
import edu.wisc.hr.dm.bnsumm.BenefitSummary;

import org.apache.commons.lang.StringUtils;
import org.jasig.springframework.security.portlet.authentication.PrimaryAttributeUtils;

import edu.wisc.portlet.hrs.web.HrsControllerBase;

/**
 * @author Eric Dalquist
 */
@Controller
@RequestMapping("VIEW")
public class BenefitInformationController extends HrsControllerBase {
    private BenefitSummaryDao benefitSummaryDao;
    private HrsRolesDao hrsRolesDao;

    private BenefitsLearnMoreLinkGenerator learnMoreLinker =
      new BenefitsLearnMoreLinkGenerator();

    private AnnualBenefitEnrollmentDatesService abeDates =
        new AnnualBenefitEnrollmentDatesService();

    @Autowired
    public void setBenefitSummaryDao(BenefitSummaryDao benefitSummaryDao) {
        this.benefitSummaryDao = benefitSummaryDao;
    }

    @Autowired
    public void setHrsRolesDao(HrsRolesDao hrsRolesDao) {
        this.hrsRolesDao = hrsRolesDao;
    }

    @RequestMapping
    public String viewBenefitInfo(ModelMap model, PortletRequest request) {
      @SuppressWarnings("unchecked")
      Map<String, String> userInfo = (Map <String, String>) request.getAttribute(PortletRequest.USER_INFO);
      final String emplId = PrimaryAttributeUtils.getPrimaryId();

      final String[] tabArray = request.getParameterMap().get("tab");
      String tab = "";
      if(tabArray!=null && tabArray.length == 1) {
        tab = tabArray[0];
      }

      final BenefitSummary benefitSummary = this.benefitSummaryDao.getBenefitSummary(emplId);
      model.addAttribute("enrollmentFlag", benefitSummary.getEnrollmentFlag());
      model.addAttribute("tab", tab);
      boolean isMadisonUser = !StringUtils.isBlank(userInfo.get("wiscEduHRSEmplid"));
      model.addAttribute("isMadisonUser", isMadisonUser);
      final PortletPreferences preferences = request.getPreferences();
      model.addAttribute("learnMoreEBenefitGuide", preferences.getValue("ebenefitguidetext", null));

      Set<String> roles = hrsRolesDao.getHrsRoles(emplId);
      model.addAttribute("learnMoreLink",
        learnMoreLinker.learnMoreLinkFor(roles, isMadisonUser));

      return "benefitInformation";
    }

    @ResourceMapping("benefitInformationWidget")
    public String benefitInformationWidget(
      ModelMap model, PortletRequest request) {

      final String emplId = PrimaryAttributeUtils.getPrimaryId();

      // same model as the portlet view JSP
      viewBenefitInfo(model, request);


      Set<String> roles = hrsRolesDao.getHrsRoles(emplId);
      LocalDate today = new LocalDate();

      if (roles.contains("ROLE_VIEW_NEW_HIRE_BENEFITS")) {
        return "benefitInformationWidgetPersonalEnrollmentEvent";

      } else if (abeDates.foreshadowAnnualBenefitsEnrollment(today)) {
        return "benefitInformationWidgetAnnualEnrollmentForeshadowing";

      } else if (roles.contains("ROLE_VIEW_OPEN_ENROLL_BENEFITS")) {
        if (abeDates.lastDayOfAnnualBenefitEnrollment(today)) {
          return "benefitInformationWidgetAnnualEnrollmentLastDay";
        }

        model.addAttribute("abeDaysRemaining",
            abeDates.daysRemainingInAnnualBenefitsEnrollment(today));
        return "benefitInformationWidgetAnnualEnrollmentDuringCountdown";

      } else if (abeDates.feedbackPeriod(today)) {
        return "benefitInformationWidgetAnnualEnrollmentFeedback";
      }

      return "benefitInformationWidget";

    }
}
