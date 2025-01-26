package com.auth.user.service;

import com.auth.user.mapper.UserMapper;
import com.auth.user.repository.UserRepository;
import com.auth.user.repository.entity.User;
import com.auth.user.dto.UserDTO;
import com.auth.user.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  public UserService(UserRepository userRepository, UserMapper userMapper, KafkaTemplate<String, String> kafkaTemplate) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.kafkaTemplate = kafkaTemplate;
  }

  // R
  public UserDTO getUserById(String id) throws UserNotFoundException {
    User user = userRepository.getUserById(id);
    if (user == null) {
      throw new UserNotFoundException("User not found");
    }
    return userMapper.toUserDTO(user);
  }

  public UserDTO getUserByEmail(String email) throws UserNotFoundException {
    User user = userRepository.getUserByEmail(email);
    if (user == null) {
      throw new UserNotFoundException("User not found");
    }
    return userMapper.toUserDTO(user);
  }
}
