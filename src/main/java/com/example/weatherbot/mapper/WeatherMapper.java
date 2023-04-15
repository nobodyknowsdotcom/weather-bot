package com.example.weatherbot.mapper;

import com.example.weatherbot.enums.Emoji;
import com.example.weatherbot.service.weatherservice.ForecastInfo;
import com.example.weatherbot.service.weatherservice.WeatherInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherMapper {
    public static String forecastInfoToMessage(ForecastInfo forecastInfo, Integer step, Integer days){
        StringBuilder sb = new StringBuilder();
        int firstTimestamp = forecastInfo.getForecast().get(0).getDt();

        forecastInfo.getForecast()
                .subList(0, days)
                .stream()
                .filter(x -> (firstTimestamp - x.getDt()) % step == 0)
                .toList()
                .forEach(x -> sb.append(parseWeatherInfoFromForecast(x)));

        return sb.toString();
    }

    public static String weatherInfoToMessage(WeatherInfo weatherInfo){
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s %s %s",
                getWeatherConditionEmoji(weatherInfo.getConditionId()), weatherInfo.getConditionDescription(), "\n"));

        sb.append(String.format("%s температура %s, ощущается как %s %s",
                Emoji.TEMPERATURE.getText(), weatherInfo.getTemperature(), weatherInfo.getFeltTemperature(), "\n"));

        sb.append(String.format("%s влажность %s%% %s",
                Emoji.WATER.getText(), weatherInfo.getHumidity(),"\n"));

        sb.append(String.format("%s облачность %s%% %s",
                Emoji.CLOUDS.getText(), weatherInfo.getCloudiness(),"\n"));

        sb.append(String.format("%s скорость ветра %s%% %s",
                Emoji.WIND.getText(), weatherInfo.getWindSpeed(), "\n"));

        sb.append(String.format("%s восход %s %s",
                Emoji.SUNSET.getText(), unixTimeToDate("HH:mm:ss", weatherInfo.getSunriseTimestamp()), "\n"));
        sb.append(String.format("%s закат %s %s",
                Emoji.SUNRISE.getText(), unixTimeToDate("HH:mm:ss", weatherInfo.getSunsetTimestamp()), "\n"));

        return sb.toString();
    }

    private static String parseWeatherInfoFromForecast(WeatherInfo weatherInfo){
        StringBuilder sb = new StringBuilder();

        sb.append(unixTimeToDate("dd/MM/yyyy HH:mm", weatherInfo.getDt()));

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

    private static String unixTimeToDate(String pattern, Integer unixTime){
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date date = new Date((long)unixTime*1000);
        return formatter.format(date);
    }
}
