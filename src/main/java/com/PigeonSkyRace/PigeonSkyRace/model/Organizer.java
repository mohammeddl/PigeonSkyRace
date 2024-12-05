package com.PigeonSkyRace.PigeonSkyRace.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.PigeonSkyRace.PigeonSkyRace.enums.Role;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ORGANIZER")
public class Organizer extends User {

    private String role = "ORGANIZER";

    @OneToMany(mappedBy = "organizer") 
    private List<Competition> competitions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

}
