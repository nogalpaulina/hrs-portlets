# MyUW hrs-portlets change log

### Next release

### 3.1.0: targeted notifications and notices for PHIT

New features:

+ In Time and Absence, a new optional `portlet-preference`
  `dynPunchTimesheetNotification` drives a new in-HRS-app message (presented
  just like `notification`) to employees with the new
  `ROLE_UW_DYN_AM_PUNCH_TIME` role. ([HRSPLT-346][], [#123][])
+ In Time and Absence, a new optional `portlet-preference`
  `nonDynPunchTimesheetNotification` drives a new in-HRS-app message (presented
  just like `notification`) to employees who see the Timesheet button but who
  do not have the new `ROLE_UW_DYN_AM_PUNCH_TIME` role. ([HRSPLT-346][],
  [#123][])
+ In Time and Absence, a new optional `portlet-preference`
  `dynPunchTimesheetNotice` drives a new message near the Timesheet button
  (presented similarly to `timesheetNotice`) to employees with the new
  `ROLE_UW_DYN_AM_PUNCH_TIME` role. ([HRSPLT-346][], [#123][])

Changes:

+ In Time and Absence, the `leaveReportingNotice` when present is now ordered
  ahead of general `hrs:notifications` in-app notifications when present.
  ([HRSPLT-348][], [#122][])

### 3.0.0: split `ROLE_VIEW_ABSENCE_HISTORIES`

BREAKING CHANGE:

The `ROLE_VIEW_ABSENCE_HISTORIES` portlet role now *only*
grants access to view absence histories and does *not* grant access to the
"Enter absence" and "Edit/cancel absence" buttons. (Access to those buttons is
now granted by new portlet role `ROLE_ENTER_EDIT_CANCEL_OWN_ABSENCES`).

Migration path: to the extent that employees ought to continue to have all the
privileges of the prior implementation of `ROLE_VIEW_ABSENCE_HISTORIES`, also
grant those employees `ROLE_ENTER_EDIT_CANCEL_OWN_ABSENCES`. ( #121 )

New features

+ Add new portlet role `ROLE_ENTER_EDIT_CANCEL_OWN_ABSENCES`, granting access to
  the "Enter absence" and "Edit/cancel absence buttons". This access is a subset
  of what `ROLE_VIEW_ABSENCE_HISTORIES` historically granted. ( #121 )
+ Add role mapping for new `UW_DYN_AM_PUNCH_TIME` HRS role. This HRS role grants
  `ROLE_VIEW_ABSENCE_HISTORIES` portlet role but does *not* grant new
  `ROLE_ENTER_EDIT_CANCEL_OWN_ABSENCES` portlet role. ( #121 )

Changes

+ Update role mapping for `UW_DYN_AM_EMPLOYEE` HRS role to grant the new
  `ROLE_ENTER_EDIT_CANCEL_OWN_ABSENCES` portlet role in addition to the
  `ROLE_VIEW_ABSENCE_HISTORIES` portlet role it was already granting. This
  yields *no functional change* for employees with the `UW_DYN_AM_EMPLOYEE` HRS
  role. While `ROLE_VIEW_ABSENCE_HISTORIES` is losing privileges in this
  release, these employees regain those very same privileges via the
  `ROLE_ENTER_EDIT_CANCEL_OWN_ABSENCES` role. ( #121 )

### 2.1.1 : Eagerly resolve leave reporting notice div conditional

2018-06-25

Fixed

+ Eagerly resolve whether to show leave reporting notice div ( #119 )

### 2.1.0

2018-06-04

New features

+ Add `leaveReportingNotice` feature in Time and Absence ( #118 )
+ Style HRS roles as <em>emphasized</em> as used as sentences in Troubleshooter ( #116 )


## 2.0.0 : Troubleshooter with more detail

2018-05-16

**Breaking change:** change to the Roles DAO API to add two methods, supporting

 + Enhanced Troubleshooting portlet, now with what HRS roles are known, the general mappings from 
   HRS roles to Portlet roles, and a link to documentation ( #115 )

### 1.12.0 : Roles troubleshooter

2018-05-14

New features

+ Add `Troubleshooting` portlet ( #114 )
+ Require secure database connections by setting `;oracle.net.encryption_client=REQUIRED;` on 
  `connectionProperties` ( 1b749187006099bea1448ac8a29e5cd69eb3f62a )

### 1.11.0 : Configurable Manager Time and Approval link labels

2018-04-16

Adjusts defaults for Manager Time and Approval dynamic hyperlink labels, and makes these labels 
configurable via `portlet-preference`.

+ Title case and make configurable Manager "Approve Absence" and "Approve Time" link labels ( #111 )
+ shorten "Time/Absence Dashboard" label ( #112 )

### 1.10.0 : Dynamic Manager Time and Approval fixes and improvements

2018-04-13

Feature changes

+ Re-orders "HRS Manager Time and Approval" links to put "Approve absence" ahead of "Approve time". 
  This is intended to better match typical manager approval workflows and a customization also made 
  in the new HRS self-service Fluid approvals dashboard. ( #109 )
+ Shortens the default label for the new manager approval dashboard link in "Manager Time and 
  Approval" to "Time & Absence Dashboard" so that it will not run over the 24 character limit and be
  truncated by the `list-of-links` widget type. ( #110 )

Bug fixes

+ Fix the "Approve time" link in the new dynamic Manager Time and Approval manager links ( #107 )
+ Fix URLs JSON to actually include the URLs (values) and not just the keys. That is, to make the 
  list-of-link representation of this links that are actual hyperlinks that link to the URLs. ( #108
  )

#### 1.9.1 : Fix display of a button in Time and Absence

2018-04-12

Patch release to fix a display issue on a button in the Time And Absence app.

### 1.9.0 : Dynamic manager links widget

2018-03-29

New features: ManagerLinks portlet

+ `ManagerLinks` portlet, exposing a resource URL vending JSON in `list-of-links` format, 
  representing role-filtered links available to the manager to launch into HRS self-service to 
  approve time and/or absences ( #97, #100, #101, #102 )
+ Optional approvals dashboard link, with its label and URL specified via portlet preference, 
  predicated on new `UW_DYN_TIME_ABS_DASH_USER` HRS role ( #99, #103,  #104 )
+ Also offers equivalent rendered view, suitable for linking from the generated `list-of-links` 
  widget.

New features: Roles portlet

+ `Roles` portlet, exposing a resource URL vending JSON in `list-of-links` format, representing the 
  user's HRS roles as understood by (mapped to) the HRS Portlets (  #96 ).
+ Also offers equivalent rendered view, suitable for linking from the generated `list-of-links` 
  widget.

New features:: Urls portlet

+ `Urls` portlet, exposing a resource URL vending JSON in `list-of-links` format, representing the 
  HRS URLs as understood by the HRS Portlets
+ Also offers equivalent rendered view, suitable for linking from the generated `list-of-links` 
  widget.

Style

+ Trivial whitespace fixes ( #98 )

### 1.8.0 : Approvals count JSON API

2018-02-14

New features:

+ JSON API to read quantity of viewer's pending approvals ( #94 )
+ Configurable link for "your human resource contact" in Personal Information ( #95 )

### 1.7.0 : Tweaks including updated year-end-leave-balances label

2018-01-12

Changes:

+ Update year-end-leave-balances label for end of 2017. ( #92 )

Fixes:

+ Removes duplicate zero-balance vacation carryover entries in leave balances ( #87 )
+ Changes advice about changing "Business/Office Address" to contact "your human resources office" 
  rather than "your Payroll Office". ( #91 )
+ Revise Benefit Statement ordering ( #93 )

#### 1.6.5 : Change spacing

2017-11-03

+ Fixes spacing of paragraphs ( #86 )

#### 1.6.4 : More conservative Personal Information presentation

2017-10-26

There are no functional or configuration changes in this release.

+ Makes more conservative the HRS PUM22 changes previously made in ( #75 ) characterizing and 
  linking the self-service UI for updating personal information in HRS. ( #85 )

#### 1.6.3 : further fix new direct deposit self-service link feature

2017-10-24

1.6.0's new feature ( #80 ) was bugged. 1.6.1 tried to fix this, but it was still bugged. Further 
fixes with ( #82 )

#### 1.6.2 : unpublished

This release did not successfully publish. Its fix is documented above in 1.6.3.

#### 1.6.1 : failed attempt at fixing bugged self-service direct deposit linking

2017-10-24

Attempted to fix bugged ( #80 ) with ( #81 ), but it didn't work.

### 1.6.0 : bugged link to HRS self-service direct deposit UI

2017-10-24

BUGGED. (Use `1.6.3` or later instead.)

This release adds one feature: support for an optional HRS role granting access to the new 
self-service direct deposit management UI in PeopleSoft 9.2 PUM22. ( #80 ).

This is entirely backwards compatible and fails gracefully, falling back on v1.5.0 behavior:

+ If the viewing employee does not have the new role (HRS role `UW_DYN_PY_DIRDEP_SS`, mapped to an 
  hrs-portlets role of `ROLE_VIEW_DIRECT_DEPOSIT`), the UI is unchanged, continuing to link a PDF 
  form for direct deposit updating (as hardcoded in the JSP).
+ If the new portlet-preference `directDepositSelfServiceUrl` is not set, *even if the employee has 
  the new role*, the UI is unchanged, continuing to link a PDF form for direct deposit updating (as 
  hardcoded in the JSP).

If the employee has the role and the portlet-preference is set, the direct deposit link in the UI 
uses the URL configured in the portlet preference (intention: the HRS self-service URL) rather than 
the previously hard-coded URL to the PDF form.

(The portlet preference could be configured with any URL, including with the URL to the PDF form, so
that's another way to turn off this new feature in a pinch.)

### 1.5.0 : Benefit Summary URL portlet preference

2017-10-12

New feature:

+ Optionally, the benefit summary URL link in Benefit Information can be specified via the new 
  `benefitsSummaryUrl` portlet-preference, overriding any value coming through the HRS URLs SOAP 
  integration. ( #78 as fixed in #79 ).

Backwards compatible: don't set the new preference and the portlet continues to function as in 
`1.4.0`.

### 1.4.0 : add Edit/Cancel Absences button

New feature:

+ Adds support in Time and Absence for new optional `editCancelAbsenceUrl` portlet-preference . When
  set, shows new Edit/Cancel Absence button to launch the URL described by that portlet-preference 
  value. When not set, no-op. Intended to support graceful upgrade to HRS PUM22 self-service UIs. ( 
  \#76 )

Fix:

+ `1.3.0` was bugged bug such that when the Personal Information self-service launch URL was 
  configured via the new-in-that-release optional `updateMyPersonalInfoUrl` portlet-preference, the 
  resulting UI included broken hyperlinks. This release fixes that bug. ( #77 )

### 1.3.0 : add Personal Information self-service URL portlet-preference

2017-10-06

New feature:

+ honor optional `updateMyPersonalInfoUrl` portlet preference superseding corresponding URL from HRS
  SOAP web service ( #75 )

This release adds support for overriding the URL linking to HRS self-service UI for updating 
personal information, via a new `updateMyPersonalInfoUrl` portlet preference.

+ When the preference is set, the UI reflects the new HRS PUM22 context (text, other links around) 
  for the link.
+ When the preference is not set, the portlet continues to get this URL from the HRS URLs SOAP web 
  service with its prior context (text, other links around) the link.
+ This release also adds an error state for when the URL is available neither from the portlet 
  preference nor from the HRS URLs web service. This edge case is not expected to actually happen in
  practice.

### 1.2.0 : enrollmentFlag API and service window message

2017-09-29

New features:

+ Add enrollmentFlag resource mapping (portlet resource URL) ( #74 )
+ Note HRS service window in footers within the portlet UIs ( #72 )
+ Add trace logging in enrollment flag parsing ( #73 )


#### 1.1.3 : workaround fixing Leave Reports column disappearance

2017-09-21

Fix:

+ Workaround to resolve race condition affecting display of Leave Reports column ( #71 )


#### 1.1.2 : add enrollmentFlag to benefitSummary JSON API; rm furlough balance

2017-07-24

New features:

+ Adds `enrollmentFlag` to `benefitSummary` resource URL ( #69 )
+ Filters furlough balance from leave balances, if furlough balance is zero ( #70 )
+ Documents `enrollmentFlag` values ( #68 )

#### 1.1.1 : style a link

2017-03-20

+ Changes styling of a link to look like a button ( #67 )

### 1.1.0 add HRS URLs API

2017-01-09

New features:

+ Adds endpoint to retrieve HRS URLs ( #63 )
+ Adds log statement when interpreting a null response as an empty set when retrieving and 
  translating roles ( #65 )

Fixes:

+ Updates year-end leave balances label to 2016 text ( #64 )
+ Fixes bug where color labels didn't appear ( #63 )

#### 1.0.0.38...

This and many more earlier releases exist as [releases in the GitHub repo][].


[releases in the GitHub repo]: https://github.com/UW-Madison-DoIT/hrs-portlets/releases

[#122]: https://github.com/UW-Madison-DoIT/hrs-portlets/pull/122
[#123]: https://github.com/UW-Madison-DoIT/hrs-portlets/pull/123

[HRSPLT-346]: https://jira.doit.wisc.edu/jira/browse/HRSPLT-346
[HRSPLT-348]: https://jira.doit.wisc.edu/jira/browse/HRSPLT-348