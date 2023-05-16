package kz.m46.resume.services;

import kz.m46.resume.models.JwtTokenDto;
import kz.m46.resume.models.LoginDto;
import kz.m46.resume.models.UserDto;
import kz.m46.resume.models.UserSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    JwtTokenDto login(LoginDto authRequest);

    void registration(UserDto dto);

    UserDto getUserById(Long id);

    Page<UserDto> getAllUsersByPagination(Pageable pageable, UserSearchDto userSearchDto);

    UserDto updateUser(UserDto userDto);

    void deleteUser(Long id);
}
