package ru.geekbrains.pocket.backend.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.db.Role;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserProfile;
import ru.geekbrains.pocket.backend.exception.RoleNotFoundException;
import ru.geekbrains.pocket.backend.exception.UserNotFoundException;
import ru.geekbrains.pocket.backend.repository.RoleRepository;
import ru.geekbrains.pocket.backend.repository.UserRepository;
import ru.geekbrains.pocket.backend.resource.UserResource;
import ru.geekbrains.pocket.backend.service.UserService;
import ru.geekbrains.pocket.backend.util.RandomStringUtil;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void delete(ObjectId id) throws RuntimeException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User with id = " + id + " not found");
        }
        userRepository.delete(user.get());
    }

    @Override
    public void delete(String email) {
        userRepository.deleteByEmail(email);
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
    public User getUserById(ObjectId id) throws RuntimeException {
        //TODO исправить
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id)
                    .orElseThrow(
                            () -> new UserNotFoundException("User with id = " + id + " not found"));
            return user;
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
//        user = Optional.of(user).orElseThrow(
//                () -> new UserNotFoundException("User with email = " + email + " not found"));
        return user;
    }

    @Override
    public User getUserByUsername(String username) throws RuntimeException {
        //User user2 = userRepository.findFirstByUsername(username);
        User user = Optional.of(userRepository.findByUsername(username)).orElseThrow(
                () -> new UserNotFoundException("User with username = '" + username + "' not found"));
        return user;
    }

    @Override
    public List<Role> getRolesByUsername(String username) throws RuntimeException {
        User user = Optional.of(userRepository.findByUsername(username)).orElseThrow(
                () -> new UserNotFoundException("User with username = '" + username + "' not found"));
        return (List<Role>) user.getRoles();
    }

    @Override
    public User insert(User user) throws RuntimeException {
        Role role = Optional.of(roleRepository.findByName("ROLE_USER")).orElseThrow(
                () -> new RoleNotFoundException("Role with name = 'ROLE_USER' not found."));
        User user1 = new User();
        user1.setProfile(new UserProfile("ddd"));
        userRepository.insert(user1);

        user.setRoles(Arrays.asList(role));
        return userRepository.insert(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }


    public String addNewUser(User user) {

        User compare = userRepository.findByEmailMatches(user.getEmail());
        if (compare == null) {
            userRepository.save(user);
            return userRepository.findByEmailMatches(user.getEmail()).getId().toString();
        }
        return "user already exists in DB";
    }

    public String deleteUser(User user) {
        User onDelete = findByEmail(user.getEmail());
        if (onDelete != null) {
            userRepository.delete(onDelete);
            return "user_id :" + onDelete.getId() + " removed successful";
        }
        return "user not found in DB";
    }

    public User findUserByID(ObjectId id) {
        return userRepository.findById(id).get();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmailMatches(email);
    }

    @Override
    public User findUsersByUsername(String username) {
        return userRepository.findByProfileUsername(username);
    }

    @Override
    public String updateUser(User user) {
        User updatingUser = userRepository.findByEmailMatches(user.getEmail());
        if (updatingUser != null)
            return userRepository.save(updatingUser).getId().toString();
        else return "user not found";
    }

    @Override
    public String updateUserProfile(User user, UserProfile userProfile) {
        User updatingUser = userRepository.findByEmailMatches(user.getEmail());
        if (updatingUser != null) {
            updatingUser.setProfile(userProfile);
            return userRepository.save(updatingUser).getId().toString();

        } else return "user not found";
    }

    @Override
    public String updateUserFullName(User user, String fullName) {
        User updatingUser = userRepository.findByEmailMatches(user.getEmail());
        if (updatingUser != null) {
            UserProfile thisUserProfile = updatingUser.getProfile();
            thisUserProfile.setFullName(fullName);
            updatingUser.setProfile(thisUserProfile);
            return userRepository.save(updatingUser).getId().toString();

        } else return "user not found";
    }

    @Override
    public String updateUserUsername(User user, String username) {
        User updatingUser = userRepository.findByEmailMatches(user.getEmail());
        if (updatingUser != null) {
            UserProfile thisUserProfile = updatingUser.getProfile();
            thisUserProfile.setUsername(username);
            updatingUser.setProfile(thisUserProfile);
            return userRepository.save(updatingUser).getId().toString();

        } else return "user not found";
    }


    @Override
    public String updateUsersLastSeen(User user, Date date) {
        User updatingUser = userRepository.findByEmailMatches(user.getEmail());
        if (updatingUser != null) {
            UserProfile thisUserProfile = updatingUser.getProfile();
            thisUserProfile.setLastSeen(date);
            updatingUser.setProfile(thisUserProfile);
            return userRepository.save(updatingUser).getId().toString();

        } else return "user not found";
    }

    @Override
    public String updateUsersPassword(User user, String password) {
        User updatingUser = userRepository.findByEmailMatches(user.getEmail());
        if (updatingUser != null) {
            updatingUser.setPassword(password);
            return userRepository.save(updatingUser).getId().toString();
        } else return "user not found";
    }

    //Spring Security - Authentication via email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.of(userRepository.findByEmail(email)).orElseThrow(
                () -> new UserNotFoundException("Invalid email or password"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public User validateUser(ObjectId id) throws UsernameNotFoundException {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User with id = " + id + " not found"));
    }

    public User validateUser(String username) throws UsernameNotFoundException {
        return Optional.of(userRepository.findByUsername(username)).orElseThrow(
                () -> new UserNotFoundException("User with username = " + username + " not found"));
    }

}
