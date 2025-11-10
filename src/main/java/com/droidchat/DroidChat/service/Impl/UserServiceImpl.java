package com.droidchat.DroidChat.service.Impl;

import com.droidchat.DroidChat.dto.LoginDTO;
import com.droidchat.DroidChat.dto.ResponseDTO;
import com.droidchat.DroidChat.dto.UserDTO;
import com.droidchat.DroidChat.exception.EmailAlreadyExistsException;
import com.droidchat.DroidChat.exception.EmptyFieldException;
import com.droidchat.DroidChat.exception.InvalidCredentialsException;
import com.droidchat.DroidChat.exception.ResourceNotFoundException;
import com.droidchat.DroidChat.model.OtpModel;
import com.droidchat.DroidChat.model.UserModel;
import com.droidchat.DroidChat.repository.OtpRepository;
import com.droidchat.DroidChat.repository.UserRepository;
import com.droidchat.DroidChat.service.UserService;
import com.droidchat.DroidChat.utility.Data;
import com.droidchat.DroidChat.utility.Utilities;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public ResponseDTO registerUser(UserDTO userDto){

        // Check if email is Empty
        if(userDto.getEmail().isEmpty()){
            throw new EmptyFieldException("Please provide email");
        }

        // Check if Email already exists
        if(userRepository.findByEmail(userDto.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("Email already exists : "+userDto.getEmail());
        }

        UserModel userModel = new UserModel();

        // Set Name & Email
        userModel.setName(userDto.getName());
        userModel.setEmail(userDto.getEmail());

        // Encrypt the password before saving
        userModel.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Save User data in DB
        UserModel savedUser = userRepository.save(userModel);

        return new ResponseDTO("User is registered successfully !");
    }

    @Override
    public UserDTO loginUser(LoginDTO loginDTO) {
        // Check if User is present or not
        UserModel user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(()-> new InvalidCredentialsException("Invalid Email or Password"));

        // Match Password
        if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid Email or Password");
        }

//        System.out.println("User Data : "+ user.convertToUserDTO());

        UserDTO userDto = user.convertToUserDTO();

        userDto.setPassword("");

        // Prepare Login Response body
        return userDto;
    }

    @Override
    public ResponseDTO sendOtp(String email) throws Exception {
//         // Testing
//        System.out.println("Email in API request params : "+ email);

        // Check if Email is present or not
        UserModel user = userRepository.findByEmail(email).orElseThrow(()-> new InvalidCredentialsException("Invalid email"));

        // MimeMessage : return HTML Body in Message
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mailMessage, true);

        message.setTo(email); // Set Receiver Email
        message.setSubject("Your OTP Code"); // Set Email Subject

        // Generate OTP from Utilities
        String generatedOtp = Utilities.generateOTP();

        // Create otp data object with Given Data (i.e., email, otpCode, currentTime)
        OtpModel otp = new OtpModel(email, generatedOtp, LocalDateTime.now());

        // Save otp data in Database
        otpRepository.save(otp);

        // Username of receiver
        String username = user.getName();

        // Email Body - Call "Data" Utility Class
        message.setText(Data.otpEmailBody(generatedOtp, username), true);

        mailSender.send(mailMessage);

        return new ResponseDTO("OTP sent successfully");
    }

    @Override
    public ResponseDTO verifyOtp(String email, String otp) throws Exception {
        OtpModel otpEntity = otpRepository.findById(email).orElseThrow(()-> new ResourceNotFoundException("OTP not found"));

        // if otp does not matches, throw error
        if(!otpEntity.getOtpCode().equals(otp)){
            throw new ResourceNotFoundException("OTP is Incorrect");
        }

        return new ResponseDTO("OTP is successfully verified");
    }

    @Override
    public ResponseDTO changePassword(LoginDTO loginDTO) {
        // Check if User is present or not
        UserModel user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(()-> new InvalidCredentialsException("User not found."));

        // Update new Password (encrypted)
        user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));

        // Save in DB
        userRepository.save(user);

        return new ResponseDTO("Password updated successfully");
    }

    // Expire OTP - Job Scheduler (Check Expired OTPs every Minute and delete them)
    @Scheduled(fixedRate = 60000) // 60 seconds - Run below function every Minute automatically
    public void removeExpiredOTPs(){
        // Current Time minus 5 minutes
        LocalDateTime expiryTime = LocalDateTime.now().minusMinutes(5);

        // Store Expired OTPs in List - OTPs having 5 minutes Expiry Time
        List<OtpModel> expiredOTPList = otpRepository.findByCreationTimeBefore(expiryTime);

        // Delete all Expired OTPs from Database
        if(!expiredOTPList.isEmpty()){
            otpRepository.deleteAll(expiredOTPList);

//            System.out.println("Removed "+ expiredOTPList.size() + " expired OTPs. ");
        }

    }
}
