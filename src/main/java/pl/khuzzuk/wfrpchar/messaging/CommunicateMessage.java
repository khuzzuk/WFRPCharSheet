package pl.khuzzuk.wfrpchar.messaging;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class CommunicateMessage implements Message {
    @Getter
    @Setter
    @NonNull
    private String type;
}
