// Note: Do not write @Enumerated annotation above CountryName in this model.

package com.driver.model;

import javax.persistence.*;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private CountryName countryName;
    private String code;

    @OneToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private ServiceProvider serviceProvider;


    public Country() {
    }



    public Country(int id,
                   ServiceProvider serviceProvider,
                   User user,
                   String code,
                   CountryName countryName) {
        this.id = id;
        this.serviceProvider = serviceProvider;
        this.user = user;
        this.code = code;
        this.countryName = countryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CountryName getCountryName() {
        return countryName;
    }

    public void setCountryName(CountryName countryName) {
        this.countryName = countryName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}