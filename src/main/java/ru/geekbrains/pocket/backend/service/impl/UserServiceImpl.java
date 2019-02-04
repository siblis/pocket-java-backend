package ru.geekbrains.pocket.backend.service.impl;

import com.mongodb.MongoWriteException;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.SystemUser;
import ru.geekbrains.pocket.backend.domain.db.*;
import ru.geekbrains.pocket.backend.exception.InvalidOldPasswordException;
import ru.geekbrains.pocket.backend.exception.RoleNotFoundException;
import ru.geekbrains.pocket.backend.exception.UserAlreadyExistException;
import ru.geekbrains.pocket.backend.exception.UserNotFoundException;
import ru.geekbrains.pocket.backend.repository.*;
import ru.geekbrains.pocket.backend.resource.UserResource;
import ru.geekbrains.pocket.backend.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";
    private final static String ROLE_USER = "ROLE_USER";
    public static String APP_NAME = "Pocket";

    @Autowired
    private UserTokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public PasswordResetToken createPasswordResetTokenForUser(User user, String token) {
        final PasswordResetToken userToken = new PasswordResetToken(token, user);
        return passwordTokenRepository.save(userToken);
    }

    @Override
    public UserToken createVerificationTokenForUser(User user) {
        final String token = UUID.randomUUID().toString();
        final UserToken userToken = new UserToken(token, user);
        return tokenRepository.save(userToken);
    }

    @Override
    public UserToken createVerificationTokenForUser(User user, String token) {
        final UserToken userToken = new UserToken(token, user);
        return tokenRepository.save(userToken);
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
    public User getUserByToken(String token) {
        final UserToken UserToken = tokenRepository.findByToken(token);
        if (UserToken != null) {
            return UserToken.getUser();
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) throws RuntimeException {
        //User user2 = userRepository.findFirstByUsername(username);
        User user = Optional.of(userRepository.findByUsername(username)).orElseThrow(
                () -> new UserNotFoundException("User with username = '" + username + "' not found"));
        return user;
    }

    @Override
    public UserToken getVerificationToken(User user) {
        return tokenRepository.findByUser(user);
    }

    @Override
    public UserToken getVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public UserToken generateNewVerificationToken(final String token) {
        UserToken vToken = tokenRepository.findByToken(token);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
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
        user.setRoles(Collections.singletonList(role));
        return userRepository.insert(user);
    }

    @Override
    public User registerNewUserAccount(SystemUser account)
            throws UserAlreadyExistException, MongoWriteException {
        if (userRepository.findByEmail(account.getEmail()) != null) {
            throw new UserAlreadyExistException("There is an account with that email adress: " + account.getEmail());
        }

        final User user = new User();

        user.setEmail(account.getEmail());
        user.setPassword(passwordEncoder.encode(account.getPassword())); //шифруем
        //user.setUsing2FA(account.isUsing2FA());
        user.setUsername(account.getFirstname());
        user.setProfile(new UserProfile(account.getFirstname(), account.getFirstname() + " " + account.getLastname()));
        user.setRoles(Arrays.asList(getRoleUser()));
        try {
            return userRepository.insert(user);
        } catch (MongoWriteException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    @Override
    public User registerNewUserAccount(String email, String password, String name)
            throws UserAlreadyExistException, MongoWriteException {
        if (userRepository.findByEmail(email) != null) {
            throw new UserAlreadyExistException("There is an account with that email adress: " + email);
        }

        final User user = new User();

        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); //получаем хэш пароля
        //user.setUsing2FA(account.isUsing2FA());
        user.setUsername(name);
        user.setProfile(new UserProfile(name));
        user.setRoles(Arrays.asList(getRoleUser()));
        try {
            return userRepository.insert(user);
        } catch (MongoWriteException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    private Role getRoleUser() {
        Role roleUser = roleRepository.findByName(ROLE_USER);
        if (roleUser == null)
            roleUser = roleRepository.insert(new Role(ROLE_USER));
        return roleUser;
    }

    @Override
    public User update(User user) throws MongoWriteException {
        try {
            return userRepository.save(user);
        } catch (MongoWriteException ex) {
            log.error(ex.getMessage());
        }
        return null;
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
    public User updateNameAndPassword(User user, String name, String oldPassword, String newPassword)
            throws InvalidOldPasswordException {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidOldPasswordException("Old & current password does not match!");
        }
        user.setUsername(name);
        user.getProfile().setUsername(name);
        user.setPassword(passwordEncoder.encode(newPassword));
        return update(user);
    }

    @Override
    public String validatePasswordResetToken(ObjectId id, String token) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
        if ((passToken == null) || (passToken.getUser().getId() != id)) {
            return "invalidToken";
        }

        final Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "expired";
        }

        final User user = passToken.getUser();
        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }

    //Spring Security - Authentication via email
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = Optional.of(userRepository.findByEmail(email)).orElseThrow(
//                () -> new UserNotFoundException("Invalid email or password"));
//        boolean accountNonExpired = true;
//        boolean credentialsNonExpired = true;
//        boolean accountNonLocked = true;
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
//                user.getPassword(),
//                user.isEnabled(),
//                accountNonExpired, credentialsNonExpired, accountNonLocked,
//                getAuthorities(user.getRoles()));
//    }

//    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//    }

    public User validateUser(ObjectId id) throws UsernameNotFoundException {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User with id = " + id + " not found"));
    }

    public User validateUser(String username) throws UsernameNotFoundException {
        return Optional.of(userRepository.findByUsername(username)).orElseThrow(
                () -> new UserNotFoundException("User with username = " + username + " not found"));
    }

    @Override
    public String validateVerificationToken(String token) {
        final UserToken UserToken = tokenRepository.findByToken(token);
        if (UserToken == null) {
            return TOKEN_INVALID;
        }

        final User user = UserToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((UserToken.getExpiryDate()
                .getTime()
                - cal.getTime()
                .getTime()) <= 0) {
            tokenRepository.delete(UserToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        // tokenRepository.delete(UserToken);
        userRepository.save(user);
        return TOKEN_VALID;
    }

}
