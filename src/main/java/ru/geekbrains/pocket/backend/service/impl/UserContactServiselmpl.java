package ru.geekbrains.pocket.backend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.repository.UserContactsRepository;

@Slf4j
@Service
public class UserContactServiselmpl {
    @Autowired
    private UserContactsRepository userContactsRepository;
}
