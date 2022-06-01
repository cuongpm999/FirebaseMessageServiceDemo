package vn.ptit.services;

import com.google.firebase.messaging.BatchResponse;
import vn.ptit.dtos.NotificationRequestDto;
import vn.ptit.dtos.SubscriptionRequestDto;
import vn.ptit.entities.Token;

import java.util.List;

public interface NotificationService {
    public void subscribeToTopic(SubscriptionRequestDto subscriptionRequestDto);
    public void unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto);
    public String sendMessageToToken(NotificationRequestDto notificationRequestDto);
    public String sendMessageToTopic(NotificationRequestDto notificationRequestDto);
    public BatchResponse sendMessageToListTokenRegister(NotificationRequestDto notificationRequestDto);
    public String sendTopicWithCondition(NotificationRequestDto notificationRequestDto);
    public String nonNotification(NotificationRequestDto notificationRequestDto);
    public Token insertToken(Token token);
    public List<Token> findAllToken();
}
