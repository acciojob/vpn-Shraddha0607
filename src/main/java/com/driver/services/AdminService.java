package com.driver.services;


import com.driver.model.Admin;
import com.driver.model.ServiceProvider;
import com.driver.model.User;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    abstract Admin register(String username, String password) ;
    abstract Admin addServiceProvider(int adminId, String providerName);
    abstract ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception;


}
