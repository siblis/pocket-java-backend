package ru.geekbrains.pocket.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.pocket.backend.domain.Role;
import ru.geekbrains.pocket.backend.domain.SystemUser;
import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.exception.RoleNotFoundException;
import ru.geekbrains.pocket.backend.exception.UserNotFoundException;
import ru.geekbrains.pocket.backend.repository.RoleRepository;
import ru.geekbrains.pocket.backend.repository.UserRepository;
import ru.geekbrains.pocket.backend.resource.UserResource;
import ru.geekbrains.pocket.backend.service.UserService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User with id = " + id + " not found");
        }
        userRepository.delete(user.get());
    }

    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserResource> getAllUserResources() {
        return userRepository.findAll()
                .stream()
                .map(UserResource::new)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user = Optional.of(userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User with id = " + id + " not found")));
        return user.get();
    }

    @Override
    @Transactional
    public User getUserByUsername(String username) {
        Optional<User> user = Optional.of(userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User with username = " + username + " not found")));
        return user.get();
    }

    @Override
    public Collection<Role> getRolesByUsername(String username) {
        Optional<User> user = Optional.of(userRepository.findUserByUsernameWithRoles(username).orElseThrow(
                () -> new UserNotFoundException("User with username = " + username + " not found")));
        return user.get().getRoles();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    //for WebController
    @Override
    @Transactional
    public User save(SystemUser systemUser) {
        User user = new User();
        user.setUsername(systemUser.getUsername());
        user.setPassword(passwordEncoder.encode(systemUser.getPassword()));
        user.setLastname(systemUser.getLastname());
        user.setFirstname(systemUser.getFirstname());
        user.setEmail(systemUser.getEmail());

        Optional<Role> role = Optional.of(roleRepository.findByName("ROLE_USER").orElseThrow(
                () -> new RoleNotFoundException("Role with name = 'ROLE_USER' not found")));

        user.setRoles(Arrays.asList(role.get()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.of(userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Invalid username or password")));
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                user.get().getPassword(), mapRolesToAuthorities(user.get().getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public User validateUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User with id = " + id + " not found"));
    }

    public User validateUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User with username = " + username + " not found"));
    }
}
