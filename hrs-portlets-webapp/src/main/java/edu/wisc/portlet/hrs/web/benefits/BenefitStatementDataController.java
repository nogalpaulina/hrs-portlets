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

import edu.wisc.hr.dm.benstmt.BenefitStatementIssuedComparator;
import edu.wisc.hr.dm.benstmt.BenefitStatementNameComparator;
import edu.wisc.hr.dm.benstmt.BenefitStatementYearComparator;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.jasig.springframework.web.client.PortletResourceProxyResponse;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import edu.wisc.hr.dao.benstmt.BenefitStatementDao;
import edu.wisc.hr.dm.benstmt.BenefitStatement;
import edu.wisc.hr.dm.benstmt.BenefitStatements;
import edu.wisc.portlet.hrs.util.HrsDownloadControllerUtils;

import org.jasig.springframework.security.portlet.authentication.PrimaryAttributeUtils;

/**
 * 
 * 
 * @author Eric Dalquist
 */
@Controller
@RequestMapping("VIEW")
public class BenefitStatementDataController {
    private BenefitStatementDao benefitStatementDao;
    private Set<String> ignoredProxyHeaders;
    
    @Resource(name="ignoredProxyHeaders")
    public void setIgnoredProxyHeaders(Set<String> ignoredProxyHeaders) {
        this.ignoredProxyHeaders = ignoredProxyHeaders;
    }

    @Autowired
    public void setBenefitStatementDao(BenefitStatementDao benefitStatementDao) {
        this.benefitStatementDao = benefitStatementDao;
    }
    
    /**
     * Sorts the provided list of statements by
     * "Issued" date desc, name asc, then by year desc.
     * 
     * Intended functionality is to split Enrollment Confirmations and ETF's and
     * and list newest first.
     * 
     * CAUTION: Works via side-effects
     * @param statements 
     */
    protected static void sortStatements(List<BenefitStatement> statements) {
        ComparatorChain chainSort = new ComparatorChain();
        chainSort.addComparator(new BenefitStatementIssuedComparator(), true);
        chainSort.addComparator(new BenefitStatementNameComparator());
        chainSort.addComparator(new BenefitStatementYearComparator(), true);
        
        Collections.sort(statements,chainSort);
    }
    
    @ResourceMapping("benefitStatements")
    public String getBenefitStatements(PortletRequest portletRequest, ModelMap modelMap) {
        final String emplid = PrimaryAttributeUtils.getPrimaryId();

        Map<String, Object> userAttributeMap =
          (Map<String, Object>) portletRequest.getAttribute(PortletRequest.USER_INFO);
        String etfMemberId = (String) userAttributeMap.get("eduWisconsinETFMemberID");

        final BenefitStatements benefitStatements = this.benefitStatementDao.getBenefitStatements(emplid, etfMemberId);

        final List<BenefitStatement> statements = benefitStatements.getBenefitStatements();
        sortStatements(statements);
        modelMap.addAttribute("report", statements);
        
        return "reportAttrJsonView";
    }

    @ResourceMapping("benefits.pdf")
    public void getBenefitStatement(
            PortletRequest portletRequest,
            @RequestParam("mode") String mode,
            @RequestParam("docId") String docId, 
            @RequestParam("year") int year,
            ResourceResponse response) {

        final String emplid = PrimaryAttributeUtils.getPrimaryId();

        Map<String, Object> userAttributeMap =
          (Map<String, Object>) portletRequest.getAttribute(PortletRequest.USER_INFO);
        String etfMemberId = (String) userAttributeMap.get("eduWisconsinETFMemberID");

        HrsDownloadControllerUtils.setResponseHeaderForDownload(response, "benefits", "PDF");
        this.benefitStatementDao.getBenefitStatement(emplid, etfMemberId, year, docId, mode, new PortletResourceProxyResponse(response, ignoredProxyHeaders));
    }

}
