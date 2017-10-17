package com.brit.parseconfig.auth;

import com.brit.parseconfig.core.User;
import com.brit.parseconfig.db.UserDAO;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class DBAuthenticator implements Authenticator<BasicCredentials, User>
{
    private UserDAO userDAO;

    public DBAuthenticator(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        return userDAO.findByUsernamePassword(
                basicCredentials.getUsername(),
                basicCredentials.getPassword()
        );
    }
}
