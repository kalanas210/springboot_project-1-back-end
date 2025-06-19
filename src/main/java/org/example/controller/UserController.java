package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.Otp;
import org.example.dto.SignInRequest;
import org.example.dto.Users;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/api/v1")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/save")
    public ResponseEntity<Users> saveUser(@RequestBody Users user) {
        try{
            Users savedUser = userService.saveUser(user);
            return new ResponseEntity<Users>(savedUser,HttpStatus.CREATED);
        }catch (IllegalArgumentException illegalArgumentException){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/sign-in")
    public ResponseEntity<Users> signIn(@RequestBody SignInRequest signInRequest) {
        try{
            Users authenticateUser = userService.authenticateUser(signInRequest);
            return new ResponseEntity<>(authenticateUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/authenticate/{email}")
    ResponseEntity<String> authenticate(@PathVariable String email) {
        try{
            userService.authenticate(email);
            return new ResponseEntity<>(HttpStatus.CONTINUE);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/otp")
    ResponseEntity<String> getUserById(@RequestBody Otp otp) {
        try{
            if(userService.validateOtp(otp)){
                return new ResponseEntity<>("OTP validated successfully", HttpStatus.OK);
            }else {
                return new ResponseEntity<>("Invalid OTP", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/reset-password")
    ResponseEntity<Users> resetPassword(@RequestBody  SignInRequest request){
        try{
            return new ResponseEntity<>(userService.resetPassword(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        try{
            userService.deleteUser(id);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    ResponseEntity<Users> updateUser(@RequestBody Users user) {
        try{
            return new ResponseEntity<>(userService.updateUser(user), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
