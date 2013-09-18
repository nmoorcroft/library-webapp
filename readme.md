# AngularJS Library Demo

### Getting Started

This demo requires the following npm modules to be installed

```
npm install -g karma
npm install -g less
npm install -g phantomjs
npm install -g karma-coverage
npm install -g karma-junit-reporter
npm install -g karma-ng-html2js-preprocessor
```

For Karma to launch PhantomJS set PHANTOMJS_BIN to install location

```
export PHANTOMJS_BIN=/usr/local/bin/phantomjs
```

### Build & Deployment

The demo webapp can should be built using maven 

```
mvn clean package
```

The webapp can be deployed to a default Apache Tomcat 7 install

### Database config

The application requires MySQL5 to be installed locally with a 'root' user with the password 'root'.
To create the database schema, first create a new database called 'library'

```
create database library;
```

The schema can then be created using the liquibase maven pluging

```
mvn liquibase:update
```

### Continuous JavaScript testing

Karma can be executed standalone to continuously execute the JavaScript tests

```
karma start src\test\javascript\karma.conf.js
```

### Environment Settings

Database username, password and jdbc url can be configured in src/main/resources/env.properties
Liquibase database settings are configured in src/main/database/liquibase.properties




