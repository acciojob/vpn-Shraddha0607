package com.driver.services.impl;

import com.driver.model.Connection;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.model.User;
import com.driver.repository.ConnectionRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;

    @Autowired
    ConnectionRepository connectionRepository3;

    @Override
    public User register(String username, String password, String countryName) throws Exception{
        User user = new User();

        // get the country code, if countryName is wrong then throw exception
        CountryName countryName1=null;
        String countryCode=null;
        if(countryName.equalsIgnoreCase("ind")){
            countryName1=CountryName.IND;
            countryCode=CountryName.IND.toCode();
        }else if(countryName.equalsIgnoreCase("aus")){
            countryName1=CountryName.AUS;
            countryCode=CountryName.AUS.toCode();
        }else if(countryName.equalsIgnoreCase("usa")) {
            countryName1 = CountryName.USA;
            countryCode = CountryName.USA.toCode();
        }else if(countryName.equalsIgnoreCase("chi")){
            countryName1 = CountryName.CHI;
            countryCode = CountryName.CHI.toCode();
        }else if(countryName.equalsIgnoreCase("jpn")){
            countryName1 = CountryName.JPN;
            countryCode = CountryName.JPN.toCode();
        }else{
            throw new Exception("Country not found");
        }

        // now we need to give the details to the user
        user.setUsername(username);
        user.setPassword(password);
        user.setConnectionList(new ArrayList<>());
        user.setServiceProviderList(new ArrayList<>());
        User user1 = userRepository3.save(user);
        // now make originalIp = countryCode+"."+userId
        String originalIp = countryCode + "." + String.valueOf(user1.getId());
        user1.setOriginalIp(originalIp);
        user1.setConnected(false);
//        user1.getMaskedIp(null);
        userRepository3.save(user1);
        return  user;
    }

    @Override
    public User subscribe(Integer userId, Integer serviceProviderId) {
        User user = userRepository3.findById(userId).get();
        ServiceProvider serviceProvider = serviceProviderRepository3.findById(serviceProviderId).get();
        // create new connection
        Connection connection = new Connection();
        connection.setUser(user);
        connection.setServiceProvider(serviceProvider);
        connectionRepository3.save(connection);

        // make connection
        user.getServiceProviderList().add(serviceProvider);
        serviceProvider.getUsers().add(user);
        userRepository3.save(user);
        serviceProviderRepository3.save(serviceProvider);
        return  user;
    }
}
