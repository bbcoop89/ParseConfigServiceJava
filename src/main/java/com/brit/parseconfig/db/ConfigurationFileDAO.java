package com.brit.parseconfig.db;

import com.brit.parseconfig.core.ConfigurationFile;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class ConfigurationFileDAO extends AbstractDAO<ConfigurationFile>
{
    public ConfigurationFileDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<ConfigurationFile> findAll()
    {
        return list(
                namedQuery(
                        "com.brit.parseconfig.core.ConfigurationFile.findAll"
                )
        );
    }

    public Optional<ConfigurationFile> findById(long id)
    {
        return Optional.fromNullable(get(id));
    }

    public ConfigurationFile save(ConfigurationFile configurationFile)
    {
        return persist(configurationFile);
    }

    public void delete(ConfigurationFile configurationFile)
    {
        namedQuery(
                "com.brit.parseconfig.core.ConfigurationFile.remove"
        )
                .setParameter("id", configurationFile.getId())
                .executeUpdate();
    }
}
