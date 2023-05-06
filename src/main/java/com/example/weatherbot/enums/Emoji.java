package com.example.weatherbot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Emoji {
    CITY ("🏙️"),
    CHECKED("✅"),
    THUNDERSTORM("⛈⚡️"),
    DRIZZLE("\uD83D\uDCA7"),
    RAIN("\uD83C\uDF27"),
    SNOW("❄️⛄️"),
    ATMOSPHERE("\uD83D\uDE36\u200D\uD83C\uDF2B️"),
    CLEAR("☀️"),
    CLOUDS("⛅️"),
    KAZAKHSTAN_FLAG("\uD83C\uDDF0\uD83C\uDDFF"),
    TEMPERATURE("\uD83C\uDF21"),
    WATER("\uD83D\uDCA7"),
    WIND("\uD83D\uDCA8"),
    SUNSET("\uD83C\uDF05"),
    SUNRISE("\uD83C\uDF04"),
    CALENDAR("\uD83D\uDCC5"),
    GRAPH("\uD83D\uDCCA"),
    TONGUE("\uD83D\uDC45"),
    UNCHECKED("\uD83D\uDEAB");

    private final String text;
}
