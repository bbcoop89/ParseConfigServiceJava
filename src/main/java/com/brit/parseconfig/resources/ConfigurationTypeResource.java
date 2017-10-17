package com.brit.parseconfig.resources;

import com.brit.parseconfig.core.Configuration;
import com.brit.parseconfig.core.ConfigurationType;
import com.brit.parseconfig.core.User;
import com.brit.parseconfig.db.ConfigurationTypeDAO;
import com.google.common.base.Optional;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.PATCH;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import java.util.List;

@Path("/configurationTypes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConfigurationTypeResource
{
    private ConfigurationTypeDAO configurationTypeDAO;

    public ConfigurationTypeResource(ConfigurationTypeDAO configurationTypeDAO) {
        this.configurationTypeDAO = configurationTypeDAO;
    }

    public ConfigurationTypeDAO getConfigurationTypeDAO() {
        return configurationTypeDAO;
    }

    public void setConfigurationTypeDAO(ConfigurationTypeDAO configurationTypeDAO) {
        this.configurationTypeDAO = configurationTypeDAO;
    }

    @GET
    @UnitOfWork
    public List<ConfigurationType> getConfigurationTypes(@Auth User user)
    {
        return this.configurationTypeDAO.findAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<ConfigurationType> getConfigurationType(@PathParam("id") LongParam id, @Auth User user)
    {
        return this.configurationTypeDAO.findById(id.get());
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Optional<ConfigurationType> delete(@PathParam("id") LongParam id, @Auth User user)
    {
        Optional<ConfigurationType> configurationType = this.configurationTypeDAO.findById(id.get());

        if(configurationType.isPresent()) {
            this.configurationTypeDAO.delete(configurationType.get());
        }

        return configurationType;
    }

    @POST
    @UnitOfWork
    public ConfigurationType saveConfigurationType(ConfigurationType configurationType, @Auth User user)
    {
        return this.configurationTypeDAO.save(configurationType);
    }

//    @PUT
//    @Path("/{id}")
//    @UnitOfWork
//    public ConfigurationType update(@PathParam("id") LongParam id, String type, List<Configuration> configurations, @Auth User user)
//    {
//        ConfigurationType c = null;
//
//        Optional<ConfigurationType> configurationType = this.configurationTypeDAO.findById(id.get());
//
//        if(configurationType.isPresent()) {
//            c = configurationType.get();
//            c.setType(type);
//            c.setConfigurations(configurations);
//        } else {
//            c = new ConfigurationType(type);
//            c.setConfigurations(configurations);
//        }
//
//        this.configurationTypeDAO.save(c);
//
//        return c;
//    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ConfigurationType update(ConfigurationType configurationType, @Auth User user)
    {
        configurationType.setType(configurationType.getType());
        configurationType.setConfigurations(configurationType.getConfigurations());

        this.configurationTypeDAO.save(configurationType);

        return configurationType;
    }
}
