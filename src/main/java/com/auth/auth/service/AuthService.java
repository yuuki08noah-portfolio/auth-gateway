package com.auth.auth.service;

import com.auth.auth.controller.dto.SignInByEmailDTO;
import com.auth.auth.controller.dto.SignInByIdDTO;
import com.auth.auth.exception.MissingSignInParametersException;
import com.auth.user.dto.UserDTO;
import com.auth.user.exception.UserNotFoundException;
import com.auth.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserService userService;
  private final TokenService tokenService;

  public void signIn(SignInByIdDTO signInByIdDTO) throws MissingSignInParametersException, UserNotFoundException {
    if (signInByIdDTO.id() == null || signInByIdDTO.password() == null) {
      throw new MissingSignInParametersException("Missing parameters");
    }
    UserDTO userDTO = userService.getUserById(signInByIdDTO.id());
    if (!BCrypt.checkpw(signInByIdDTO.password(), userDTO.password())) {
      throw new MissingSignInParametersException("Missing parameters");
    }
  }

  public void signIn(SignInByEmailDTO signInByEmailDTO) throws MissingSignInParametersException, UserNotFoundException {
    if (signInByEmailDTO.email() == null || signInByEmailDTO.password() == null) {
      throw new MissingSignInParametersException("Missing parameters");
    }
    UserDTO userDTO = userService.getUserByEmail(signInByEmailDTO.email());
    if (!BCrypt.checkpw(signInByEmailDTO.password(), userDTO.password())) {
      throw new MissingSignInParametersException("Missing parameters");
    }
  }
}
