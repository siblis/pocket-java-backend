package ru.geekbrains.pocket.backend.service.DB.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.entitiesDB.Profile;
import ru.geekbrains.pocket.backend.domain.entitiesDB.Users;
import ru.geekbrains.pocket.backend.repository.DB.UsersRepository;
import ru.geekbrains.pocket.backend.service.DB.interfaces.UserService;
import ru.geekbrains.pocket.backend.service.RandomStringUtil;


import java.util.Date;
import java.util.List;
import java.util.Random;


@Service
public class UserServiceImpl implements UserService {

    UsersRepository userRepository;

    @Autowired
    public void setUserRepository(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String addNewUser(Users user) {

          Users compare = userRepository.findByEmailMatches(user.getEmail()).get();
          if (compare == null){
              userRepository.save(user);
              return userRepository.findByEmailMatches(user.getEmail()).get().getId();
          }
          return "user already exists in DB";
    }

    public String deleteUser(Users user) {
        Users onDelete = findByEmail(user.getEmail());
        if (onDelete != null){
            userRepository.delete(onDelete);
            return "user_id :"+onDelete.getId()+" removed successful";
        }
        return "user not found in DB";
    }


    public String addNewTestUser() {
        Users randomUser = new Users();
        randomUser.setEmail(RandomStringUtil.randomString(10));
        randomUser.setPassword(RandomStringUtil.randomString(10));
        randomUser.setCreated_at(new Date());
        randomUser.setProfile(new Profile(RandomStringUtil.randomString(5), RandomStringUtil.randomString(5), new Date()));
        return userRepository.save(randomUser).getId();
    }

    public Users getRandomUserFromDB(){
        List<Users> users =userRepository.findAll();
        int length = users.size();
        Random random = new Random();
        return users.get(random.nextInt(length-1));
    }

    public Users getTestUser1() {
        return userRepository.findByProfile_NameAndProfile_LastName("test1","test1");
    }

    public Users getTestUser2() {
        return userRepository.findByProfile_NameAndProfile_LastName("test2","test2");
    }

    public Users findUserByID(String id){
        return userRepository.findById(id).get();
    }

    public Users findByEmail(String email){
        return userRepository.findByEmailMatches(email).get();
    }

    @Override
    public String updateUser(Users user) {
        Users updatingUser = userRepository.findByEmailMatches(user.getEmail()).get();
        if (updatingUser!=null)
        return userRepository.save(updatingUser).getId();
        else return "user not found";
    }

    @Override
    public String updateUserProfile(Users user, Profile profile) {
        Users updatingUser = userRepository.findByEmailMatches(user.getEmail()).get();
        if (updatingUser!=null) {
            updatingUser.setProfile(profile);
            return userRepository.save(updatingUser).getId();

        }
        else return "user not found";
    }

    @Override
    public String updateUserFirstName(Users user,String firstName) {
        Users updatingUser = userRepository.findByEmailMatches(user.getEmail()).get();
        if (updatingUser!=null) {
            Profile thisUserProfile = updatingUser.getProfile();
            thisUserProfile.setName(firstName);
            updatingUser.setProfile(thisUserProfile);
            return userRepository.save(updatingUser).getId();

        }
        else return "user not found";
    }

    @Override
    public String updateUserLastName(Users user, String lastName) {
        Users updatingUser = userRepository.findByEmailMatches(user.getEmail()).get();
        if (updatingUser!=null) {
            Profile thisUserProfile = updatingUser.getProfile();
            thisUserProfile.setLastName(lastName);
            updatingUser.setProfile(thisUserProfile);
            return userRepository.save(updatingUser).getId();

        }
        else return "user not found";
    }

    @Override
    public String updateUsersLastSeen(Users user, Date date) {
        Users updatingUser = userRepository.findByEmailMatches(user.getEmail()).get();
        if (updatingUser!=null) {
            Profile thisUserProfile = updatingUser.getProfile();
            thisUserProfile.setLastSeen(date);
            updatingUser.setProfile(thisUserProfile);
            return userRepository.save(updatingUser).getId();

        }
        else return "user not found";
    }

    @Override
    public String updateUsersPassword(Users user,String password) {
        Users updatingUser = userRepository.findByEmailMatches(user.getEmail()).get();
        if (updatingUser!=null) {
            updatingUser.setPassword(password);
            return userRepository.save(updatingUser).getId();

        }
        else return "user not found";
    }


}
