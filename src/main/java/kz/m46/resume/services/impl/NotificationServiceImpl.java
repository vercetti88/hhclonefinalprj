package kz.m46.resume.services.impl;

import kz.m46.resume.models.AbstractDto;
import kz.m46.resume.models.UserDto;
import kz.m46.resume.models.ResumeDto;
import kz.m46.resume.models.VacancyDto;
import kz.m46.resume.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Value("${notification-service.url}")
    private String rootUrl;

    private final RestTemplate restTemplate;

    @Override
    public void sendCode(String email) {

        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();

        form.add("email", email);

        String url = rootUrl + "/email/send/code";

        log.info("url: {}", url);

        restTemplate.postForEntity(url, form, String.class);

    }

    @Override
    public void sendMessage(String email, String message) {

        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();

        form.add("email", email);
        form.add("message", message);

        String url = rootUrl + "/email/send/message";

        log.info("url: {}", url);

        restTemplate.postForEntity(url, form, String.class);

    }

    @Override
    public Boolean verifyCode(String email, String code) {

        String url = rootUrl + "/email/send/verify?email=" + email + "&code=" + code;

        log.info("url: {}", url);

        ResponseEntity<Boolean> responseEntity = restTemplate
                .getForEntity(url, Boolean.class);

        return responseEntity.getBody();
    }

    @Override
    public void createAndSendMessage(AbstractDto dto) {

        UserDto userDto = dto.getUser();

        StringBuilder message = new StringBuilder("Здравствуйте, " + userDto.getName() + "! ");

        if (dto instanceof ResumeDto) {
            message.append("Ваша заявка на размещение резюме ");
        } else if (dto instanceof VacancyDto) {
            message.append("Ваша заявка на размещение вакансии ");
        }

        switch (dto.getStatus()) {
            case ACTIVE -> {
                message.append(dto.getTitle()).append(" одобрена.");
                sendMessage(userDto.getEmail(), String.valueOf(message));
            }
            case REJECTED -> {
                message.append(dto.getTitle()).append(" отклонена.");
                sendMessage(userDto.getEmail(), String.valueOf(message));
            }
        }
    }
}
