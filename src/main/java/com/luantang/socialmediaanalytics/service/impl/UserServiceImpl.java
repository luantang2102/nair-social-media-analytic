package com.luantang.socialmediaanalytics.service.impl;

import com.luantang.socialmediaanalytics.exception.EmailAlreadyExistException;
import com.luantang.socialmediaanalytics.exception.UserNotFoundException;
import com.luantang.socialmediaanalytics.model.UserEntity;
import com.luantang.socialmediaanalytics.repository.UserRepository;
import com.luantang.socialmediaanalytics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        if(userRepository.findByEmail(user.getEmail()).isEmpty()) {
            return userRepository.save(user);
        }
        else throw new EmailAlreadyExistException("Email already exist");
//        if(userRepository.findByEmail(user.getEmail()).isEmpty()) {
//            return userRepository.save(user);
//        }
//        else {
//            UserEntity foundedUser = userRepository.findByEmail(user.getEmail()).get();
//            if(foundedUser.isEnabled()) {
//                throw new EmailAlreadyExistException("Email already exist");
//            }
//            else {
//                return foundedUser;
//            }
//        }
    }

    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with associate email could not be found"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with associate email could not be found"));
    }

    @Override
    public UserEntity enableUserByEmail(String email) {
        UserEntity updatedUser = getUserByEmail(email);
        updatedUser.setEnabled(true);
        return userRepository.save(updatedUser);
    }
}
