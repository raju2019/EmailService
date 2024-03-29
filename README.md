**Problem**
Create a service to Send emails with support of two Email implementations. The application should try with one email implementation and in case of failure should fallback to second implementation.
  
**Implementation**

The code is implemented using Approach 2 as Approach 1  can be deemed to be using a third party library.

Approach1 : Using Circuit Breaker Pattern using Hsyterix, there are two services(MailGun and SendGrid) built to support the API integration of respective providers.
if one fails there is a Hysterix Fallback command to continue the execution

Approach2: Using Completable Future to asynchronously execute the first service (MailGun). In case there is an Exception then the second email implementation (SendGrid) is invoked
If both the implementation fails an Exception is thrown 


**REST HTTPS Codes**
Success - 200
Failure - 500
  
**TODO**
1.) Dockerize the Service
2.) Perfomance Testing
3.) Add more tests
4.) Dependency of lambok could have been avoided by using manually adding getters and setters , but needed slf4j for logging so have kept.
5.) Swagger Contracts could have been implemented


**Setup**
1. git clone <the repository>
2. mvn clean install
3. mvn package && java -jar target/EmailChallenge-1.0-SNAPSHOT.jar
4. Update your API keys and domain needed for the SendGrid and MailGun in  mailgun.properties and sendgrid.properties

**Sample Request**

curl -d '{"to":"rajnsydney@gmail.com","body":"Hello Test Email!","subject":"Hi"}' -H 'Content-Type:application/json' -v -X POST localhost:8080/sendemail/
