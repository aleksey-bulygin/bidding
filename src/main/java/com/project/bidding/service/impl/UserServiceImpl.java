package com.project.bidding.service.impl;

import com.project.bidding.entity.User;
import com.project.bidding.forms.UserForm;
import com.project.bidding.repository.UserRepositories;
import com.project.bidding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepositories userRepositories;

    @Autowired
    public UserServiceImpl(UserRepositories userRepositories) {
        this.userRepositories = userRepositories;
    }

    @Override
    public Optional<User> signUp(UserForm userForm) {
        return Optional.empty();
    }

    @Override
    public Optional<User> save(User user) {
        return Optional.of(userRepositories.save(user));
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepositories.findById(id);
    }

    @Override
    public List<User> findUsers() {
        return userRepositories.findAll();
    }

    @Override
    public Boolean remove(User user) {
        userRepositories.delete(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.of(userRepositories.findByUsername(username)).map(
                user -> new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles()))
                .orElseThrow( () ->  new UsernameNotFoundException("Invalid username"));
    }

}
