# -*- coding: utf-8 -*-
from flask import Flask, request, g
from flask.ext import restful
import json
from sqlalchemy.exc import IntegrityError
from db import db
from models import User, Password

import settings

# Create flask application
from utils import auth_required

app = Flask(__name__)
app.config.from_object(settings)

# Database
db.init_app(app)

# REST API stuff
api = restful.Api(app)


class UserResource(restful.Resource):
    @auth_required
    def get(self, username):
        if username != g.user.username:
            return restful.abort(404, message='Invalid username')
        return g.user.json()

    @auth_required
    def patch(self, username):
        if username != g.user.username:
            return restful.abort(404, message='Invalid username')
        updated_fields = []
        # Update the password
        if 'password' in request.form:
            # TODO: Validation
            g.user.password = request.form['password']
            updated_fields.append({'field': 'password', 'status': 'success', 'message': 'Updated password'})
        db.session.commit()
        return {'updated_fields': updated_fields, 'user': g.user.json()}


class UserRegistrationResource(restful.Resource):
    def put(self):
        """
         Register a new user on the server
        """
        # TODO: Do not allow everybody to register
        errors = []
        # TODO: Some form validation
        for field in ['username', 'password']:
            if not field in request.form:
                errors.append({'field': field, 'code': 'missing_field'})
        if len(errors) != 0:
            message = {'message': 'Validation failed', 'errors': errors}
            return restful.abort(422, message=message)
        user = User(request.form['username'], request.form['password'])
        # TODO: Handle duplicate users
        try:
            db.session.add(user)
            db.session.commit()
        except IntegrityError:
            message = {'message': 'Registration failed', 'errors': [{'field': 'username', 'code': 'already_exists'}]}
            return restful.abort(422, message=message)
        return user.json()


class UserPasswordsResource(restful.Resource):
    @auth_required
    def get(self, username):
        if username != g.user.username:
            return restful.abort(404, message='Invalid username')
        passwords = g.user.passwords.all()
        return [password.json() for password in passwords]


class PasswordResource(restful.Resource):
    @auth_required
    def get(self, password_id):
        password = g.user.passwords.filter(Password.id == password_id).first()
        if password is None:
            return restful.abort(404, message='Invalid password ID')
        return password.json()

    @auth_required
    def patch(self, password_id):
        password = g.user.passwords.filter(Password.id == password_id).first()
        if password is None:
            return restful.abort(404, message='Invalid password ID')
        updated_fields = []
        # Update the title
        if 'title' in request.form:
            # TODO: Validation
            password.title = request.form['title']
            updated_fields.append({'field': 'title', 'status': 'success', 'message': 'Updated title'})
        # Update the url
        if 'url' in request.form:
            # TODO: Validation
            password.url = request.form['url']
            updated_fields.append({'field': 'url', 'status': 'success', 'message': 'Updated URL'})
        # Update the username
        if 'username' in request.form:
            # TODO: Validation
            password.username = request.form['username']
            updated_fields.append({'field': 'username', 'status': 'success', 'message': 'Updated username'})
        # Update the password
        if 'password' in request.form:
            # TODO: Validation
            password.password = request.form['password']
            updated_fields.append({'field': 'password', 'status': 'success', 'message': 'Updated password'})
        db.session.commit()
        return {'updated_fields': updated_fields, 'password': password.json()}

    @auth_required
    def delete(self, password_id):
        password = g.user.passwords.filter(Password.id == password_id).first()
        if password is None:
            return restful.abort(404, message='Invalid password ID')
        db.session.delete(password)
        db.session.commit()
        return {'status': 'password_deleted'}


class PasswordAdditionResource(restful.Resource):
    @auth_required
    def put(self):
        """
         Add a new password to the users account
        """
        errors = []
        # TODO: Some form validation
        for field in ['title', 'url', 'username', 'password']:
            if not field in request.form:
                errors.append({'field': field, 'code': 'missing_field'})
        if len(errors) != 0:
            message = {'message': 'Validation failed', 'errors': errors}
            return restful.abort(422, message=message)
        password = Password(request.form['title'], request.form['url'], request.form['username'],
                            request.form['password'], g.user.id)
        db.session.add(password)
        db.session.commit()
        return password.json()

# Add resources
api.add_resource(UserRegistrationResource, '/users')
api.add_resource(UserResource, '/users/<username>')
api.add_resource(UserPasswordsResource, '/users/<username>/passwords')
api.add_resource(PasswordAdditionResource, '/passwords')
api.add_resource(PasswordResource, '/passwords/<int:password_id>')

# Start debug server if called directly
if __name__ == '__main__':
    # Debug stuff
    @app.route('/install')
    def install():
        db.drop_all()
        db.create_all()
        return 'INSTALLED'
    app.run(debug=True)