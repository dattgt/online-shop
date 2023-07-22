
package dattgt.registration;

/**
 *
 * @author DATTGT
 */
public class RegistrationDTO {
    private String username;
    private String password;
    private String fullname;
    private boolean role;

    public RegistrationDTO() {
    }

    public RegistrationDTO(String username, String password, 
            String fullname, boolean role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }
}
