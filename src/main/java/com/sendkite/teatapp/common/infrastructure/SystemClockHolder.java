package com.sendkite.teatapp.common.infrastructure;

import com.sendkite.teatapp.common.service.port.ClockHolder;
import java.time.Clock;
import org.springframework.stereotype.Component;

@Component
public class SystemClockHolder implements ClockHolder {

    @Override
    public long millis() {
        return Clock.systemUTC().millis();
    }
}
