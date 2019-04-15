package com.project.bidding.service;

import com.project.bidding.entity.User;
import com.project.bidding.forms.UserForm;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> save(User user);

    Optional<User> signUp(UserForm userForm);

    Optional<User> findById(Long id);

    List<User> findUsers();

    Boolean remove(User user);

}
