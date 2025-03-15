package com.example.CourseWork_Server.mapper;

import com.example.CourseWork_Server.model.User;
import com.example.CourseWork_Server.security.dto.auth.RegisterDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
  User dtoToEntity(RegisterDto registerDto);
}
