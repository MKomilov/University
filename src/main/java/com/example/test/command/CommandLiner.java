package com.example.test.command;


import com.example.test.entity.Authority;
import com.example.test.entity.Student;
import com.example.test.entity.enums.RoleEnum;
import com.example.test.repository.RoleRepository;
import com.example.test.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommandLiner implements CommandLineRunner {

    final RoleRepository roleRepository;
    final StudentRepository userRepository;
    final PasswordEncoder passwordEncoder;

    /**
     * When program runs for the first time, command.line-runner should be true in application.yaml
     * Then run method creates User instance, sets role as Admin and saves to database.
     * Then command.line-runner should be changed to false in order to prevent not creating User instance
     * every time the program runs.
     */
    @Value("${command.line-runner}")
    Boolean check;
    @Override
    public void run(String... args) throws Exception {
        if (check) {
            Authority authority0 = new Authority();
            authority0.setRole(RoleEnum.ADMIN);

            Authority authority1 = new Authority();
            authority1.setRole(RoleEnum.USER);

            roleRepository.save(authority0);
            roleRepository.save(authority1);

            Student user=new Student();
            user.setEmail("admin@gmail.com");
            user.setName("admin");
//            user.setCode("1111");
            user.setPassword(passwordEncoder.encode("admin"));
            userRepository.save(user);
        }
    }
}
