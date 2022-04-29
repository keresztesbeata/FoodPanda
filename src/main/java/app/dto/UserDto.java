package app.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String userRole;
}
