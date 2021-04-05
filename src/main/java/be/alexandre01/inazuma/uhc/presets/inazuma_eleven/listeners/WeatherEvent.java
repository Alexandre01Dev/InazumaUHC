package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherEvent implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {

        boolean rain = e.toWeatherState();
        if(rain)
            e.setCancelled(true);
    }

    @EventHandler
    public void onThunderChange(ThunderChangeEvent event) {

        boolean storm = event.toThunderState();
        if(storm)
            event.setCancelled(true);
    }


}
