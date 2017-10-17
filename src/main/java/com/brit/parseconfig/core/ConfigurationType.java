package com.brit.parseconfig.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "config_type")
@NamedQueries({
        @NamedQuery(
                name = "com.brit.parseconfig.core.ConfigurationType.findAll",
                query = "select c from ConfigurationType c"
        ),
        @NamedQuery(
                name = "com.brit.parseconfig.core.ConfigurationType.remove",
                query = "delete from ConfigurationType c where c.id = :id"
        )
})
public class ConfigurationType
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "config_type_id")
    private long id;

    @Column(name = "config_type_name")
    private String type;

    @OneToMany(mappedBy = "configurationType")
    @JsonIgnore
    private List<Configuration> configurations = new ArrayList<>();

    public ConfigurationType() {}

    public ConfigurationType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
    }
}
