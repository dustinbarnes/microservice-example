microservice-example
====================
2015-01-07: Moved files from DropWizard to Spring Boot, and moved everything to Java 8.

This is just a quick example project for taking 4 micro-services (done in DropWizard), then having an
aggregation service (also in DropWizard) which fetches results from the 4 microservices in parallel,
as an alternative to 4 simultaneous AJAX requests from the frontend. 
