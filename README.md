# Sose_project_working

## Questions

        - Adding the final database in /database (project serviceprev)
        - JONObject in response (is it correct??)..difference between SOAP and REST with respect to the WSDL, so which type of                     interaction we need.
        - Multithread..in our case, where??
        - For Autili and Di Salle: Note that does not exists a webservice that is able to retrieve all the conferences (and all the               related information) around the world.





## Application and services 

- Google Maps
        To integrate this service in our project, it's enough to get the API_KEY from Google Website to enable the retrieve of maps and         in particular of POIs given certain latitude and longitude. This API return a JSON object that is used in the mobile application        (the client) to display the map on the mobile phone and the related POIs. To do this, a Google account is required, and you have         to request for key in free mode.
-Google Calendar
        For this service, we need the authorization to access at the personal calendar of the user through initializing the calendar in         the prosumer. At the first time, the application opens a new tab in which the user must link with his google account and                authorize the application to access his personal data. Then, the application download the client_secret.json file that allow the        complete access to the calendar.

- Project structure and setup
        The entire project is based on Maven and Apache CXF structure that automatically handle dependencies using the pom file in which         we specify all dependencies needed for the project. Apache CXF divides the structure in two main part: the auto-generated               resources and the implementation part, in which we write the business logic of our application.  Going in deep on the latter, we         have the src folder that contain three subfolders:  
                -	Java, that contain the really implementation with the all webservices involved;
                -	Resources, that contain principally the WSDL files and all the resources for the application (such as                                   configuration files)
                -	Webapp, that contain the configuration files for Spring and servlet mapping
        Depending on the service, we change only the business logic in the business subfolder and in particular in the impl-ws path we           can find the implementation of the service. 
        To run the project, it’s enough to build all Maven projects and deploy them on Tomcat. With the update operation, Maven resolves         the dependencies of the pom and build the structure (methods, interfaces) following the WSDL files that perform the mapping             between the abstract and concrete resources. Once we launch the prosumer and our provider from scratch (Preview), our client can         perform Restful request in order to display all necessary contents. To do this, we have set the ClientInterface servlet that             allow communication with the prosumer or directly with the other providers. 
