package com.example.weatherbot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Emoji {
    CITY ("🏙️"),
    CHECKED("✅"),
    UNCHECKED("\uD83D\uDEAB");

    private final String text;
}
