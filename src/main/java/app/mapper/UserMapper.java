package app.mapper;

import app.dto.UserDto;
import app.model.User;
import app.model.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User, UserDto>{

    @Override
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setUserRole(user.getUserRole().name());
        return userDto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setUserRole(UserRole.valueOf(userDto.getUserRole()));
        return user;
    }
}
