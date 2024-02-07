package com.oomool.api.domain.notification.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ReadState {
    UNREAD,
    READ;

    @JsonValue
    public String jsonString() {
        return this.toString().toLowerCase();
    }
}
