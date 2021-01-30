package be.alexandre01.inazuma.uhc.timers.utils;

import be.alexandre01.inazuma.uhc.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateBuilderTimer {
    private long time;
    private Date date;
    private String build;
    Format h;
    Format m;
    Format s;
    public DateBuilderTimer(long time){
        this.time = time+new Date().getTime();
         h = new SimpleDateFormat("hh");
         m = new SimpleDateFormat("mm");
         s = new SimpleDateFormat("ss");
         loadDate();
    }

    public void loadDate(){
            long now = new Date().getTime();

            Date date = new Date(time-now);
            int hour =  (int) ((date.getTime() / (1000*60*60)) % 24);
            String minute = m.format(date);
            String second = s.format(date);
            StringBuilder sb = new StringBuilder();
            if(hour > 0){
                sb.append("0"+hour+":");
            }
            sb.append(minute+":");
            sb.append(second);
            this.date = date;
            this.build = sb.toString();
        }

    public Date getDate() {
        return date;
    }

    public String getBuild() {
        return build;
    }
}

