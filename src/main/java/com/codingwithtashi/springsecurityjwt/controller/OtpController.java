package com.codingwithtashi.springsecurityjwt.controller;

import com.codingwithtashi.springsecurityjwt.model.AuthRequest;
import com.codingwithtashi.springsecurityjwt.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    // Generate and send OTP to user's email/phone number
    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(@RequestBody AuthRequest authRequest) {
        String otp = otpService.generateOtp();

        // Send the OTP to the user's email/phone number
        boolean sent = otpService.sendOtp(authRequest.getPhoneNo(), otp);

        if (sent) {
            return new ResponseEntity<>("OTP has been sent successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to send OTP.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Validate the entered OTP
    @PostMapping("/validate")
    public ResponseEntity<String> validateOtp(@RequestBody AuthRequest authRequest) {
        boolean valid = otpService.validateOtp(authRequest.getPhoneNo(), authRequest.getOtp());

        if (valid) {
            return new ResponseEntity<>("OTP is valid.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid OTP.", HttpStatus.BAD_REQUEST);
        }
    }
}

