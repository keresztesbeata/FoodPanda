package app.mapper;

import app.dto.UserDto;
import app.model.User;
import app.model.UserRole;

public class UserMapper implements Mapper<User, UserDto>{

    @Override
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setUsername(user.getUsername());
        userDto.setAddress(user.getAddress());
        userDto.setUserRole(user.getUserRole().name());

        return userDto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setAddress(userDto.getAddress());
        user.setUserRole(UserRole.valueOf(userDto.getUserRole()));

        return user;
    }
}
