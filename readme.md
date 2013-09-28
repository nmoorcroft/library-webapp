# AngularJS Library Demo

### Getting Started

This demo requires the following npm modules to be installed (npm is available by installing node.js http://nodejs.org/)

```
npm install -g karma
npm install -g less
npm install -g phantomjs
npm install -g karma-coverage
npm install -g karma-junit-reporter
npm install -g karma-ng-html2js-preprocessor
```

For Karma to be able to launch PhantomJS, set PHANTOMJS_BIN to the install location

```
export PHANTOMJS_BIN=/usr/local/bin/phantomjs
```

### Build & Deployment

The demo webapp can be built using maven 

```
mvn clean package
```

Once built, the webapp can be deployed to a default Apache Tomcat 7 install

### Database config

The application is configured to use a local MySQL5 install with a root user with password 'root'.

To create the database schema, first create a new database called 'library'

```
create database library;
```

The library schema can be created using the liquibase maven plugin

```
mvn liquibase:update
```

### Continuous JavaScript testing

Karma can be executed standalone to continuously execute the JavaScript tests

```
karma start src\test\javascript\karma.conf.js
```

### Environment Settings

Database username, password and jdbc url can be configured in `src/main/resources/env.properties`

Liquibase database settings are configured in `src/main/database/liquibase.properties`




