package fr.diginamic.hello.repositories;

import fr.diginamic.hello.entites.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

    UserAccount findByUsernameStartsWith(String username);

    UserAccount findByUsername(String username);

    UserAccount deleteByUsername(String username);


}
