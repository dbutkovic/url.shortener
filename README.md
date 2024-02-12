# url.shortener
Assessment project for Infobip

Java 21

Steps how to use(see /help for guided tutorial, only works at port 8080)
1. POST /account
   1. Type your account id and get back password
2. POST /login
   1. Type your account id and password to get jwt token
3. POST /register
   1. Type your jwt token, url that you wish to shorter and redirection type (301 or 302)
   2. Response is shorten url
4. GET /
   1.  Try your short url
5. POST /statistic/{AccountId}
   1. Type your jwt token and account id to get statistic

### Build
mvn package

### Run
 - java -jar url.shortener-0.0.1-SNAPSHOT.war
 - java -jar url.shortener-0.0.1-SNAPSHOT.jar

.war and .jar file are already in executable/ folder