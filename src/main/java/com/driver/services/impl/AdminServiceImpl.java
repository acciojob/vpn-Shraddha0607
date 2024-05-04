package com.driver.services.impl;

import com.driver.model.Admin;
import com.driver.model.Country;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    ServiceProviderRepository serviceProviderRepository1;

    @Autowired
    CountryRepository countryRepository1;

    @Override
    public Admin register(String username, String password) {
        Admin admin = new Admin();
        admin.setPassword(password);
        admin.setUsername(username);
//        admin.setServiceProviders(new ArrayList<>());

       adminRepository1.save(admin);
        return admin;
    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName) {
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setName(providerName);
//        serviceProvider.setConnectionList(new ArrayList<>());
//        serviceProvider.setUsers(new ArrayList<>());
//        serviceProvider.setCountryList(new ArrayList<>());
        ServiceProvider serviceProvider1 =serviceProviderRepository1.save(serviceProvider);

        // now do connectivity
        Optional<Admin> adminOptional = adminRepository1.findById(adminId);
        Admin admin = adminOptional.get();
        admin.getServiceProviders().add(serviceProvider1);
        serviceProvider.setAdmin(admin);
        adminRepository1.save(admin);
        serviceProviderRepository1.save(serviceProvider1);

        return admin;

    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception{
        Optional<ServiceProvider> optionalServiceProvider = serviceProviderRepository1.findById(serviceProviderId);
        if(optionalServiceProvider== null) throw new Exception("ServiceProvider ID is invalid!");
        ServiceProvider serviceProvider = optionalServiceProvider.get();

        // now need to get the country
        Country country = countryRepository1.findByCountryName(countryName);
        serviceProvider.getCountryList().add(country);
        serviceProviderRepository1.save(serviceProvider);
        return  serviceProvider;
    }
}
