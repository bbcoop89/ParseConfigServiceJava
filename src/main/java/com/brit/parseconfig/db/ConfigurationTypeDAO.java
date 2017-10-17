package com.brit.parseconfig.db;

import com.brit.parseconfig.core.ConfigurationType;
import com.brit.parseconfig.core.User;
import com.google.common.base.Optional;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class ConfigurationTypeDAO extends AbstractDAO<ConfigurationType>
{
    public ConfigurationTypeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<ConfigurationType> findAll()
    {
        return list(
                namedQuery(
                        "com.brit.parseconfig.core.ConfigurationType.findAll"
                )
        );
    }

    public Optional<ConfigurationType> findById(long id)
    {
        return Optional.fromNullable(get(id));
    }

    public ConfigurationType save(ConfigurationType configurationType)
    {
        return persist(configurationType);
    }

    public void delete(ConfigurationType configurationType)
    {
        namedQuery(
                "com.brit.parseconfig.core.ConfigurationType.remove"
        )
                .setParameter("id", configurationType.getId())
                .executeUpdate();

//        Response.noContent();
    }

    public void update(ConfigurationType configurationType)
    {
        currentSession().saveOrUpdate(configurationType);
    }
}
