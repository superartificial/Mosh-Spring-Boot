package nz.clem.store.mappers;

import nz.clem.store.dtos.RegisterUserRequest;
import nz.clem.store.dtos.UserDto;
import nz.clem.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(RegisterUserRequest registerUserRequest);

}
