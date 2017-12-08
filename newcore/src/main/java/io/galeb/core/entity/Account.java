package io.galeb.core.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UK_account_name", columnNames = { "name" }) })
public class Account extends AbstractEntity  {

    @JsonProperty(required = true)
    @Column(nullable = false)
    private String email;

    @JsonProperty(required = true)
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "accounts")
    private Set<Team> teams = new HashSet<>();

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        if (teams != null) {
            this.teams.clear();
            this.teams.addAll(teams);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        Assert.hasText(email, "email is not valid");
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Assert.hasText(name, "name is not valid");
        this.name = name;
    }
}