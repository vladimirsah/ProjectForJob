package com.example.projectforjob.services;

import com.example.projectforjob.models.Person;
import com.example.projectforjob.repositories.PeopleRepositories;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

@Service
public class PersonGetService {
    private final PeopleRepositories peopleRepositories;

    @Autowired
    public PersonGetService(PeopleRepositories peopleRepositories) {
        this.peopleRepositories = peopleRepositories;
    }


    @Transactional
    public void update(Person updatePerson) {
        peopleRepositories.save(updatePerson);
    }

}
