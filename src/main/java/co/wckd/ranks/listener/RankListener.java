package co.wckd.ranks.listener;

import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.entity.rank.RankType;
import co.wckd.ranks.event.PlayerChangeRankEvent;
import co.wckd.ranks.event.PlayerGainRankTimeEvent;
import co.wckd.ranks.event.PlayerLoseRankTimeEvent;
import co.wckd.ranks.util.Lang;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class RankListener implements Listener {

    private final RanksPlugin plugin;
    private final Lang lang;

    public RankListener(RanksPlugin plugin) {
        this.plugin = plugin;
        this.lang = plugin.getFileLifecycle().getLang();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onGainRankTime(PlayerGainRankTimeEvent event) {

        if (event.isCancelled()) return;
        if (event.getSource() == PlayerGainRankTimeEvent.Source.COMMAND) {

            long time = event.getTime();
            String messageToSend = time == -1 ? "give_lifetime_rank" : "give_rank";
            String senderName = event.getExecutor() instanceof Player ? event.getExecutor().getName() : "CONSOLE";

            Player player = event.getPlayer();
            RankType type = event.getType();

            lang.send(player, messageToSend,
                    Pair.of("{player}", senderName),
                    Pair.of("{sender}", player.getName()),
                    Pair.of("{pretty_name}", type.getPrettyName()),
                    Pair.of("{identifier}", type.getIdentifier()),
                    Pair.of("{time}", lang.formatTime(time)));

        } else {

            long time = event.getTime();
            String messageToSend = time == -1 ? "usekey_success_lifetime" : "usekey_success";

            Player player = event.getPlayer();
            RankType type = event.getType();

            lang.send(player, messageToSend,
                    Pair.of("{sender}", player.getName()),
                    Pair.of("{pretty_name}", type.getPrettyName()),
                    Pair.of("{identifier}", type.getIdentifier()),
                    Pair.of("{time}", lang.formatTime(time)));

        }
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onLoseRankTime(PlayerLoseRankTimeEvent event) {

        if (event.isCancelled()) return;
        if (event.getSource() == PlayerLoseRankTimeEvent.Source.COMMAND) {

            boolean isLifetime = event.isExpired();
            String messageToSend = isLifetime ? "remove_lifetime_rank" : "remove_rank";
            String senderName = event.getExecutor() instanceof Player ? event.getExecutor().getName() : "CONSOLE";

            Player player = event.getPlayer();
            RankType type = event.getType();
            lang.send(player, messageToSend,
                    Pair.of("{player}", senderName),
                    Pair.of("{sender}", player.getName()),
                    Pair.of("{pretty_name}", type.getPrettyName()),
                    Pair.of("{identifier}", type.getIdentifier()),
                    Pair.of("{time}", lang.formatTime(event.getTimeLost())));

        } else {

            Player player = event.getPlayer();
            RankType type = event.getType();
            lang.send(player, "rank_expired",
                    Pair.of("{sender}", player.getName()),
                    Pair.of("{pretty_name}", type.getPrettyName()),
                    Pair.of("{identifier}", type.getIdentifier()),
                    Pair.of("{time}", lang.formatTime(event.getTimeLost())));

        }

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChangeRank(PlayerChangeRankEvent event) {

        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        if (event.getExecutor().equals(player)) {

            lang.send(player, "changerank_changed",
                    Pair.of("{previous_pretty_name}", event.getFrom().getType().getPrettyName()),
                    Pair.of("{previous_identifier}", event.getFrom().getType().getIdentifier()),
                    Pair.of("{pretty_name}", event.getTo().getType().getPrettyName()),
                    Pair.of("{identifier}", event.getTo().getType().getIdentifier()));

        }

    }

}
