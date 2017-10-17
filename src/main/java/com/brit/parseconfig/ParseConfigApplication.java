package com.brit.parseconfig;

import com.brit.parseconfig.auth.DBAuthenticator;
import com.brit.parseconfig.auth.ParseConfigAuthenticator;
import com.brit.parseconfig.core.Configuration;
import com.brit.parseconfig.core.ConfigurationFile;
import com.brit.parseconfig.core.ConfigurationType;
import com.brit.parseconfig.core.User;
import com.brit.parseconfig.db.ConfigurationDAO;
import com.brit.parseconfig.db.ConfigurationFileDAO;
import com.brit.parseconfig.db.ConfigurationTypeDAO;
import com.brit.parseconfig.db.UserDAO;
import com.brit.parseconfig.resources.ConfigurationFileResource;
import com.brit.parseconfig.resources.ConfigurationResource;
import com.brit.parseconfig.resources.ConfigurationTypeResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ParseConfigApplication extends Application<ParseConfigConfiguration> {

    private final HibernateBundle<ParseConfigConfiguration> hibernateBundle =
            new HibernateBundle<ParseConfigConfiguration>(
                    User.class,
                    ConfigurationType.class,
                    ConfigurationFile.class,
                    Configuration.class
            ) {
                @Override
                public DataSourceFactory getDataSourceFactory(ParseConfigConfiguration parseConfigConfiguration) {
                    return parseConfigConfiguration.getDataSourceFactory();
                }
            };

    public static void main(final String[] args) throws Exception {
        new ParseConfigApplication().run(args);
    }

    @Override
    public String getName() {
        return "ParseConfig";
    }

    @Override
    public void initialize(final Bootstrap<ParseConfigConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<ParseConfigConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(ParseConfigConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });

        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(final ParseConfigConfiguration configuration,
                    final Environment environment) {

        final UserDAO userDAO = new UserDAO(hibernateBundle.getSessionFactory());
        final ConfigurationDAO configurationDAO = new ConfigurationDAO(hibernateBundle.getSessionFactory());
        final ConfigurationTypeDAO configurationTypeDAO = new ConfigurationTypeDAO(hibernateBundle.getSessionFactory());
        final ConfigurationFileDAO configurationFileDAO = new ConfigurationFileDAO(hibernateBundle.getSessionFactory());
//        environment.jersey().register(AuthFactory.binder(
//                new BasicAuthFactory<>(
//                        new ParseConfigAuthenticator(configuration.getPassword()), "SECURITY REALM", User.class
//                )
//        ));

        environment.jersey().register(new ConfigurationResource(configurationDAO, configurationTypeDAO));
        environment.jersey().register(new ConfigurationTypeResource(configurationTypeDAO));
        environment.jersey().register(new ConfigurationFileResource(configurationFileDAO));
        environment.jersey().register(AuthFactory.binder(
                new BasicAuthFactory<>(
                        new DBAuthenticator(userDAO), "SECURITY REALM", User.class
                )
        ));
    }

}
