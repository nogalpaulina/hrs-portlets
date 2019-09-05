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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import edu.wisc.hr.dao.bnsumm.BenefitSummaryDao;
import edu.wisc.hr.dao.roles.HrsRolesDao;
import edu.wisc.hr.dm.bnsumm.BenefitSummary;
import org.jasig.springframework.security.portlet.authentication.PrimaryAttributeUtils;

/**
 *
 *
 * @author Eric Dalquist
 */
@Controller
@RequestMapping("VIEW")
public class BenefitSummaryDataController {
    private BenefitSummaryDao benefitSummaryDao;
    private HrsRolesDao hrsRolesDao;

    @Autowired
    public void setBenefitSummaryDao(BenefitSummaryDao benefitSummaryDao) {
        this.benefitSummaryDao = benefitSummaryDao;
    }

    @Autowired
    public void setHrsRolesDao(HrsRolesDao hrsRolesDao) {
      this.hrsRolesDao = hrsRolesDao;
    }

    @ResourceMapping("benefitSummary")
    public String getBenefitSummary(ModelMap modelMap) {
        final String emplid = PrimaryAttributeUtils.getPrimaryId();

        final BenefitSummary benefitSummary = this.benefitSummaryDao.getBenefitSummary(emplid);
        modelMap.addAttribute("benefits", benefitSummary.getBenefits());
        modelMap.addAttribute("dependents", benefitSummary.getDependents());
        modelMap.addAttribute(
          "enrollmentFlag", benefitSummary.getEnrollmentFlag());

        return "jsonView";
    }

    /**
     * Historically, an enrollmentFlag modeled whether the employee has a
     * benefit enrollment opportunity, whether of a personal event-driven nature
     * ("H", most often becase a new Hire), or the group annual benefit
     * enrollment opportunity ("O" for Open enrollment).
     *
     * More recently we've switched to using roles to model employee benefit
     * enrollment opportunities.
     *
     * @deprecated use enrollmentRole instead
     * @param modelMap
     * @return
     */
    @ResourceMapping("enrollmentFlag")
    public String getEnrollmentFlag(ModelMap modelMap) {
      final String emplid = PrimaryAttributeUtils.getPrimaryId();

      final BenefitSummary benefitSummary =
        this.benefitSummaryDao.getBenefitSummary(emplid);

      Map<String, String> enrollmentFlagMap = new HashMap<String, String>();

      enrollmentFlagMap.put(
        "enrollmentFlag", benefitSummary.getEnrollmentFlag());

      List<Map<String, String>> report = new ArrayList<Map<String, String>>();
      report.add(enrollmentFlagMap);

      modelMap.addAttribute("report", report);

      return "jsonView";
    }

    /**
     * Historically, an enrollmentFlag modeled whether the employee has a
     * benefit enrollment opportunity, whether of a personal event-driven nature
     * ("H", most often becase a new Hire), or the group annual benefit
     * enrollment opportunity ("O" for Open enrollment).
     *
     * More recently we've switched to using roles to model employee benefit
     * enrollment opportunities.
     *
     * This emulates the old enrollment flag data model, driving it by role.
     *
     * @param modelMap
     * @return "jsonView" indicating what view Spring MVC should render. Does
     *   NOT return the role; rather the role is added to the ModelMap as a side
     *   effect.
     */
    @ResourceMapping("enrollmentRole")
    public String enrollmentRole(ModelMap modelMap) {
      final String emplid = PrimaryAttributeUtils.getPrimaryId();

      final BenefitSummary benefitSummary =
        this.benefitSummaryDao.getBenefitSummary(emplid);

      Map<String, String> enrollmentFlagMap = new HashMap<String, String>();


      String enrollmentFlagFromRole = "Z";

      Set<String> roles = hrsRolesDao.getHrsRoles(emplid);

      if (roles.contains("ROLE_VIEW_NEW_HIRE_BENEFITS")) {
        enrollmentFlagFromRole = "H";
      } else if (roles.contains("ROLE_VIEW_OPEN_ENROLL_BENEFITS")) {
        enrollmentFlagFromRole = "O";
      }

      enrollmentFlagMap.put(
        "enrollmentFlag", enrollmentFlagFromRole);

      List<Map<String, String>> report = new ArrayList<Map<String, String>>();
      report.add(enrollmentFlagMap);

      modelMap.addAttribute("report", report);

      return "jsonView";
    }

}
