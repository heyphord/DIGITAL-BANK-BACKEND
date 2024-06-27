# DIGITAL-BANK-BACKEND
BACKEND FOR DIGITAL BANK USING JAVA SPRING BOOT

Developed by Hayford Owusu Ansah

Security Considerations

1. Validation :All request needed for the api call is thoroughly checked before processing is done.
2. This application using Bearer Authentication to make sure that an unauthorized personnel does not have access to the Account Details and Fund Transfer APIS
3. Developers must use the username and password supplied in the postman collection to get their token.


Error Handling
1. The API utilizes try catch exception handling. So any exception thrown is gracefully handled without the api breaking.

Documentation

1. This api uses api standards plus the right HTTP Request for each endpoint
2. A postman collection has been added to the email. this will make it easy for anyone to see the api structure
3. Example request and responses was added to each API.