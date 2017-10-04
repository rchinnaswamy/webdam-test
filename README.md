# WebDAM API Test Tool
This tool tests the following APIs:
* GET /api/v1/asset
* GET /api/v1/asset/{id}
* GET /api/v1/search

The automated tests are implemented using Java and RestAssured. The test framework and test runner are built using TestNG. Dependencies are managed using Maven. You will need JDK 1.8, Maven 3.x to execute the tests.

## Instructions to Run the tests
* Go to the project folder: cd webdam-assignment
* To run GetAsset tests: mvn clean install -DincludedGroups=getassettests
* To run Search Asset tests: mvn clean install -DincludedGroups=searchtests
* To run Negative tests: mvn clean install -DincludedGroups=negativetests

## Test Results
The tests are executed via Maven Surefure plugin which generates the test result reports. The test reports are found under: /target/surefire-reports folder. Here are the few reports to look at:
* emailable-report.html
* "WebDAM Interview Test Suite"/"Assets - Get and Search Tests.html" 
