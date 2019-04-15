package com.project.bidding.controller;

import com.project.bidding.dto.UserDto;
import com.project.bidding.entity.Role;
import com.project.bidding.entity.User;
import com.project.bidding.exception.NotFoundException;
import com.project.bidding.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("admin/setting/users")
public class CustomUserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomUserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(method = RequestMethod.GET, consumes = {"application/json"})
    public ResponseEntity<List<UserDto>> showUsers(){
        List<User> users = userService.findUsers();
        List<UserDto> userDtos = UserDto.from(users);

        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Void> addUser(@RequestBody UserDto userDto) {
        User user = new User();

        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setCompany(userDto.getCompany());
        user.setPosition(userDto.getPosition());

        Set<Role> roles = new HashSet<>();
        switch (userDto.getRoles().toUpperCase()) {
            case "ADMIN" :
                roles.add(Role.ADMIN);
                user.setRoles(roles);

                break;

            default:
                roles.add(Role.USER);
                user.setRoles(roles);

                break;
        }

        return userService.save(user).map( u -> new ResponseEntity<Void>(HttpStatus.OK) )
              .orElseThrow( () -> new NotFoundException("not save"));
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.DELETE, consumes = {"application/json"})
    public ResponseEntity<Boolean> deleteUser(@PathVariable("userId") Long userId) {

        Optional<User> byUser = userService.findById(userId);
        if (byUser.isPresent())
            return new ResponseEntity<>(userService.remove(byUser.get()), HttpStatus.OK);

        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

}
