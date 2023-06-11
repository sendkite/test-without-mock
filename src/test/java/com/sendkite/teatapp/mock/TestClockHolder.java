package com.sendkite.teatapp.mock;

import com.sendkite.teatapp.common.service.port.ClockHolder;

public class TestClockHolder implements ClockHolder {

    private final long millis;

    public TestClockHolder(long millis) {
        this.millis = millis;
    }

    @Override
    public long millis() {
        return millis;
    }
}
