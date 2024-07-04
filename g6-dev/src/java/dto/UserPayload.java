package dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserPayload implements Serializable {

    private String password;
    private String username;
    private List<SimpleGrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private boolean rememberMe;
    private String sessionId = null;
    private Long timestamp = null;

    public UserPayload(String password, String username, List<SimpleGrantedAuthority> authorities, boolean rememberMe) {
        this.password = password;
        this.username = username;
        this.authorities = authorities;
        this.rememberMe = rememberMe;
        this.timestamp = System.currentTimeMillis();
        this.sessionId = "L." + UUID.randomUUID().toString().replaceAll("-", "");
    }

    public UserPayload() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPayload)) return false;

        UserPayload that = (UserPayload) o;
        return isAccountNonExpired() == that.isAccountNonExpired() && isAccountNonLocked() == that.isAccountNonLocked() && isCredentialsNonExpired() == that.isCredentialsNonExpired() && isEnabled() == that.isEnabled() && isRememberMe() == that.isRememberMe() && getPassword().equals(that.getPassword()) && getUsername().equals(that.getUsername()) && getAuthorities().equals(that.getAuthorities()) && Objects.equals(getSessionId(), that.getSessionId()) && Objects.equals(getTimestamp(), that.getTimestamp());
    }

    @Override
    public int hashCode() {
        int result = getPassword().hashCode();
        result = 31 * result + getUsername().hashCode();
        result = 31 * result + getAuthorities().hashCode();
        result = 31 * result + Boolean.hashCode(isAccountNonExpired());
        result = 31 * result + Boolean.hashCode(isAccountNonLocked());
        result = 31 * result + Boolean.hashCode(isCredentialsNonExpired());
        result = 31 * result + Boolean.hashCode(isEnabled());
        result = 31 * result + Boolean.hashCode(isRememberMe());
        result = 31 * result + Objects.hashCode(getSessionId());
        result = 31 * result + Objects.hashCode(getTimestamp());
        return result;
    }

    public static class SimpleGrantedAuthority {

        private String authority;

        public SimpleGrantedAuthority() {

        }

        public SimpleGrantedAuthority(String authority) {
            this.authority = authority;
        }

        public String getAuthority() {
            return authority;
        }
    }
}
