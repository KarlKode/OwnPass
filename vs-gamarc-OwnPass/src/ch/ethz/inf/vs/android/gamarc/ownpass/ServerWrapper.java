package ch.ethz.inf.vs.android.gamarc.ownpass;

/**
 * Created by Cédric Bürke on 17.12.13.
 */
public class ServerWrapper {



        //TODO encrypte login pwd?
        String url;
        String name;
        String login;
        String password;

        public ServerWrapper(String name, String url, String login, String password){
            name = this.name;
            url = this.url;
            login = this.login;
            password = this.password;
        }

        public String getLogin(){
            return login;
        }

        public String getName(){
            return name;
        }

        public String getUrl(){
            return url;
        }

        public String getPassword(){
            return password;
        }


}
