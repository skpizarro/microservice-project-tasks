package co.com.poli.pojecttask.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;



@Builder
@Data
public class ErrorMessage {
    private String code;
    private List<Map<String, String>> messages;

    public ErrorMessage(String code, List<Map<String, String>> messages) {
        this.code = code;
        this.messages = messages;
    }
}
