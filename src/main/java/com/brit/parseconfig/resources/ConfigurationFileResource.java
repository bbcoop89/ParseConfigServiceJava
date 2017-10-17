package com.brit.parseconfig.resources;

import com.brit.parseconfig.core.Configuration;
import com.brit.parseconfig.core.ConfigurationFile;
import com.brit.parseconfig.core.User;
import com.brit.parseconfig.db.ConfigurationFileDAO;
import com.google.common.base.Optional;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

@Path("/configurationFiles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConfigurationFileResource
{
    private ConfigurationFileDAO configurationFileDAO;

    public ConfigurationFileResource(ConfigurationFileDAO configurationFileDAO) {
        this.configurationFileDAO = configurationFileDAO;
    }

    public ConfigurationFileDAO getConfigurationFileDAO() {
        return configurationFileDAO;
    }

    public void setConfigurationFileDAO(ConfigurationFileDAO configurationFileDAO) {
        this.configurationFileDAO = configurationFileDAO;
    }

    @GET
    @UnitOfWork
    public List<ConfigurationFile> getConfigurationFiles(@Auth User user)
    {
        return this.configurationFileDAO.findAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<ConfigurationFile> getConfigurationFile(@PathParam("id")LongParam id, @Auth User user)
    {
        return this.configurationFileDAO.findById(id.get());
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Optional<ConfigurationFile> delete(@PathParam("id") LongParam id, @Auth User user)
    {
        Optional<ConfigurationFile> configurationFile = this.configurationFileDAO.findById(id.get());

        if(configurationFile.isPresent()) {
            this.configurationFileDAO.delete(configurationFile.get());
        }

        return configurationFile;
    }

    @POST
    @UnitOfWork
    public ConfigurationFile saveConfigurationFile(ConfigurationFile configurationFile, @Auth User user)
    {
        return this.configurationFileDAO.save(configurationFile);
    }

//    @PUT
//    @Path("/{id}")
//    @UnitOfWork
//    public ConfigurationFile update(
//            @PathParam("id") LongParam id,
//            String path,
//            Set<Configuration> configurations,
//            @Auth User user
//    ) {
//        ConfigurationFile c = null;
//
//        Optional<ConfigurationFile> configurationFile = this.configurationFileDAO.findById(id.get());
//
//        if(configurationFile.isPresent()) {
//            c = configurationFile.get();
//            c.setPath(path);
//            c.setConfigurations(configurations);
//        } else {
//            c = new ConfigurationFile(path);
//            c.setConfigurations(configurations);
//        }
//
//        this.configurationFileDAO.save(c);
//
//        return c;
//    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ConfigurationFile update(ConfigurationFile configurationFile, @Auth User user)
    {
        configurationFile.setPath(configurationFile.getPath());
        configurationFile.setConfigurations(configurationFile.getConfigurations());

        this.configurationFileDAO.save(configurationFile);

        return configurationFile;
    }
}
