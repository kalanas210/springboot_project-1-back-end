package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    private Integer userId;
    private String fullName;
    private String email;
    private String password;
    private String userType;
    private String adminId;
    private String phoneNumber;
    private String city;
}
