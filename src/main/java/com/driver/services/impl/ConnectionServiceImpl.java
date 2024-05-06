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


        user.setConnected(true);
        Country country = null;
        if(user.getServiceProviderList() == null) throw new Exception("Unable to connect");
        ServiceProvider serviceProviderToConnect = null;
        for(ServiceProvider serviceProvider : user.getServiceProviderList()){
            boolean flagGotServiceProvider = false;
            for(Country country1 : serviceProvider.getCountryList()){
                if(country1.getCountryName().toString() == countryName){
                    country = country1;
                    serviceProviderToConnect = serviceProvider;
                    flagGotServiceProvider = true;
                    break;
                }
            }
            if(flagGotServiceProvider) break;
        }
        if(country == null)  throw new Exception("Unable to connect");

        String maskedIp = country.getCode() + "." + serviceProviderToConnect.getId() + "." + user.getId();
        user.setMaskedIp(maskedIp);
        userRepository2.save(user);

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
        userRepository2.save(user);
        return user;
    }
    @Override
    public User communicate(int senderId, int receiverId) throws Exception {
        User user = new User();
        //Establish a connection between sender and receiver users
        //To communicate to the receiver, sender should be in the current country of the receiver.
        //If the receiver is connected to a vpn, his current country is the one he is connected to.
        //If the receiver is not connected to vpn, his current country is his original country.
        //The sender is initially not connected to any vpn. If the sender's original country does not match receiver's current country, we need to connect the sender to a suitable vpn. If there are multiple options, connect using the service provider having smallest id
        //If the sender's original country matches receiver's current country, we do not need to do anything as they can communicate. Return the sender as it is.
        //If communication can not be established due to any reason, throw "Cannot establish communication" exception

        return user;
            }
}
