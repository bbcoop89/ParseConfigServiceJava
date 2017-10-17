package com.brit.parseconfig.db;

import com.brit.parseconfig.core.User;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;


public class UserDAO extends AbstractDAO<User>
{

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<User> findAll()
    {
        return list(
                namedQuery(
                        "com.brit.parseconfig.core.User.findAll"
                )
        );
    }

    public Optional<User> findByUsernamePassword(String username, String password)
    {
        return Optional.fromNullable(
                uniqueResult(
                        namedQuery(
                                "com.brit.parseconfig.core.User.findByUsernamePassword"
                        )
                        .setParameter("username", username)
                        .setParameter("password", password)
                )
        );
    }
}
