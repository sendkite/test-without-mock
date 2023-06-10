package com.sendkite.teatapp.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdateDto {

    private final String nickname;
    private final String address;

    @Builder
    public UserUpdateDto(
            @JsonProperty("nickname") String email,
            @JsonProperty("address") String address) {
        this.nickname = email;
        this.address = address;
    }
}
