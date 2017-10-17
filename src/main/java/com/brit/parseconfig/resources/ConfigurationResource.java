package com.brit.parseconfig.resources;

import com.brit.parseconfig.core.Configuration;
import com.brit.parseconfig.core.ConfigurationFile;
import com.brit.parseconfig.core.ConfigurationType;
import com.brit.parseconfig.core.User;
import com.brit.parseconfig.db.ConfigurationDAO;
import com.brit.parseconfig.db.ConfigurationTypeDAO;
import com.google.common.base.Optional;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

@Path("/configurations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConfigurationResource
{
    private ConfigurationDAO configurationDAO;

    private ConfigurationTypeDAO configurationTypeDAO;

    public ConfigurationResource(ConfigurationDAO configurationDAO, ConfigurationTypeDAO configurationTypeDAO) {
        this.configurationDAO = configurationDAO;
        this.configurationTypeDAO = configurationTypeDAO;
    }

    public ConfigurationDAO getConfigurationDAO() {
        return configurationDAO;
    }

    public void setConfigurationDAO(ConfigurationDAO configurationDAO) {
        this.configurationDAO = configurationDAO;
    }

    @GET
    @UnitOfWork
    public List<Configuration> getConfigurations(@Auth User user)
    {
        return this.configurationDAO.findAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Configuration> getConfiguration(@PathParam("id") LongParam id, @Auth User user)
    {
        return this.configurationDAO.findById(id.get());
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Optional<Configuration> delete(@PathParam("id") LongParam id, @Auth User user)
    {
        Optional<Configuration> configuration = this.configurationDAO.findById(id.get());

        if(configuration.isPresent()) {
            this.configurationDAO.delete(configuration.get());
        }

        return configuration;
    }

    @POST
    @UnitOfWork
    public Configuration saveConfiguration(Configuration configuration, @Auth User user)
    {
        Optional<ConfigurationType> configurationType = this.configurationTypeDAO.findById(configuration.getConfigurationType().getId());

        if(configurationType.isPresent()) {
            configuration.setConfigurationType(configurationType.get());
        }

        return this.configurationDAO.save(configuration);
    }

//    @PUT
//    @Path("/{id}")
//    @UnitOfWork
//    public Configuration update(
//            @PathParam("id") LongParam id,
//            String key,
//            String value,
//            Set<ConfigurationFile> configurationFiles,
//            ConfigurationType configurationType
//    ) {
//        Configuration c = null;
//
//        Optional<Configuration> configuration = this.configurationDAO.findById(id.get());
//
//        if(configuration.isPresent()) {
//            c = configuration.get();
//            c.setKey(key);
//            c.setValue(value);
//            c.setConfigurationType(configurationType);
//            c.setConfigurationFiles(configurationFiles);
//        } else {
//            c = new Configuration(key, value, configurationFiles, configurationType);
//        }
//
//        this.configurationDAO.save(c);
//
//        return c;
//    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Configuration update(Configuration configuration, @Auth User user)
    {
        configuration.setKey(configuration.getKey());
        configuration.setValue(configuration.getValue());
        configuration.setConfigurationType(configuration.getConfigurationType());
        configuration.setConfigurationFiles(configuration.getConfigurationFiles());

        this.configurationDAO.save(configuration);

        return configuration;
    }
}
