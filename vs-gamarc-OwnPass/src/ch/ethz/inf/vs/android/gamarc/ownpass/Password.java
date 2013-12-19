package ch.ethz.inf.vs.android.gamarc.ownpass;

import android.util.Base64;

public class Password {
    private long id;
    private Server server;
    private String title;
    private String url;
    private byte[] username;
    private byte[] password;

    public Password(long id, Server server, String title, String url, byte[] username, byte[] password) {
        setId(id);
        setServer(server);
        setTitle(title);
        setUrl(url);
        setUsername(username);
        setPassword(password);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getUsername() {
        return username;
    }

    public String getUsernameBase64() {
        return Base64.encodeToString(username, Base64.DEFAULT);
    }

    public void setUsername(byte[] username) {
        this.username = username;
    }

    public byte[] getPassword() {
        return password;
    }

    public String getPasswordBase64() {
        return Base64.encodeToString(getPassword(), Base64.DEFAULT);
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }
}
