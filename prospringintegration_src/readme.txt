This sample code requires the following spring integration sandbox projects.

Checkout from git using:
git clone git://git.springsource.org/spring-integration/sandbox.git

cd into each of the projects listed below and run mvn install

Required Spring Integration Sandbox Projects

1) spring-integration-amqp
2) spring-integration-comet
3) spring-integration-gemfire
4) spring-integration-activiti (This may require building the activiti code at http://svn.codehaus.org/activiti/
   or move to the current release version of activiti in project pom.xml)

You may need to synchronized with current version and the main pom.xml file

The sample code may be built using the command:

mvn install

Software requirements:

JDK version 1.5 or greater
Maven version 2.2.1 or greater

Some of the tool based projects such as OpenESB and Spring Batch Admin are self contained and will need to
be build separately.
