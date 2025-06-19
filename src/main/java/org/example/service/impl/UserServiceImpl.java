package org.example.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.dto.Otp;
import org.example.dto.SignInRequest;
import org.example.dto.Users;
import org.example.entity.UsersEntity;
import org.example.repository.UsersRepository;
import org.example.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JavaMailSender mailSender;
    public static final String SENDER_EMAIL = "taskinahned774@gmail.com";
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final String adminId="12345";
    @Override
    public Users saveUser(Users user) {
        if(user.getUserType().equals("admin")){
            if(!user.getAdminId().equals(adminId)){
                throw new IllegalArgumentException("Not valid admin Id");
            }
        }
        UsersEntity usersEntity = usersRepository.save(modelMapper.map(user, UsersEntity.class));

        String subject = "Registration Successful";
        String body = "Dear " + user.getFullName() + ",\n\n" +
                "Your registration was successful. You can now login to the system.\n\n" +
                "Your Id is : "+usersEntity.getUserId() +
                "Best regards,\n" +
                "Taskin Ahned";
        sendEmail(user.getEmail(), subject, body);

        return modelMapper.map(usersEntity, Users.class);
    }

    @Override
    public Users authenticateUser(SignInRequest signInRequest) throws Exception {
        UsersEntity byEmail = usersRepository.findByEmailAndIsDisabledFalse(signInRequest.getEmail());
        if(byEmail.getPassword().equals(signInRequest.getPassword())){
            return modelMapper.map(byEmail, Users.class);
        }else{
            throw new Exception("Invalid password");
        }
    }

    private String createOTP() {
        return String.format("%04d", new Random().nextInt(10000));
    }


    @Override
    public void authenticate(String email){
        try {
            UsersEntity usersEntity = usersRepository.findByEmail(email).orElseThrow(() -> new Exception("Could not find"));
            if(usersEntity != null){
                String otp=createOTP();
                usersEntity.setOtpNumber(otp);
                usersRepository.save(usersEntity);
                String subject = "Authentication";
                String body = "Dear " + usersEntity.getFullName() + ",\n\n" +
                        "Your OTP is : \n\n" + otp +" "+
                        "Best regards,\n" +
                        "Taskin Ahned";
                sendEmail(usersEntity.getEmail(), subject, body);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean validateOtp(Otp otp) {
        try {
            UsersEntity usersEntity = usersRepository.findByEmail(otp.getEmail()).orElseThrow(() -> new Exception("Could not find"));
            if(usersEntity.getOtpNumber().equals(otp.getOtpNumber())){
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Users resetPassword(SignInRequest request) {
        try{
            UsersEntity usersEntity = usersRepository.findByEmail(request.getEmail()).orElseThrow(() -> new Exception("Could not find"));
            usersEntity.setPassword(request.getPassword());
            UsersEntity saved = usersRepository.save(usersEntity);
            return modelMapper.map(saved, Users.class);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(Integer id) {
        try {
            UsersEntity usersEntity = usersRepository.findByUserIdAndIsDisabledFalse(id);
            usersEntity.setDisabled(true);
            usersRepository.save(usersEntity);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Users updateUser(Users user) {
        try{
            UsersEntity usersEntity = usersRepository.save(modelMapper.map(user, UsersEntity.class));
            return modelMapper.map(usersEntity, Users.class);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    boolean sendEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(SENDER_EMAIL);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);

            return true;
        } catch (MessagingException e) {

            throw new RuntimeException("Failed to send email", e);
        }
    }
}
