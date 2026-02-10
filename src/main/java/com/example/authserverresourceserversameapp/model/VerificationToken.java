package com.example.authserverresourceserversameapp.model;

import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Date;

@Entity(name = "tokens")
public class VerificationToken {

    private static final int EXPIRATION = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne
    private User user;

    private Date expiryDate;

    public VerificationToken() {
        super();
    }

    public VerificationToken(String token) {
        super();

        this.token = token;
        this.expiryDate = calculateExpiryDate();
    }

    public VerificationToken(String token, User user) {
        super();

        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate();
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.SECOND, VerificationToken.EXPIRATION);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate();
    }
}
