package com.auth.user.mapper;

import com.auth.user.repository.entity.User;
import com.auth.user.dto.UserDTO;
import com.auth.user.dto.UserPublicDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mindrot.jbcrypt.BCrypt;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring")
public interface UserMapper {
  UserDTO toUserDTO(User user);
  
  @Mapping(target = "password", source = "password", qualifiedByName = "hashPassword")
  User toUser(UserDTO userDTO);
  UserPublicDTO toUserPublicDTO(User user);

  @Named("hashPassword")
  default String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  };
}
