package com.PigeonSkyRace.PigeonSkyRace.Mapper;

import org.mapstruct.Mapper;

import com.PigeonSkyRace.PigeonSkyRace.dto.request.UserRequest;
import com.PigeonSkyRace.PigeonSkyRace.dto.response.LoginResponse;
import com.PigeonSkyRace.PigeonSkyRace.dto.response.UserResponse;
import com.PigeonSkyRace.PigeonSkyRace.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userRequestToUserEntity(UserRequest userRequest);

    UserResponse userEntityToUserResponse(User user);

    LoginResponse LoginUserEntityToUserResponse(User user, String token);
}
