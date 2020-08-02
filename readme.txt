-- Download and run application
1. Clone code base from GitHub : https://github.com/niallo58/document-analyzer

2. Install Maven if necessary

3. Prepare you jar. To do this go to directory where the source code has been downloaded
   		e.g. ~/Projects/document-analyzer
   Once here, you can run the following command
   		mvn clean install		
   		
4. Start up the application. To do this go to directory where the source code has been downloaded
		e.g. ~/Projects/document-analyzer
   Once here, you can run the following command
   		java -jar ./target/document-analyzer-0.0.1-SNAPSHOT.jar
   		

-- H2 database
As the application is using H2 for its database, the database is automatically setup and populated with information. To access the database, you can use the below link
	http://localhost:8085/h2-console/login.jsp
The file which is used to build and populate this database can be found here
	~/document-analyzer/src/main/resources/data.sql
To log into the database, the credentials can be found in the application.properties file which is located here
	~/document-analyzer/src/main/resources/application.properties


-- Swagger
I have setup some automated documentation and endpoints with Swagger for testing. You can test the endpoints from here, or create your own testing scripts if you want
The Swagger page can be found below :
	http://localhost:8085/swagger-ui.html