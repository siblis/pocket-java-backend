package ru.geekbrains.pocket.backend.service.impl;

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
import ru.geekbrains.pocket.backend.exception.RoleNotFoundException;
import ru.geekbrains.pocket.backend.exception.UserAlreadyExistException;
import ru.geekbrains.pocket.backend.exception.UserNotFoundException;
import ru.geekbrains.pocket.backend.repository.PasswordResetTokenRepository;
import ru.geekbrains.pocket.backend.repository.RoleRepository;
import ru.geekbrains.pocket.backend.repository.UserRepository;
import ru.geekbrains.pocket.backend.repository.VerificationTokenRepository;
import ru.geekbrains.pocket.backend.resource.UserResource;
import ru.geekbrains.pocket.backend.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";
    private final static String ROLE_USER = "ROLE_USER";
    public static String APP_NAME = "Pocket";

    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public void createVerificationTokenForUser(User user, String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
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
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken != null) {
            return verificationToken.getUser();
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
    public VerificationToken getVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public VerificationToken generateNewVerificationToken(final String token) {
        VerificationToken vToken = tokenRepository.findByToken(token);
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
    public User registerNewUserAccount(SystemUser account) throws UserAlreadyExistException {
        if (userRepository.findByEmail(account.getEmail()) != null) {
            throw new UserAlreadyExistException("There is an account with that email adress: " + account.getEmail());
        }

        final User user = new User();

        user.setEmail(account.getEmail());
        //user.setPassword(password); //если приходит в зашифрованном виде
        user.setPassword(passwordEncoder.encode(account.getPassword())); //шифруем
        //user.setUsing2FA(account.isUsing2FA());
        user.setUsername(account.getFirstname());
        user.setProfile(new UserProfile(account.getFirstname(), account.getFirstname() + account.getLastname()));
        user.setRoles(Arrays.asList(getRoleUser()));
        return userRepository.insert(user);
    }

    @Override
    public User registerNewUserAccount(String email, String password, String name) throws UserAlreadyExistException {
        if (userRepository.findByEmail(email) != null) {
            throw new UserAlreadyExistException("There is an account with that email adress: " + email);
        }

        final User user = new User();

        user.setEmail(email);
        //user.setPassword(password); //если приходит в зашифрованном виде
        user.setPassword(passwordEncoder.encode(password)); //шифруем
        //user.setUsing2FA(account.isUsing2FA());
        user.setUsername(name);
        user.setProfile(new UserProfile(name));
        user.setRoles(Arrays.asList(getRoleUser()));
        return userRepository.insert(user);
    }

    private Role getRoleUser() {
        Role roleUser = roleRepository.findByName(ROLE_USER);
        if (roleUser == null)
            roleUser = roleRepository.insert(new Role(ROLE_USER));
        return roleUser;
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
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
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
                .getTime()
                - cal.getTime()
                .getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        // tokenRepository.delete(verificationToken);
        userRepository.save(user);
        return TOKEN_VALID;
    }

}
