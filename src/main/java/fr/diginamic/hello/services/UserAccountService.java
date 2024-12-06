package fr.diginamic.hello.services;

import fr.diginamic.hello.entites.CustomUserDetails;
import fr.diginamic.hello.entites.UserAccount;
import fr.diginamic.hello.repositories.UserAccountRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService implements UserDetailsService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserAccountService.class);


    @PostConstruct
    public void init() {
        if (userAccountRepository.findByUsername("coucou") == null) {
            UserAccount user = new UserAccount("coucou", passwordEncoder.encode("coucou"), "ROLE_USER");
            userAccountRepository.save(user);
        }

        if (userAccountRepository.findByUsername("admin") == null) {
            UserAccount admin = new UserAccount("admin", passwordEncoder.encode("admin"), "ROLE_ADMIN");
            userAccountRepository.save(admin);
        }
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.debug("Entering in loadUserByUsername Method...");
        UserAccount user = userAccountRepository.findByUsername(username);
        if (user == null) {
            logger.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }
        logger.info("User Authenticated Successfully..!!!");
        return new CustomUserDetails(user);
    }




}
