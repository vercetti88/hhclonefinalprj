package kz.m46.resume.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.m46.resume.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
@PreAuthorize("hasAnyAuthority('CLIENT', 'ADMIN')")
public class NotificationController {

    final private NotificationService notificationService;

    @PostMapping("/code")
    @Operation(summary = "Отправка кода на email")
    public ResponseEntity<Void> sendCode(@RequestParam String email) {
        notificationService.sendCode(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/message")
    @Operation(summary = "Отправка сообщения на email")
    public ResponseEntity<Void> sendMessage(@RequestParam String email, @RequestParam String message) {
        notificationService.sendMessage(email, message);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verify")
    @Operation(summary = "Проверка кода")
    public ResponseEntity<Boolean> verifyCode(@RequestParam String email, @RequestParam String code) {
        return ResponseEntity.ok(notificationService.verifyCode(email, code));
    }
}
