package com.brit.parseconfig.auth;

import com.brit.parseconfig.core.User;
import com.brit.parseconfig.db.UserDAO;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;


public class ParseConfigAuthenticator implements Authenticator<BasicCredentials, User>
{

    private String password;

    public ParseConfigAuthenticator(String password)
    {
        this.password = password;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException
    {
        if(password.equals(basicCredentials.getPassword())) {
            return Optional.of(new User());
        } else {
            return Optional.absent();
        }
    }
}
