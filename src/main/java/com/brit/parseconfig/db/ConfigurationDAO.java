package com.brit.parseconfig.db;

import com.brit.parseconfig.core.Configuration;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class ConfigurationDAO extends AbstractDAO<Configuration>
{
    public ConfigurationDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Configuration> findAll()
    {
        return list(
                namedQuery(
                        "com.brit.parseconfig.core.Configuration.findAll"
                )
        );
    }

    public List<Configuration> findByType(long id)
    {
        return list(
                namedQuery(
                        "com.brit.parseconfig.core.Configuration.findByType"
                )
                .setParameter("id", id)
        );
    }

    public Optional<Configuration> findById(long id)
    {
        return Optional.fromNullable(get(id));
    }

    public Configuration save(Configuration configuration)
    {
        return persist(configuration);
    }

    public void delete(Configuration configuration)
    {
        namedQuery(
                "com.brit.parseconfig.core.Configuration.remove"
        )
                .setParameter("id", configuration.getId())
                .executeUpdate();
    }
}
