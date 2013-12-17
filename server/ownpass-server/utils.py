from functools import wraps
from flask import request, abort, g
from models import User


def auth_required(f):
    @wraps(f)
    def decorated_function(*args, **kwargs):
        if not request.authorization:
            return abort(403)
        auth = request.authorization

        # Check user
        user = User.query.filter(User.username == auth.username, User.password == auth.password).first()
        if user is None:
            return abort(403)
        g.user = user
        return f(*args, **kwargs)
    return decorated_function