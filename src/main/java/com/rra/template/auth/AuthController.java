package com.rra.template.auth;


import com.rra.template.auth.dtos.RegisterRequestDTO;
import com.rra.template.email.EmailService;
import com.rra.template.user.UserService;
import com.rra.template.user.dtos.UserResponseDTO;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final OtpService otpService;
    private final EmailService emailService;

    @PostMapping("/auth")
    @RateLimiter(name="auth-rate-limiter")
    public ResponseEntity<UserResponseDTO> registerUser(
            @Valid @RequestBody RegisterRequestDTO user, UriComponentsBuilder uriBuilder){

        var userResponse = userService.createUser(user);

        // Build the URI for the created user to include in the response header
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userResponse.id()).toUri();

        // Generate a verification OTP to be sent to the user's email
        var otpToSend = otpService.generateOtp(userResponse.email(), OtpType.VERIFY_ACCOUNT);

        // Send the verification OTP via email
        emailService.sendAccountVerificationEmail(userResponse.email(), userResponse.firstName(), otpToSend);

        // Return a "201 Created" response with the user data and location URI
        return ResponseEntity.created(uri).body(userResponse);
    }


}
