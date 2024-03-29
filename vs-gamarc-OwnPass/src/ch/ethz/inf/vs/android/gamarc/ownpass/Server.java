package ch.ethz.inf.vs.android.gamarc.ownpass;

public class Server {
    private long id;
    private String name;
    private String url;
    private String username;
    private String password;

    public Server(String name, String url, String username, String password) {
        setName(name);
        setUrl(url);
        setUsername(username);
        setPassword(password);
    }

    public Server(long id, String name, String url, String username, String password) {
        this(name, url, username, password);
        setId(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){ return this.name;}
}
