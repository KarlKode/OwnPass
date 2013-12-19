package ch.ethz.inf.vs.android.gamarc.ownpass;

import android.util.Base64;

public class Password {
    private long id;
    private Server server;
    private long serverId;
    private String title;
    private String url;
    private byte[] username;
    private byte[] password;

    public Password(Server server, long serverId, String title, String url, byte[] username, byte[] password) {
        setId(id);
        setServer(server);
        setServerId(serverId);
        setTitle(title);
        setUrl(url);
        setUsername(username);
        setPassword(password);
    }

    public Password(Server server, long serverId, String title, String url, String username, String password) {
        this(server, serverId, title, url, Base64.decode(username, Base64.DEFAULT), Base64.decode(password, Base64.DEFAULT));
    }

    public Password(long id, Server server, long serverId, String title, String url, byte[] username, byte[] password) {
        this(server, serverId, title, url, username, password);
        setId(id);
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

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
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

    public String toString(){return title;}
}
