package com.PigeonSkyRace.PigeonSkyRace.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
public abstract class User {
    @Id
    protected ObjectId id;
    protected String name;
    protected String password;
    protected String email;
    protected String role;
}
