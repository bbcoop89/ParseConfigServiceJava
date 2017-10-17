package com.brit.parseconfig.core;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(
                name = "com.brit.parseconfig.core.User.findAll",
                query = "select u from User u"
        ),
        @NamedQuery(
                name = "com.brit.parseconfig.core.User.findByUsernamePassword",
                query = "select u from User u where u.username = :username and u.password = :password"
        )
})
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public User() {}

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (getId() != user.getId()) return false;
        if (!getUsername().equals(user.getUsername())) return false;
        return getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getUsername().hashCode();
        result = 31 * result + getPassword().hashCode();
        return result;
    }
}
