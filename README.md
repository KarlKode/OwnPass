# OwnPass #

## API ##
### General ###
All successful requests result in a response with the response code 200.
All requests (except the user registration) have to carry an HTTP authentication header with valid credentials.
If the information is not correct, a response with the response code 406 is sent.


### PUT /users ###
Register a new user.

#### Parameters ####
* "username": Username
* "password": SHA256 hash of the master password of the user (TODO: Use bcrypt or scrypt)

#### Response ####
* Success: The created user
* Error: 422 An object with the fields "message" and "errors" with a list of all errors that occured


### GET /users/<username> ###
Get the user's information.

#### Response ####
* Success: The user's information
* Error: 404 If the user is not the same as the one from the authentication credentials


### PATCH /users/<username>
Update/change a user.

#### Parameters ####
Optional:
* "password": SHA256 hash of the new master password of the user (TODO: Use bcrypt or scrypt)

#### Response ####
* Success: An object with the list of updated fields as "updated_fields" and the updated user as "user"
* Error: 404 If the user is not the same as the one from the authentication credentials


### GET /users/<username>/passwords
Get a list of all the users passwords

#### Response ####
* Success: A list of all the users passwords
* Error: 404 If the user is not the same as the one from the authentication credentials


### PUT /passwords ###
Add a new password.

#### Parameters ####
* "title": Title of the new password
* "url": URL of the new password
* "username": Username of the new password (already encrypted, The first 16 bytes have to be the used IV)
* "password": Password of the new password (already encrypted, The first 16 bytes have to be the used IV)

#### Response ####
* Success: The created password
* Error: 422 An object with the fields "message" and "errors" with a list of all errors that occured



### GET /passwords/<password_id> ###
Get all information of a password entry that belongs to the user.

#### Response ####
* Success: The password
* Error: 404 If the password does not exist or belong to another user

### PATCH /passwords/<password_id> ###
Update/change a password entry.

#### Parameters ####
Optional:
* "title": Title of the updated password
* "url": URL of the updated password
* "username": Username of the updated password (already encrypted, The first 16 bytes have to be the used IV)
* "password": Password of the updated password (already encrypted, The first 16 bytes have to be the used IV)

#### Response ####
* Success: An object with the list of updated fields as "updated_fields" and the updated password as "password"
* Error: 404 If the password does not exist or belong to another user
* Error: 422 An object with the fields "message" and "errors" with a list of all errors that occured


### DELETE /passwords/<password_id> ###
Delete a password.

#### Response ####
* Success: An object with one field "status_deleted" that contains the string "success"
* Error: 404 If the password does not exist or belong to another user
