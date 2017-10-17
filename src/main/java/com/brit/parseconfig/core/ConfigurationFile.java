package com.brit.parseconfig.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "config_file")
@NamedQueries({
        @NamedQuery(
                name = "com.brit.parseconfig.core.ConfigurationFile.findAll",
                query = "select c from ConfigurationFile c"
        ),
        @NamedQuery(
                name = "com.brit.parseconfig.core.ConfigurationFile.remove",
                query = "delete from ConfigurationFile c where c.id = :id"
        )
})
public class ConfigurationFile
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "config_file_id")
    private long id;

    @Column(name = "config_file_path")
    private String path;

    @ManyToMany(mappedBy = "configurationFiles")
    @JsonIgnore
    private Set<Configuration> configurations = new HashSet<>();

    public ConfigurationFile() {}

    public ConfigurationFile(String path) {
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(Set<Configuration> configurations) {
        this.configurations = configurations;
    }
}
