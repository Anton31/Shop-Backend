package com.example.authserverresourceserversameapp.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity(name = "tokens")
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;
    @Id
    @SequenceGenerator(name = "tokenGen", sequenceName = "tokenSeq", initialValue = 10)
    @GeneratedValue(generator = "tokenGen")
    private Long id;

    private String token;
    @Column(name = "expiry_date")
    private Date expiryDate;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    private User user;

    public VerificationToken() {
    }

    public VerificationToken(final String token) {

        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate  = calculateExpiryDate(EXPIRATION);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

}