## Common Module

This module contains shared common classes and libraries used across multiple LMS microservices. It is published to a Nexus repository for easy integration and dependency management.

## nexus information
```
url: https://nexus-lms.azurewebsites.net/
username: admin
password: admin123
```
The username and password are the same for both local and server.

## push common to nexus
- dev(local)
```
mvn -s ./settings-dev.xml clean deploy
```
- server(remote)
```
mvn -s ./settings-prod.xml -f ./pom-prod.xml clean deploy
```