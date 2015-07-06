package ru.mera.agileboard.rest.info;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import ru.mera.agileboard.model.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by antfom on 16.02.2015.
 */
//@XmlRootElement(name = "user")
//@XmlType(propOrder = {"id", "name", "email", "created", "obsolete"})
@JsonPropertyOrder({"id"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {
    private int id;
    private String name;
    private String email;
    private long created;
    private boolean obsolete = false;

    public UserInfo() {
    }

    public UserInfo(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.created = user.getCreated();
        this.obsolete = user.isObsolete();
    }

    public UserInfo(int id, String name, String email, long created, boolean obsolete) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.created = created;
        this.obsolete = obsolete;
    }

    public static List<UserInfo> fromUsers(Collection<? extends User> users) {
        List<UserInfo> list = users.stream().map(UserInfo::new).collect(Collectors.toList());
        return list;
    }

    public boolean getObsolete() {
        return obsolete;
    }

    public void setObsolete(boolean obsolete) {
        this.obsolete = obsolete;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", created=" + created +
                '}';
    }
}
