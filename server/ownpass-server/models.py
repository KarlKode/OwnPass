from db import db


class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(100), unique=True)
    password = db.Column(db.String(100))

    def __init__(self, username, password):
        self.username = username
        self.password = password

    def __repr__(self):
        return '<User %r>' % self.username

    def json(self):
        return {'id': self.id,
                'username': self.username,
                'password': self.password
        }


class Password(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(100))
    url = db.Column(db.String(200))
    username = db.Column(db.LargeBinary())
    password = db.Column(db.LargeBinary())
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'))
    user = db.relationship('User', backref=db.backref('passwords', lazy='dynamic'))

    def __init__(self, title, url, username, password, user_id):
        self.title = title
        self.url = url
        self.username = username
        self.password = password
        self.user_id = user_id

    def __repr__(self):
        return '<Password %r %r %r>' % (self.id, self.title, self.user_id)

    def json(self):
        return {
            'id': self.id,
            'title': self.title,
            'url': self.url,
            'user_id': self.user_id,
            'username': self.username,
            'password': self.password
        }
