# Spendesk


# How to install

The app is running with openjdk15 and have embedded mongodb so you just need to install openJdk to make it run.
Clone the project or take a zip and put it somewhere, on the root folder you have the jar WalletCards-0.0.1-SNAPSHOT.jar to launch

### Install java

Download java following your machine : https://jdk.java.net/15/ and unzip it somewhere.

# How to run

Now you have unzip openjdk15 open a command prompt and go to the bin folder (jdk-15\bin), it will avoid to configure windows env, bash profile etc...

You are on the bin folder, execute the following command to launch the app :
"java -jar pathOfTheApplication" so for example : "java -jar C:\Spendesk\WalletCards-0.0.1-SNAPSHOT.jar"

The application should run :)

# API Documentation

Documentation of the endpoint is available on openAPI format when the app is running -> http://localhost:8080/v2/api-docs
If you want to see it with a super UI you can go to https://editor.swagger.io/ and copy/paste the openAPI.

Card API errors :
- CARDBUS01 : This card id doesn't not exit
- CARDBUS02 : There is not enough money on the card
- CARDBUS03 : The card is not active
- CARDBUS04 : This card doesn't belong to this user

Wallet API errors :
- WALLBUS01 : This wallet id [walletId] doesn't not exit
- WALLBUS02 : This wallet doesn't belong to this company
- WALLBUS03 : There is not enough money on the wallet

Transfer API errors :
- TRANBUS01 : Transfer from [transfer.getOriginEntityType()] is not allowed
- TRANBUS02 : Not possible to transfer if the origin is not in EUR

Tech errors :
- SPENTEC01 : Missing headers or bad input
# Testing

Postman collection is available on the root repo : TestSpendesk.postman_collection.json

To install postman : https://www.postman.com/downloads/ then on the left corner you have a "Import" button to import collection. It's not automatic test that you can launch,
you need to manually take output from one request to put in another one.


# Scope

## Current functional limitation : 
- you can do transfer only if the origin is in EUR because of fixer.io free limitation
- you can do transfer only between wallet

## Added feature
- userIdentifier stored in Transfer model to keep track of the user

## Potential features to do
- Audit table when we load/unload money
- External creation of technical wallet + put fee rate as a property

