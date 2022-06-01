package vn.ptit.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import vn.ptit.dtos.NotificationRequestDto;
import vn.ptit.dtos.SubscriptionRequestDto;
import vn.ptit.entities.Token;
import vn.ptit.repositories.TokenRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService{
    @Value("${app.firebase-config}")
    private String firebaseConfig;
    private FirebaseApp firebaseApp;
    @Autowired
    TokenRepository tokenRepository;

    @PostConstruct
    private void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfig).getInputStream())).build();

            if (FirebaseApp.getApps().isEmpty()) {
                this.firebaseApp = FirebaseApp.initializeApp(options);
            } else {
                this.firebaseApp = FirebaseApp.getInstance();
            }
        } catch (IOException e) {
            log.error("Create FirebaseApp Error", e);
        }
    }

    @Override
    public void subscribeToTopic(SubscriptionRequestDto subscriptionRequestDto) {
        try {
            FirebaseMessaging.getInstance(firebaseApp).subscribeToTopic(Arrays.asList(subscriptionRequestDto.getTokens()),
                    subscriptionRequestDto.getTopic());
        } catch (FirebaseMessagingException e) {
            log.error("Firebase subscribe to topic fail", e);
        }

    }

    @Override
    public void unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto) {
        try {
            FirebaseMessaging.getInstance(firebaseApp).unsubscribeFromTopic(Arrays.asList(subscriptionRequestDto.getTokens()),
                    subscriptionRequestDto.getTopic());
        } catch (FirebaseMessagingException e) {
            log.error("Firebase unsubscribe from topic fail", e);
        }

    }

    @Override
    public String sendMessageToToken(NotificationRequestDto notificationRequestDto) {
        Message message = Message.builder()
                .setToken(notificationRequestDto.getToken())
                .setNotification(
                        Notification.builder()
                                .setTitle(notificationRequestDto.getTitle())
                                .setBody(notificationRequestDto.getMessage())
                                .build()
                )
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            log.error("Fail to send firebase notification", e);
        }

        return response;
    }

    @Override
    public String sendMessageToTopic(NotificationRequestDto notificationRequestDto) {
        Message message = Message.builder()
                .setTopic(notificationRequestDto.getTopic())
                .setNotification(
                        Notification.builder()
                                .setTitle(notificationRequestDto.getTitle())
                                .setBody(notificationRequestDto.getMessage())
                                .build()
                )
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            log.error("Fail to send firebase notification", e);
        }

        return response;
    }

    @Override
    public BatchResponse sendMessageToListTokenRegister(NotificationRequestDto notificationRequestDto) {
        List<Token> tokenObjs = tokenRepository.findAll();
        List<String> tokens = new ArrayList<>();
        tokenObjs.forEach(t -> tokens.add(t.getValue()));

        MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(tokens)
                .setNotification(
                        Notification.builder()
                                .setTitle(notificationRequestDto.getTitle())
                                .setBody(notificationRequestDto.getMessage())
                                .build()
                )
                .build();

        BatchResponse response = null;
        try {
            response = FirebaseMessaging.getInstance().sendMulticast(message);
        } catch (FirebaseMessagingException e) {
            log.error("Fail to send firebase notification", e);
        }

        return response;
    }

    @Override
    public String sendTopicWithCondition(NotificationRequestDto notificationRequestDto) {
        Message message = Message.builder()
                .setTopic(notificationRequestDto.getTopic())
                .setNotification(
                        Notification.builder()
                                .setTitle(notificationRequestDto.getTitle())
                                .setBody(notificationRequestDto.getMessage())
                                .build()
                )
                .setCondition(notificationRequestDto.getCondition())
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            log.error("Fail to send firebase notification", e);
        }

        return response;
    }

    @Override
    public String nonNotification(NotificationRequestDto notificationRequestDto) {
        Message message = Message.builder()
                .setToken(notificationRequestDto.getToken())
                .setNotification(
                        Notification.builder()
                                .setTitle(notificationRequestDto.getTitle())
                                .setBody(notificationRequestDto.getMessage())
                                .build()
                )
                .putData("score","990")
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            log.error("Fail to send firebase notification", e);
        }

        return response;
    }

    @Override
    public Token insertToken(Token token) {
        Token res = tokenRepository.save(token);
        return res;
    }

    @Override
    public List<Token> findAllToken() {
        List<Token> tokens = tokenRepository.findAll();
        return tokens;
    }
}
