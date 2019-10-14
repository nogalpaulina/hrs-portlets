# URLS sourced from the HRS URLs web service

The keys, meaning, and usages of the URLs sourced from the HRS URLs web service.

**Not necessarily a complete list of URL keys supported from HRS.**
This list seeded from the URL keys actually received from HRS in production.

See also the [HRS URLs troubleshooting app][],
which lists the URL key-value pairs currently received from HRS.

Note that all HRS URLS are available via

`my.wisc.edu/HRSPortlets/go?urlKey={KEY}`

and

`my.wisconsin.edu/HRSPortlets/go?urlKey={KEY}`

regardless of other usage.

## `1095-C Consent` (optional)

Iff this HRS URL is present, Payroll Information will include the
"Consent to receive 1095-C electronically" link on the "Tax Statements" tab.

## `Approvals`

No effect.

## `Approve Absence` (required)

This is the URL for the "Approve Absence" link in the Manager Time and Approval
app and list-of-links widget.

## `Approve Payable time` (sic) (optional)

Note that A in Approve and P in Payable are capitals,
but t in time is lowercase.

Deep link into HRS self-service to the page where supervisors approve time
reported by their supervisees.

Example: `https://www.hrs.wisconsin.edu/psp/hrs-fd/EMPLOYEE/HRMS/c/ROLE_MANAGER.TL_SRCH_APPRV_GRP.GBL`

When present, and the viewing employee has `ROLE_VIEW_MANAGED_TIMES`,
is the URL for the "Approve time" link in
the "Manager Time and Approval" widget and app.

When absent, the "Approve time" link disappears from the
"Manager Time and Approval" widget and app.

## `Benefit Details`

No effect.

## `Benefits Summary` (required)

This is the URL of the "View Benefits Summary Detail" link in
Benefit Information, when not overridden by `benefitsSummaryUrl`
portlet-preference.

## `Classic ESS Abs Bal` (optional)

When present (and the viewing employee has `ROLE_LINK_TO_CLASSIC_ESS_ABS_BAL`),
is the URL of the "View Leave Balances" link in "Time and Absence".

When absent, the "View Leave Balances" link disappears from "Time and Absence".

## `Dependent Coverage` (required)

This is the URL of the "View Dependent Coverage" link in Benefit Information.

## `Dependent Information` (optional)

This is an old HRS URL that was superseded by `Dependent/Beneficiary Info`.

When present, and HRS URL `Dependent/Beneficiary Info` is *not* present,
is the URL of the "View Dependent Details" link in Benefit Information.

When absent, the "View Dependent Details" link in Benefit Information is not
shown.

## `Dependent/Beneficiary Info` (optional)

When present,
is the URL of the "View/Update Dependent Information" link in
Benefit Information.

When absent, the "View/Update Dependent Information" link is not shown, but the
"View Dependent Details" link might be shown instead.

## `Direct Deposit` (optional)

When present, and the viewing employee has `ROLE_VIEW_DIRECT_DEPOSIT`,
is the URL of the "Update your Direct Deposit" link in Payroll Information.

When absent, "Update your Direct Deposit" links the PDF form for this task.

## `Earning Statement` (sic) (optional)

This is the base URL to which `?paycheck_nbr={PAYCHECKNBR}` is appended to
compose a deep link into HRS self service for viewing the earnings statement
with that number. `{PAYCHECKNBR}` is read from the SOAP representation of the
earnings statement as read from HRS.

hrs-portlets will only attempt to read earnings statements from HRS at all if
this URL is present in the HRS URLs service. That is, un-setting this URL is a
way to turn off the MyUW integration with earnings statements vended via HRS
self-service.

## `Employee ePerf` (required)

When present, and the viewing employee has `ROLE_LINK_SELF_PERFORMANCE`,
is the URL of the "Employee" link in the "Performance" list-of-links widget.

When absent, the "Employee" link in the "Performance" list-of-links widgert does
not display.

Absence of this URL when employees have `ROLE_LINK_SELF_PERFORMANCE` logs as an
error.

## `Fluid Garnishments` (optional)

When present, and the viewing employee has `ROLE_VIEW_OWN_GARNISHMENTS`,
is the URL of the "Garnishments/Wage Assignments" link in the
"Payroll Information" portlet.

