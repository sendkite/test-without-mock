package com.sendkite.teatapp.common.infrastructure;

import com.sendkite.teatapp.common.service.port.UuidHolder;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class SystemUuidHolder implements UuidHolder {

    @Override
    public String random() {
        return UUID.randomUUID().toString();
    }
}
