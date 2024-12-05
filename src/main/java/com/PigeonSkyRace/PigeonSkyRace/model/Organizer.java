package com.PigeonSkyRace.PigeonSkyRace.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ORGANIZER")
public class Organizer extends User {

    @OneToMany(mappedBy = "organizer")
    private List<Competition> competitions;

}
