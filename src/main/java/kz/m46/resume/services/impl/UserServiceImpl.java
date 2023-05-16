package kz.m46.resume.services.impl;

import kz.m46.resume.entity.RoleEntity;
import kz.m46.resume.entity.UserEntity;
import kz.m46.resume.exception.GeneralApiServerException;
import kz.m46.resume.models.*;
import kz.m46.resume.models.enums.RoleType;
import kz.m46.resume.repositories.UserRepository;
import kz.m46.resume.security.JwtUtils;
import kz.m46.resume.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;

    @Lazy
    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
    }

    @Override
    public JwtTokenDto login(LoginDto authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        String jwt = jwtUtils.generateToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return JwtTokenDto.builder()
                .token(jwt)
                .build();
    }

    @Override
    public void registration(UserDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new GeneralApiServerException("Email уже сущетствует", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = mapper.map(dto, UserEntity.class);
        userEntity.setPassword(encoder.encode(dto.getPassword()));
        userEntity.setRole(new RoleEntity(RoleType.CLIENT));

        userRepository.save(userEntity);
    }

    @Override
    public UserDto getUserById(Long id) {

        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> {
            throw new GeneralApiServerException("Пользователь не найден!", HttpStatus.NOT_FOUND);
        });

        return mapper.map(userEntity, UserDto.class);
    }

    @Override
    public Page<UserDto> getAllUsersByPagination(Pageable pageable, UserSearchDto userSearchDto) {

        String searchField = userSearchDto.getSearchField();
        Boolean isActive = userSearchDto.getIsActive();

        Page<UserEntity> list = userRepository.findByParams(searchField, isActive, pageable);
        return list.map(v -> mapper.map(v, UserDto.class));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {

        userRepository.findById(userDto.getId()).orElseThrow(() -> {
            throw new GeneralApiServerException("Пользователь не найден!", HttpStatus.NOT_FOUND);
        });

        return mapper.map(userRepository.save(mapper.map(userDto, UserEntity.class)), UserDto.class);

    }

    @Override
    public void deleteUser(Long id) {

        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> {
            throw new GeneralApiServerException("Пользователь не найден!", HttpStatus.NOT_FOUND);
        });

        userRepository.delete(userEntity);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(username).orElseThrow(() -> {
            throw new GeneralApiServerException("Пользователь не найден!", HttpStatus.NOT_FOUND);
        });

        UserDto userDto = mapper.map(userEntity, UserDto.class);

        userDto.setRole(mapper.map(userEntity.getRole(), RoleDto.class));

        return userDto;
    }
}



