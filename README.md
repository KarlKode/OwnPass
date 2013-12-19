# OwnPass #

## Debug stuff ##
http://marcg.ch:5000/

## Encryption ##
User passwords are hashed with SHA256
Password entrie fields are encrypted with AES 256 in CBC mode with PKCS5 padding. They should be encoded with Base64. http://stackoverflow.com/questions/10198462/android-aes-256-bit-encrypt-data

## API ##
### General ###
All successful requests result in a response with the response code 200.
All requests (except the user registration) have to carry an HTTP authentication header with valid credentials.
If the information is not correct, a response with the response code 403 is sent.


#### Examples ####
curl -X GET http://127.0.0.1:5000/users/marc
{
    "message": "Forbidden",
    "status": 403
}

### PUT /users ###
Register a new user.

#### Parameters ####
* "username": Username
* "password": SHA256 hash of the master password of the user (TODO: Use bcrypt or scrypt)

#### Response ####
* Success: The created user
* Error: 422 An object with the fields "message" and "errors" with a list of all errors that occured

#### Examples ####
curl -X PUT http://127.0.0.1:5000/users -d "username=marc&password=marcspassword"
{
    "id": 4,
    "password": "marcspassword",
    "username": "marc"
}

curl -X PUT http://127.0.0.1:5000/users -d "username=marc&password=marcspassword"
{
    "message": {
        "errors": [
            {
                "code": "already_exists",
                "field": "username"
            }
        ],
        "message": "Registration failed"
    }
}


### GET /users/<username> ###
Get the user's information.

#### Response ####
* Success: The user's information
* Error: 404 If the user is not the same as the one from the authentication credentials

#### Example ####
curl -X GET -u marc:marcspassword http://127.0.0.1:5000/users/marc
{
    "id": 4,
    "password": "marcspassword",
    "username": "marc"
}

curl -X GET -u marc:marcspassword http://127.0.0.1:5000/users/marc2
{
    "message": "Invalid username"
}


### PATCH /users/<username>
Update/change a user.

#### Parameters ####
Optional:
* "password": SHA256 hash of the new master password of the user (TODO: Use bcrypt or scrypt)

#### Response ####
* Success: An object with the list of updated fields as "updated_fields" and the updated user as "user"
* Error: 404 If the user is not the same as the one from the authentication credentials

#### Example ####
curl -X PATCH -u marc:test -d "" http://127.0.0.1:5000/users/marc
{
    "updated_fields": [],
    "user": {
        "id": 4,
        "password": "test",
        "username": "marc"
    }
}

curl -X PATCH -u marc:marcspassword -d "password=test" http://127.0.0.1:5000/users/marc
{
    "updated_fields": [
        {
            "field": "password",
            "message": "Updated password",
            "status": "success"
        }
    ],
    "user": {
        "id": 4,
        "password": "test",
        "username": "marc"
    }
}

curl -X PATCH -u marc:test -d "" http://127.0.0.1:5000/users/mar
{
    "message": "Invalid username"
}


### GET /users/<username>/passwords
Get a list of all the users passwords

#### Response ####
* Success: A list of all the users passwords
* Error: 404 If the user is not the same as the one from the authentication credentials

#### Example ####
curl -X GET -u marc:test http://127.0.0.1:5000/users/marc/passwords
[
    {
        "id": 4,
        "password": "ENCRYPTED",
        "title": "test",
        "url": "http://www.test.com/",
        "user_id": 4,
        "username": "ENCRYPTED"
    }
]


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

#### Example ####
curl -X PUT -u marc:test -d "title=test&url=http://www.test.com/&username=ENCRYPTED&password=ENCRYPTED" http://127.0.0.1:5000/passwords
{
    "id": 4,
    "password": "ENCRYPTED",
    "title": "test",
    "url": "http://www.test.com/",
    "user_id": 4,
    "username": "ENCRYPTED"
}

curl -X PUT -u marc:test -d "title=test" http://127.0.0.1:5000/passwords
{
    "message": {
        "errors": [
            {
                "code": "missing_field",
                "field": "url"
            },
            {
                "code": "missing_field",
                "field": "username"
            },
            {
                "code": "missing_field",
                "field": "password"
            }
        ],
        "message": "Validation failed"
    }
}



### GET /passwords/<password_id> ###
Get all information of a password entry that belongs to the user.

#### Response ####
* Success: The password
* Error: 404 If the password does not exist or belong to another user

#### Example ####
curl -X GET -u marc:test http://127.0.0.1:5000/passwords/4
{
    "id": 4,
    "password": "ENCRYPTED",
    "title": "test",
    "url": "http://www.test.com/",
    "user_id": 4,
    "username": "ENCRYPTED"
}

curl -X GET -u marc:test http://127.0.0.1:5000/passwords/3
{
    "message": "Invalid password ID"
}


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

#### Example ####
curl -X PATCH -u marc:test -d "title=google&url=https://www.google.com/" http://127.0.0.1:5000/passwords/4
{
    "password": {
        "id": 4,
        "password": "ENCRYPTED",
        "title": "google",
        "url": "https://www.google.com/",
        "user_id": 4,
        "username": "ENCRYPTED"
    },
    "updated_fields": [
        {
            "field": "title",
            "message": "Updated title",
            "status": "success"
        },
        {
            "field": "url",
            "message": "Updated URL",
            "status": "success"
        }
    ]
}


### DELETE /passwords/<password_id> ###
Delete a password.

#### Response ####
* Success: An object with one field "status_deleted" that contains the string "success"
* Error: 404 If the password does not exist or belong to another user

#### Example ####
curl -X DELETE -u marc:test http://127.0.0.1:5000/passwords/4
{
    "status": "password_deleted"
}

curl -X DELETE -u marc:test http://127.0.0.1:5000/passwords/4
{
    "message": "Invalid password ID"
}
