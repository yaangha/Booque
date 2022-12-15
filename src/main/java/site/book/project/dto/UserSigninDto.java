package site.book.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.book.project.domain.User;

@NoArgsConstructor
@Data
public class UserSigninDto {
    
    private String signinUsername;
    private String signinPassword;
    
    public User dto() {
        return User.builder().username(signinUsername).password(signinPassword).build();
    }

}
