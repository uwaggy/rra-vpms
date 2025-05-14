package com.rra.template.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j

public class UserService {
    // Repository for accessing and managing User data in the database
    private final UserRepository userRepository;

    // Mapper for converting between RegisterRequestDTO ↔ User ↔ UserResponseDTO
    private final UserMapper userMapper;

    // Password encoder for encrypting user passwords
    private final PasswordEncoder passwordEncoder;

    // Method to create and save a new user from the registration DTO
    public UserResponseDTO createUser(RegisterRequestDTO user){

        // Check if a user already exists with the given email, national ID, or phone number
        if(userRepository.existsByEmailOrNationalIdOrPhoneNumber(user.email(), user.nationalId(),user.phoneNumber())){
            // If yes, throw an exception to prevent duplication
            throw new BadRequestException("User with this email or nationalId or phoneNumber already exists");
        }

        // Convert the RegisterRequestDTO to a User entity using the mapper
        var newUser = userMapper.toEntity(user);

        // Encrypt and set the password
        newUser.setPassword(passwordEncoder.encode(user.password()));

        // Set the default role to USER
        newUser.setRole(Role.USER);

        // Disable the user account until activated manually
        newUser.setEnabled(false);

        // Log the creation of the user
        log.info("User created: {}", newUser);

        // Save the new user to the database
        userRepository.save(newUser);

        // Convert and return the saved user as a response DTO
        return userMapper.toResponseDTO(newUser);
    }

    // Method to update a user's password using their email
    public void changeUserPassword(String userEmail, String newPassword){
        // Find the user by email or throw an exception if not found
        var user = findByEmail(userEmail);

        // Encrypt and update the user's password
        user.setPassword(passwordEncoder.encode(newPassword));

        // Save the updated user back to the database
        userRepository.save(user);
    }

    // Method to activate a user's account using their email
    public void activateUserAccount(String userEmail){
        // Find the user by email
        var user = findByEmail(userEmail);

        // Set the account to enabled
        user.setEnabled(true);

        // Save the change to the database
        userRepository.save(user);
    }

    // Helper method to find a user by email or throw an exception if not found
    public User findByEmail(String email){
        // Return the user if found, otherwise throw BadRequestException
        return userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("User with that email not found."));
    }
}
