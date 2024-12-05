package com.PigeonSkyRace.PigeonSkyRace.Mapper;

import org.mapstruct.Mapper;
import org.springframework.security.core.userdetails.UserDetails;

import com.PigeonSkyRace.PigeonSkyRace.dto.embeded.UserDetailsResponse;
import com.PigeonSkyRace.PigeonSkyRace.dto.request.PigeonsRequest;
import com.PigeonSkyRace.PigeonSkyRace.dto.response.PigeonsResponse;
import com.PigeonSkyRace.PigeonSkyRace.model.Pigeon;

@Mapper(componentModel = "spring")
public interface PigeonMapper {

    Pigeon pigeonRequestToPigeonEntity(PigeonsRequest pigeonRequest);

    PigeonsResponse pigeonEntityToPigeonResponse(Pigeon pigeon);

    default UserDetailsResponse userDetailsToUserDetailsResponse(UserDetails userDetails) {
        return new UserDetailsResponse(userDetails.getUsername());
    }

}
