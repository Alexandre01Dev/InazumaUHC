package be.alexandre01.inazuma.uhc.scoreboard;


import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class ScoreboardManager {
    private final Map<UUID, PersonalScoreboard> scoreboards;
    public Object scoreboardInitializer = null;
    private final ScheduledFuture glowingTask;
    private final ScheduledFuture reloadingTask;
    private ChatColor fColor = ChatColor.AQUA;
    private ChatColor sColor = ChatColor.WHITE;
    private int ipCharIndex;
    private int cooldown;
    private boolean reverse = false;
    @Setter private IPreset preset = Preset.instance.p;
    private String ip = InazumaUHC.get.ip;
    public ScoreboardManager() {
        InazumaUHC inazumaUHC = InazumaUHC.get;
        scoreboards = new HashMap<>();
        ipCharIndex = 0;
        cooldown = 0;

        glowingTask = inazumaUHC.getScheduledExecutorService().scheduleAtFixedRate(() ->
        {
            String ip = colorIpAt();
            for (PersonalScoreboard scoreboard : scoreboards.values())
               inazumaUHC.getExecutorMonoThread().execute(() -> scoreboard.setLines(ip));
        }, 80, 80, TimeUnit.MILLISECONDS);

        reloadingTask = inazumaUHC.getScheduledExecutorService().scheduleAtFixedRate(() ->
        {
            for (PersonalScoreboard scoreboard : scoreboards.values())
                inazumaUHC.getExecutorMonoThread().execute(scoreboard::reloadData);
        }, 1, 1, TimeUnit.SECONDS);
    }

    public void onDisable() {
        scoreboards.values().forEach(PersonalScoreboard::onLogout);
    }

    public void onLogin(Player player) {
        if (scoreboards.containsKey(player.getUniqueId())) {
            return;
        }
        scoreboards.put(player.getUniqueId(), preset.getScoreboard(player));
    }

    public void onLogout(Player player) {
        if (scoreboards.containsKey(player.getUniqueId())) {
            scoreboards.get(player.getUniqueId()).onLogout();
            scoreboards.remove(player.getUniqueId());
        }
    }

    public void update(Player player) {
        if (scoreboards.containsKey(player.getUniqueId())) {
            scoreboards.get(player.getUniqueId()).reloadData();
        }
    }
    private String colorIpAt_dev(){
        if(cooldown > 0){
            cooldown--;
            return ChatColor.BLUE + ip;
        }
        StringBuilder formattedIp = new StringBuilder();
        int i = 1;
            if(ipCharIndex > 0) {
                formattedIp.append(ip.substring(0,ipCharIndex-1));
                formattedIp.append(ChatColor.AQUA+ip.substring(ipCharIndex-1,ipCharIndex));
                formattedIp.append(ChatColor.WHITE+ip.substring(ipCharIndex,ipCharIndex+1));
            }
            formattedIp.append(ChatColor.BLUE+ip.substring(ipCharIndex,ipCharIndex+1));


        ipCharIndex++;

        return ChatColor.BLUE + formattedIp.toString();
    }

    private String colorIpAt() {

        if (cooldown > 0) {
            cooldown--;
            return ChatColor.BLUE + ip;
        }

        StringBuilder formattedIp = new StringBuilder();
        if(ipCharIndex > 0 && reverse){
            formattedIp.append(ip.substring(0, ipCharIndex - 1));
            formattedIp.append(ChatColor.AQUA).append(ip.substring(ipCharIndex -1, ipCharIndex));
        }else {
            if (ipCharIndex > 0 && !reverse) {
                formattedIp.append(ip.substring(0, ipCharIndex - 1));
                formattedIp.append(ChatColor.AQUA).append(ip.substring(ipCharIndex - 1, ipCharIndex));
            } else {
                if(reverse){
                    formattedIp.append(ChatColor.AQUA+"p"+ChatColor.BLUE+ip.substring(1));
                }else {

                    formattedIp.append(ip.substring(0,ipCharIndex));
                }
                //System.out.println("ok");

            }
        }




        if (ipCharIndex + 1 < ip.length() && !reverse || ipCharIndex+1 > 1 && reverse) {
            formattedIp.append(fColor).append(ip.charAt(ipCharIndex));
            formattedIp.append(sColor).append(ip.charAt(ipCharIndex + 1));
            if (ipCharIndex + 2 < ip.length())
                formattedIp.append(ChatColor.BLUE).append(ip.substring(ipCharIndex + 2));
            if(!reverse){

                ipCharIndex++;
            }else {
                ipCharIndex--;
            }

        } else {
            if(!reverse){
                fColor = ChatColor.WHITE;
                sColor = ChatColor.AQUA;
                reverse = true;
                ipCharIndex--;
            }else {
                fColor = ChatColor.AQUA;
                sColor = ChatColor.WHITE;
                reverse = false;
                ipCharIndex = 0;
            }

            cooldown = 20;
        }
        return ChatColor.BLUE + formattedIp.toString();
    }

    public Map<UUID, PersonalScoreboard> getScoreboards() {
        return scoreboards;
    }

    public void refreshPlayers(Object o){
        this.scoreboardInitializer = o;
        for (UUID uuid : scoreboards.keySet()){
            Player player = Bukkit.getPlayer(uuid);
            scoreboards.get(player.getUniqueId()).onLogout();
            scoreboards.put(player.getUniqueId(), preset.getScoreboard(player));
        }
    }
}