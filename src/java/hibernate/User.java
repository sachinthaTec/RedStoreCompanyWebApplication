package hibernate;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author USER
 */

@Entity
@Table(name = "user")
public class User implements Serializable{
    
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "first_name",length = 45,nullable = false)
    private String first_name;
    
    @Column(name = "last_name",length = 45,nullable = false)
    private String last_name;
    
    @Column(name = "email",length = 45,nullable = false)
    private String email;
    
    @Column(name = "mobile",length = 45,nullable = false)
    private String mobile;
    
    @Column(name = "password",length = 20,nullable = false)
    private String password;
    
    @Column(name = "varification",length = 20,nullable = false)
    private String varification;
    
    @Column(name = "created_at",nullable = false)
    private Date created_at;
    
    
    public User(){}

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the first_name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * @param first_name the first_name to set
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * @return the last_name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * @param last_name the last_name to set
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the verification
     */
    public String getVerification() {
        return varification;
    }

    /**
     * @param verification the verification to set
     */
    public void setVerification(String verification) {
        this.varification = verification;
    }

    /**
     * @return the created_at
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * @param created_at the created_at to set
     */
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
