package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ConnectionRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    UserRepository userRepository2;
    @Autowired
    ServiceProviderRepository serviceProviderRepository2;
    @Autowired
    ConnectionRepository connectionRepository2;

    @Override
    public User connect(int userId, String countryName) throws Exception{
        User user = userRepository2.findById(userId).get();
        // first if connected already then exception
        if(user.getConnected() == true) throw new Exception("Already connected");

        // next check country name is same as original country of user
        else if(user.getOriginalCountry().getCountryName().toString() == countryName) return user;     // no need for connection

        // else part if then do calculation
//        3. Else, the user should be subscribed under a serviceProvider having option to connect to the given country.
        //If the connection can not be made (As user does not have a serviceProvider or serviceProvider does not have given country, throw "Unable to connect" exception.
        //Else, establish the connection where the maskedIp is "updatedCountryCode.serviceProviderId.userId" and return the updated user. If multiple service providers allow you to connect to the country, use the service provider having smallest id.


        Country country = user.getOriginalCountry();
//        if(user.getServiceProviderList()){
//
//        }
//        else{
//            String maskedIp = country.getCode() + "." + serviceProvider.getId() + "." + user.getId();
//            user.setMaskedIp(maskedIp);
//            userRepository2.save(user);
//        }

        return  user;
    }
    @Override
    public User disconnect(int userId) throws Exception {
        User user = userRepository2.findById(userId).get();

        //If the given user was not connected to a vpn, throw "Already disconnected" exception.
        if(user.getConnected() ==false) throw new Exception("Already disconnected");
        //Else, disconnect from vpn, make masked Ip as null, update relevant attributes and return updated user.
        user.setMaskedIp(null);
        user.setConnected(false);     // disconnect the vpn
        return user;
    }
    @Override
    public User communicate(int senderId, int receiverId) throws Exception {
        User user = new User();
        return user;
            }
}
