package net.pacificsoft.microservices.loka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    private String username;
    private List<String> roles;
    private String token_type;
    private String access_token;
    private String expires_in;
    private String refresh_token;

    public Account() {
    }

    public Account(String username, List<String> roles, String token_type, String access_token, String expires_in, String refresh_token) {

        this.username = username;
        this.roles = roles;
        this.token_type = token_type;
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }


    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", roles=" + roles +
                ", token_type='" + token_type + '\'' +
                ", access_token='" + access_token + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                '}';
    }
}
