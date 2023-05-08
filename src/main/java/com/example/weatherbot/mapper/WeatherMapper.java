package com.example.weatherbot.mapper;

import com.example.weatherbot.enums.Emoji;
import com.example.weatherbot.service.weatherservice.ForecastInfo;
import com.example.weatherbot.service.weatherservice.WeatherInfo;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class WeatherMapper {
    public static String forecastInfoToMessage(ForecastInfo forecastInfo, Integer step, Integer days){
        StringBuilder sb = new StringBuilder();
        long firstTimestamp = forecastInfo.getForecast().get(0).getDate();

        forecastInfo.getForecast()
                .subList(0, days)
                .stream()
                .filter(x -> (firstTimestamp - x.getDate()) % step == 0)
                .toList()
                .forEach(x -> sb.append(parseWeatherInfoFromForecast(x)));

        return sb.toString();
    }

    public static String weatherInfoToMessage(WeatherInfo weatherInfo){
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s %s: %s",
                Emoji.CALENDAR.getText(),
                unixTimeToDate("yyy/MM/dd HH:mm", System.currentTimeMillis() / 1000L, weatherInfo.getTimezone()),
                "\n"
        ));
        sb.append(String.format("%s %s %s",
                getWeatherConditionEmoji(weatherInfo.getConditionId()), weatherInfo.getConditionDescription(),
                "\n"
        ));
        sb.append(String.format("%s температура %.1f°C, ощущается как %.1f°C %s",
                Emoji.TEMPERATURE.getText(), weatherInfo.getTemperature(), weatherInfo.getFeltTemperature(),
                "\n"
        ));
        sb.append(String.format("%s минимум %.1f°C, максимум %.1f°C %s",
                Emoji.GRAPH.getText(), weatherInfo.getMinTemperature(), weatherInfo.getMaxTemperature(),
                "\n"
        ));
        sb.append(String.format("%s влажность %s%% %s",
                Emoji.WATER.getText(), weatherInfo.getHumidity(),
                "\n"
        ));
        sb.append(String.format("%s облачность %s%% %s",
                Emoji.CLOUDS.getText(), weatherInfo.getCloudiness(),
                "\n"
        ));
        sb.append(String.format("%s скорость ветра %sм/с %s",
                Emoji.WIND.getText(), weatherInfo.getWindSpeed(),
                "\n"
        ));
        sb.append(String.format("%s восход %s %s",
                Emoji.SUNSET.getText(),
                unixTimeToDate("HH:mm:ss", weatherInfo.getSunriseTimestamp(), weatherInfo.getTimezone()),
                "\n"
        ));
        sb.append(String.format("%s закат %s %s",
                Emoji.SUNRISE.getText(),
                unixTimeToDate("HH:mm:ss", weatherInfo.getSunsetTimestamp(), weatherInfo.getTimezone()),
                "\n"
        ));

        return sb.toString();
    }

    private static String parseWeatherInfoFromForecast(WeatherInfo weatherInfo){
        StringBuilder sb = new StringBuilder();

        sb.append(unixTimeToDate("dd/MM/yyyy HH:mm", weatherInfo.getDate(), weatherInfo.getTimezone()));

        sb.append(String.format("%s %s %s",
                getWeatherConditionEmoji(weatherInfo.getConditionId()), weatherInfo.getConditionDescription(), "\n"));

        sb.append(String.format("%s температура %s, ощущается как %s %s",
                Emoji.TEMPERATURE.getText(), weatherInfo.getTemperature(), weatherInfo.getFeltTemperature(), "\n"));

        sb.append("\n");

        return sb.toString();
    }

    private static String getWeatherConditionEmoji(Integer code){
        int prefix = code/100;

        switch (prefix){
            case 2 -> {
                return Emoji.THUNDERSTORM.getText();
            }
            case 3 -> {
                return Emoji.DRIZZLE.getText();
            }
            case 5-> {
                return Emoji.RAIN.getText();
            }
            case 6 -> {
                return Emoji.SNOW.getText();
            }
            case 7 -> {
                return Emoji.ATMOSPHERE.getText();
            }
            case 8 -> {
                if (code == 800){
                    return Emoji.CLEAR.getText();
                } else {
                    return Emoji.CLOUDS.getText();
                }
            }
            default -> {
                return Emoji.KAZAKHSTAN_FLAG.getText();
            }
        }
    }

    private static String unixTimeToDate(String pattern, Long unixTime, Integer timezoneOffset){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        OffsetDateTime offsetDateTime = Instant.ofEpochSecond(unixTime)
                .atOffset(ZoneOffset.ofTotalSeconds(timezoneOffset));
        LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();
        return localDateTime.format(formatter);
    }
}
