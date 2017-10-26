# Contributing to hrs-portlets

## This is not a community project

This *is* open source software, in that source code is publicly available under 
an OSI recognized genuine open source license. That's what "open source" means.
You are entirely welcome to this source code and free to try to derive value
from it locally, or learn from it, or whatever.

However. **This is *not* a community project, in that the maintainers don't 
expect you to successfully locally implement this software** and are not making 
any effort to enable that. This project was once in Apereo (Jasig?) incubation 
and failed incubation for precisely this reason: this product is not 
architected to be locally realizable anywhere other than the one context where 
it's already locally realized, that is, in MyUW. Sorry.

The world would be a better place with viable community-driven free and open 
source software projects to build services, adaptors, middleware, controllers, 
user experiences enabling better self-service payroll, benefits, and time and 
leave accounting experiences for higher education and beyond. Maybe this code 
could inspire some of that, or even some of it could be harvested and re-worked 
to be more modular and configurable in those ways. That'd be awesome. Please do 
let the maintainers know if this starts coming together. We might well want to 
be part of that and eventually retire this code too. Local ad-hoc one-off 
solutions to generic higher education problems, they've got maintenance 
downsides.

# Maintaining documentation across change

## Roles

If your change 

+ Changes the set of roles, or
+ Changes what the roles let employees do

be sure to update 
`hrs-portlets-ps-impl/src/main/resources/app-context/psAppContext.xml` 
to reflect.

## Portlet preferences

If your change

+ Changes the set of `portlet-preference`s
+ Changes the effect of `portlet-preference`s

be sure to update `README.md` to reflect.
