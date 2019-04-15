package com.project.bidding.dto;

import com.project.bidding.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String company;
    private String position;
    private String roles;

    public static UserDto from(User user) {
        return new UserDto().builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .company(user.getCompany())
                .position(user.getPosition())
                .roles(user.getRoles().toString())
                .build();
    }

    public static List<UserDto> from(List<User> users) {
        return users.stream().map(UserDto::from).collect(Collectors.toList());
    }
}
