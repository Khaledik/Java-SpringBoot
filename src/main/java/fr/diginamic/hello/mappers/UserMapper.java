package fr.diginamic.hello.mappers;

import fr.diginamic.hello.entites.UserAccount;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserDetails toUserDetails(UserAccount userAccount) {
        return User.builder().username(userAccount.getUsername()).password(userAccount.getPassword()).authorities(userAccount.getAuthorities()).build();
    }


}
