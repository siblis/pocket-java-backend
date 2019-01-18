package ru.geekbrains.pocket.backend.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.UserProfile;
import ru.geekbrains.pocket.backend.domain.Role;
import ru.geekbrains.pocket.backend.domain.User;
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


    public String addNewUser(User user) {

        User compare = userRepository.findByEmailMatches(user.getEmail()).get();
        if (compare == null) {
            userRepository.save(user);
            return userRepository.findByEmailMatches(user.getEmail()).get().getId().toString();
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


    public String addNewTestUser() {
        User randomUser = new User();
        randomUser.setEmail(RandomStringUtil.randomString(10));
        randomUser.setPassword(RandomStringUtil.randomString(10));
        randomUser.setCreated_at(new Date());
        randomUser.setProfile(new UserProfile(RandomStringUtil.randomString(5), RandomStringUtil.randomString(5), new Date()));
        return userRepository.save(randomUser).getId().toString();
    }

    public User getRandomUserFromDB() {
        List<User> users = userRepository.findAll();
        int length = users.size();
        Random random = new Random();
        return users.get(random.nextInt(length - 1));
    }

    public User getTestUser1() {
        return userRepository.findByProfile_Username("tester1");
    }

    public User getTestUser2() {
        return userRepository.findByProfile_Username("tester2");
    }

    public User findUserByID(ObjectId id) {
        return userRepository.findById(id).get();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmailMatches(email).get();
    }

    @Override
    public User findUsersByUsername(String username) {
        return userRepository.findByProfile_Username(username);
    }

    @Override
    public String updateUser(User user) {
        User updatingUser = userRepository.findByEmailMatches(user.getEmail()).get();
        if (updatingUser != null)
            return userRepository.save(updatingUser).getId().toString();
        else return "user not found";
    }

    @Override
    public String updateUserProfile(User user, UserProfile userProfile) {
        User updatingUser = userRepository.findByEmailMatches(user.getEmail()).get();
        if (updatingUser != null) {
            updatingUser.setProfile(userProfile);
            return userRepository.save(updatingUser).getId().toString();

        } else return "user not found";
    }

    @Override
    public String updateUserFullName(User user, String fullName) {
        User updatingUser = userRepository.findByEmailMatches(user.getEmail()).get();
        if (updatingUser != null) {
            UserProfile thisUserProfile = updatingUser.getProfile();
            thisUserProfile.setFullName(fullName);
            updatingUser.setProfile(thisUserProfile);
            return userRepository.save(updatingUser).getId().toString();

        } else return "user not found";
    }

    @Override
    public String updateUserUsername(User user, String username) {
        User updatingUser = userRepository.findByEmailMatches(user.getEmail()).get();
        if (updatingUser != null) {
            UserProfile thisUserProfile = updatingUser.getProfile();
            thisUserProfile.setUsername(username);
            updatingUser.setProfile(thisUserProfile);
            return userRepository.save(updatingUser).getId().toString();

        } else return "user not found";
    }


    @Override
    public String updateUsersLastSeen(User user, Date date) {
        User updatingUser = userRepository.findByEmailMatches(user.getEmail()).get();
        if (updatingUser != null) {
            UserProfile thisUserProfile = updatingUser.getProfile();
            thisUserProfile.setLastSeen(date);
            updatingUser.setProfile(thisUserProfile);
            return userRepository.save(updatingUser).getId().toString();

        } else return "user not found";
    }

    @Override
    public String updateUsersPassword(User user, String password) {
        User updatingUser = userRepository.findByEmailMatches(user.getEmail()).get();
        if (updatingUser != null) {
            updatingUser.setPassword(password);
            return userRepository.save(updatingUser).getId().toString();
        } else return "user not found";
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
