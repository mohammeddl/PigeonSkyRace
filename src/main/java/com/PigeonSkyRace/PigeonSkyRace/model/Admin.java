package com.PigeonSkyRace.PigeonSkyRace.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.PigeonSkyRace.PigeonSkyRace.enums.Role;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Collection;
import java.util.List;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

}