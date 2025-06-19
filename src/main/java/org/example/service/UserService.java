package org.example.service;

import org.example.dto.Otp;
import org.example.dto.SignInRequest;
import org.example.dto.Users;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Users saveUser(Users user);
    Users authenticateUser(SignInRequest signInRequest) throws Exception;

    void authenticate(String email);


    boolean validateOtp(Otp otp);

    Users resetPassword(SignInRequest request);

    void deleteUser(Integer id);

    Users updateUser(Users user);
}
