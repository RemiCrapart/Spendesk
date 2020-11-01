# Spendesk


# How to install

The app is running with openjdk15 and have embedded mongodb so you just need to install openJdk to make it run.

### Install java

Download java following your machine : https://jdk.java.net/15/ and unzip it somewhere.

# How to run

Now you have unzip openjdk15 go directly to the bin folder(jdk-15\bin), it will avoid to configure windows env, bash profile etc...
You are on the bin folder, execute the following command to launch the app :
"java -jar pathOfTheApplication" so for example : "java -jar C:\Spendesk\WalletCards-0.0.1-SNAPSHOT.jar"

The application should run :)

# API Documentation

Documentation of the endpoint is available on openAPI format when the app is running -> http://localhost:8080/v2/api-docs
If you want to see it with a super UI you can go to https://editor.swagger.io/ and copy/paste the openAPI.

# Testing

Postman collection is available on the root repo : TestSpendesk.postman_collection.json
