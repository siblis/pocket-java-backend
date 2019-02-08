package ru.geekbrains.pocket.backend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.db.PasswordResetToken;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserToken;
import ru.geekbrains.pocket.backend.repository.PasswordResetTokenRepository;
import ru.geekbrains.pocket.backend.repository.UserRepository;
import ru.geekbrains.pocket.backend.repository.UserTokenRepository;
import ru.geekbrains.pocket.backend.security.token.JwtTokenUtil;
import ru.geekbrains.pocket.backend.service.UserTokenService;

import java.util.Arrays;
import java.util.Calendar;

@Slf4j
@Service
public class UserTokenServiceImpl implements UserTokenService {
    private static final String TOKEN_INVALID = "invalidToken";
    private static final String TOKEN_EXPIRED = "expired";
    private static final String TOKEN_VALID = "valid";

    @Autowired
    private UserTokenRepository userTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public PasswordResetToken createPasswordResetTokenForUser(User user, String token) {
        final PasswordResetToken userToken = new PasswordResetToken(token, user);
        return passwordTokenRepository.save(userToken);
    }

    @Override
    public UserToken createVerificationTokenForUser(User user) {
        //final String token = UUID.randomUUID().toString();
        final String token = jwtTokenUtil.generateToken(user);
        final UserToken userToken = new UserToken(token, user);
        return userTokenRepository.save(userToken);
    }

    @Override
    public User getUserByToken(String token) {
        final UserToken UserToken = userTokenRepository.findByToken(token);
        if (UserToken != null) {
            return UserToken.getUser();
        }
        return null;
    }

    @Override
    public UserToken getVerificationToken(User user) {
        return userTokenRepository.findByUser(user);
    }

    @Override
    public UserToken getVerificationToken(String token) {
        return userTokenRepository.findByToken(token);
    }

    @Override
    public UserToken generateNewVerificationToken(final String token) {
        UserToken vToken = userTokenRepository.findByToken(token);
        //vToken.updateToken(UUID.randomUUID().toString());
        vToken.updateToken(jwtTokenUtil.generateToken(vToken.getUser()));
        vToken = userTokenRepository.save(vToken);
        return vToken;
    }

    @Override
    public UserToken newUserToken(UserToken newToken) {
        return null;
    }

    @Override
    public void deleteUserToken(UserToken tokenOnDelete) {
    }

    @Override
    public void deleteAllUserToken() {
        userTokenRepository.deleteAll();
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

    @Override
    public String validateVerificationToken(String token) {
        final UserToken UserToken = userTokenRepository.findByToken(token);
        if (UserToken == null) {
            return TOKEN_INVALID;
        }

        final User user = UserToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((UserToken.getExpiryDate()
                .getTime()
                - cal.getTime()
                .getTime()) <= 0) {
            userTokenRepository.delete(UserToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        // userTokenRepository.delete(UserToken);
        userRepository.save(user);
        return TOKEN_VALID;
    }

}
