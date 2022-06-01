package vn.ptit.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequestDto {
    private String topic;
    private String token;
    private String[] tokens;
    private String title;
    private String message;
    private String condition;
}
