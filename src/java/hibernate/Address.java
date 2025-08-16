/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hibernate;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "address")
public class Address implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "line_1",nullable = false)
    private String lineOne;
    
    @Column(name = "line_2",nullable = false)
    private String lineTwo;
    
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    
    @Column(name = "postal_code", length = 45, nullable = false)
    private String postalCode;
    
    @Column(name = "mobile", nullable = false, length = 10)
    private String mobile;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(name = "first_name", length = 45)
    private String firstName;
    
    @Column(name = "last_name", length = 45)
    private String lastName;


    
     public Address(){}
    
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
     * @return the lineOne
     */
    public String getLineOne() {
        return lineOne;
    }

    /**
     * @param lineOne the lineOne to set
     */
    public void setLineOne(String lineOne) {
        this.lineOne = lineOne;
    }

    /**
     * @return the lineTwo
     */
    public String getLineTwo() {
        return lineTwo;
    }

    /**
     * @param lineTwo the lineTwo to set
     */
    public void setLineTwo(String lineTwo) {
        this.lineTwo = lineTwo;
    }

    /**
     * @return the city
     */
    public City getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
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
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    
    
}
