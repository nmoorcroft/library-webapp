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

The demo webapp should be built using maven 

```
mvn clean package
```

Once built, the webapp can be deployed to a default Apache Tomcat 7 install

### Database config

The application requires MySQL5 to be installed locally with a 'root' user with the password 'root'.

To create the database schema, first create a new database called 'library'

```
create database library;
```

Once the database has been created, create the schema using the liquibase maven plugin

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




