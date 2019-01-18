package ru.geekbrains.pocket.backend.service.security.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.Security.Role;
import ru.geekbrains.pocket.backend.domain.Security.User;
import ru.geekbrains.pocket.backend.exception.RoleNotFoundException;
import ru.geekbrains.pocket.backend.exception.UserNotFoundException;
import ru.geekbrains.pocket.backend.repository.security.RoleRepository;
import ru.geekbrains.pocket.backend.repository.security.UserRepository;
import ru.geekbrains.pocket.backend.resource.UserResource;
import ru.geekbrains.pocket.backend.service.security.UserService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void delete(ObjectId id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User with id = " + id + " not found");
        }
        userRepository.delete(user.get());
    }

    @Override
    public List<User> getAllUsers() {
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
    public User getUserById(ObjectId id) {
        Optional<User> user = Optional.of(userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User with id = " + id + " not found")));
        return user.get();
    }

    @Override
    public User getUserByUsername(String username) {
        //Optional<User> user1 = userRepository.findByUsername(username);
        User user2 = userRepository.findFirstByUsername(username);
        Optional<User> user = Optional.of(userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User with username = '" + username + "' not found")));
        return user.get();
    }

    @Override
    public List<Role> getRolesByUsername(String username) {
        Optional<User> user = Optional.of(userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User with username = '" + username + "' not found")));
        return (List<Role>) user.get().getRoles();
    }

    @Override
    public User insert(User user) throws RuntimeException {
        Optional<Role> role = Optional.of(roleRepository.findByName("ROLE_USER").orElseThrow(
                () -> new RoleNotFoundException("Role with name = 'ROLE_USER' not found")));

        user.setRoles(Arrays.asList(role.get()));
        return userRepository.insert(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.of(userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Invalid username or password")));
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                user.get().getPassword(), mapRolesToAuthorities(user.get().getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public User validateUser(ObjectId id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User with id = " + id + " not found"));
    }

    public User validateUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User with username = " + username + " not found"));
    }
}
