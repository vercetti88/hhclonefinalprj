package kz.m46.resume.services;

import kz.m46.resume.models.AbstractDto;

public interface NotificationService {

    void sendCode(String email);

    void sendMessage(String email, String message);

    Boolean verifyCode(String email, String code);

    void createAndSendMessage(AbstractDto dto);
}
