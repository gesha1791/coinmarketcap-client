package net.chaplinskiy.coinmarketcapclient.error;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class ErrorDetail {
    private String title;
    private int status;
    private String detail;
    private LocalDateTime timeStamp;
    private String developerMessage;
    private Map<String, List<ValidationError>> errors
            = new HashMap<String, List<ValidationError>>();
}
