package com.example.projectforjob.services;

import com.example.projectforjob.models.Person;
import com.example.projectforjob.repositories.PeopleRepositories;
import com.example.projectforjob.security.PersonDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PersonDetailService implements UserDetailsService {

    private final PeopleRepositories peopleRepositories;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonDetailService(PeopleRepositories peopleRepositories, PasswordEncoder passwordEncoder) {
        this.peopleRepositories = peopleRepositories;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) {
        Optional<Person> person = peopleRepositories.findByUsername(s);

        if (person.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return new PersonDetail(person.get());
    }

    @Transactional
    public void save(Person person) {

        String codePassword = passwordEncoder.encode(person.getPassword());

        person.setPassword(codePassword);
        person.setRole("ROLE_USER");
        peopleRepositories.save(person);
    }
}
