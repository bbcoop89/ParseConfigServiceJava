package com.brit.parseconfig.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "config")
@NamedQueries({
        @NamedQuery(
                name = "com.brit.parseconfig.core.Configuration.findByType",
                query = "select c from Configuration c where c.configurationType.id = :id"
        ),
        @NamedQuery(
                name = "com.brit.parseconfig.core.Configuration.remove",
                query = "delete from Configuration c where c.id = :id"
        ),
        @NamedQuery(
                name = "com.brit.parseconfig.core.Configuration.findAll",
                query = "select c from Configuration c"
        )
})
public class Configuration
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "config_id")
    private long id;

    @Column(name = "config_key")
    private String key;

    @Column(name = "config_value")
    private String value;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "config_to_file",
            joinColumns =
                    {
                            @JoinColumn(
                                    name = "config_id"
                            )
                    },
            inverseJoinColumns =
                    {
                            @JoinColumn(
                                    name = "config_file_id"
                            )
                    }
    )
    @JsonIgnore
    private Set<ConfigurationFile> configurationFiles = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "config_type_id")
    @JsonIgnore
    private ConfigurationType configurationType;

    public Configuration() {}

    public Configuration(String key, String value, Set<ConfigurationFile> configurationFiles, ConfigurationType configurationType) {
        this.key = key;
        this.value = value;
        this.configurationFiles = configurationFiles;
        this.configurationType = configurationType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<ConfigurationFile> getConfigurationFiles() {
        return configurationFiles;
    }

    public void setConfigurationFiles(Set<ConfigurationFile> configurationFiles) {
        this.configurationFiles = configurationFiles;
    }

    public ConfigurationType getConfigurationType() {
        return configurationType;
    }

    public void setConfigurationType(ConfigurationType configurationType) {
        this.configurationType = configurationType;
    }
}
