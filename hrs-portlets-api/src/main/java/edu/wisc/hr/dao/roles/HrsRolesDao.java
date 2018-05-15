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

package edu.wisc.hr.dao.roles;

import java.util.Map;
import java.util.Set;

/**
 * Gets the employee's roles as understood by this application.
 */
public interface HrsRolesDao {

  public Set<String> getHrsRoles(String emplId);

  /**
   * Get the raw roles as delivered by HRS, before any within-app mapping.
   * @param emplId
   * @return potentially empty non-null Set of Strings representing HRS roles
   * @since 2.0
   */
  public Set<String> rawHrsRolesForEmplid(String emplId);


  /**
   * Get the general (not-empl-specific) mapping from raw HRS role to porlet role(s).
   * @return non-null Map of non-null Strings representing raw HRS roles to non-null non-empty
   * Sets of Strings representing portlet roles.
   * @since 2.0
   */
  public Map<String, Set<String>> getHrsRoleMappings();
}