Available as `HrsUrlDao.SELF_SERVICE_VIEW_GARNISHMENTS_KEY`.

## `Fluid Payroll`

No effect.

## `Fluid Time` (required)

When present, and the viewing employee has `ROLE_REDIRECT_TO_HRS_FLUID_TIME`,
is the URL to which the employee is redirected upon trying to render MyUW
"Time and Absence".

When absent, and the viewing employee has `ROLE_REDIRECT_TO_HRS_FLUID_TIME`,
shows an employee-facing error message.

## `Manager ePerf` (required)

When present, and the viewing employee has `ROLE_LINK_MANAGE_PERFORMANCE`,
is the URL of the "Manager" link in the Performance list-of-links widget.

When absent, and the viewing employee has `ROLE_LINK_MANAGE_PERFORMANCE`,
logs a warning.

Available as `HrsUrlDao.OTHERS_PERFORMANCE_KEY`.

## `Open Enrollment/Hire Event` (required)

When the viewing employee has `ROLE_VIEW_NEW_HIRE_BENEFITS`,
is the URL of the "Enroll now" link in the Benefit Information portlet.

When the viewing employee has `ROLE_VIEW_OPEN_ENROLL_BENEFITS`,
is the URL of the "Enroll now" link in the Benefit Information portlet.

When the viewing employee has either or both of
`ROLE_VIEW_NEW_HIRE_BENEFITS`, `ROLE_VIEW_OPEN_ENROLL_BENEFITS`,
is the URL of the "Enroll now" link in the Benefit Information widget.

## `Payable time detail` (required)

When the viewing employee has `ROLE_VIEW_TIME_ENTRY_HISTORY`,
is the URL of the "View Details" link on the "Time Entry" tab in the
"Time and Absence" portlet.

## `Personal Information` (optional)

The `updateMyPersonalInfoUrl` portlet-preference overrides this URL.

When present, and the `updateMyPersonalInfoUrl` portlet-preference is *not* set,
is the URL of the "Update my personal information" link in Personal Information.

When absent, and the `updateMyPersonalInfoUrl` portlet-preference is *not* set,
shows an employee-facing error message.

## `Request Absence` (required)

When the viewing employee has `ROLE_ENTER_EDIT_CANCEL_OWN_ABSENCES`,
is the URL of the "Enter Absence" link in "Time and Absence".

## `Team Time`

No effect.

## `Time and Absence Edit/Cancel` (optional)

When present and the viewing employee has `ROLE_ENTER_EDIT_CANCEL_OWN_ABSENCES`,
is the URL of the "Edit/Cancel Absence" link in "Time and Absence".

Falls back on `editCancelAbsenceUrl` portlet-preference if this HRS URL is
absent.

When neither this HRS URL nor the portlet-preference is present,
the "Edit/Cancel Absence" link does not appear, regardless of employee role.

This key is available as `HrsUrlDao.ABSENCE_EDIT_CANCEL`.

## `Time Exceptions`

No effect.

## `Time Management`

Drives a link in the old Manager Time and Approval portlet, which is no longer
used.

## `Timesheet` (required)

When the viewing employee has `ROLE_TIMESHEET_BUTTON"`,
is the URL of the "Timesheet" link in "Time and Absence".

## `Update TSA Deductions` (required)

Is the URL of the "Update TSA Deductions" link in "Benefit Information".

## `View 1095-C` (optional)

When present,
is the URL of the "View 2018 1095-C" button in Payroll Information.

When absent,
the "View 2018 1095-C" button does not appear.

## `View W-2` (optional)

When present,
is the URL of the "View 2018 W-2" button in Payroll Information.

When absent,
the "View 2018 W-2" button button does not appear.

## `W-2 Consent` (optional)

When present,
is the URL of the "Consent to receive W-2 electronically" button in
Payroll Information.

When absent,
the "Consent to receive W-2 electronically" button does not appear.

## `Web Clock` (required)

When the viewing employee has `ROLE_VIEW_WEB_CLOCK`,
is the URL of the "Web Clock" link in Time and Absence.

[HRS URLs troubleshooting app]: https://my.wisc.edu/web/exclusive/hrs-portlet-urls
