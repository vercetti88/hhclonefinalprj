package kz.m46.resume.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kz.m46.resume.models.JwtTokenDto;
import kz.m46.resume.models.LoginDto;
import kz.m46.resume.models.UserDto;
import kz.m46.resume.models.UserSearchDto;
import kz.m46.resume.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    @Operation(summary = "Регистрация пользователя")
    public ResponseEntity<Void> registration(@RequestBody UserDto userDto) {
        userService.registration(userDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @Operation(summary = "Авторизация пользователя")
    public ResponseEntity<JwtTokenDto> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(userService.login(loginDto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CLIENT', 'ADMIN')")
    @Operation(summary = "Получение пользователя по id")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('CLIENT', 'ADMIN')")
    @Operation(summary = "Получение всех пользователей")
    public ResponseEntity<Page<UserDto>> getAllUsersByPagination(Pageable pageable, @RequestBody UserSearchDto userSearchDto) {
        return ResponseEntity.ok(userService.getAllUsersByPagination(pageable, userSearchDto));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('CLIENT', 'ADMIN')")
    @Operation(summary = "Изменение пользователя")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CLIENT', 'ADMIN')")
    @Operation(summary = "Удаление пользователя")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}
