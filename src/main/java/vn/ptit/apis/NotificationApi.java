package vn.ptit.apis;

import com.google.firebase.messaging.BatchResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.dtos.NotificationRequestDto;
import vn.ptit.dtos.SubscriptionRequestDto;
import vn.ptit.entities.Token;
import vn.ptit.services.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationApi {

    private NotificationService notificationService;

    public NotificationApi(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/subscribe")
    public void subscribeToTopic(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        notificationService.subscribeToTopic(subscriptionRequestDto);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto) {
        notificationService.unsubscribeFromTopic(subscriptionRequestDto);
    }

    @PostMapping(path = "/token", produces = "application/json")
    public String sendMessageToToken(@RequestBody NotificationRequestDto notificationRequestDto) {
        return notificationService.sendMessageToToken(notificationRequestDto);
    }

    @PostMapping("/topic")
    public String sendMessageToTopic(@RequestBody NotificationRequestDto notificationRequestDto) {
        return notificationService.sendMessageToTopic(notificationRequestDto);
    }

    @PostMapping("/tokens")
    public BatchResponse sendMessageToListDevice(@RequestBody NotificationRequestDto notificationRequestDto) {
        return notificationService.sendMessageToListTokenRegister(notificationRequestDto);
    }

    @PostMapping("/topic/condition")
    public String sendTopicWithCondition(@RequestBody NotificationRequestDto notificationRequestDto) {
        return notificationService.sendTopicWithCondition(notificationRequestDto);
    }

    @PostMapping("/token/non-notification")
    public String sendNonNotification(@RequestBody NotificationRequestDto notificationRequestDto) {
        return notificationService.nonNotification(notificationRequestDto);
    }

    @PostMapping(path = "/insert-token", produces = "application/json")
    public ResponseEntity<Token> insertToken(@RequestBody Token token) {
        Token res = notificationService.insertToken(token);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(path = "/list-token", produces = "application/json")
    public ResponseEntity<List<Token>> listToken(@RequestBody Token token) {
        List<Token> res = notificationService.findAllToken();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
