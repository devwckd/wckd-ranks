package co.wckd.ranks.lifecycle;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.lifecycle.Lifecycle;
import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.cache.RankTypeCache;
import co.wckd.ranks.entity.rank.RankType;
import lombok.Getter;

import java.io.File;

@Getter
public class RankTypeLifecycle extends Lifecycle {

    private final RanksPlugin plugin;
    private final Adapter adapter;
    private final RankTypeCache rankTypeCache;

    private File vipTypeFolder;

    public RankTypeLifecycle(RanksPlugin plugin) {
        this.plugin = plugin;
        this.adapter = plugin.getAdapter();
        this.rankTypeCache = new RankTypeCache();
    }

    @Override
    public void enable() {

        vipTypeFolder = plugin.getFileLifecycle().getRankTypeFolder();

        // TODO: info
        loadRankTypes();

    }

    private void loadRankTypes() {

        File[] files = vipTypeFolder.listFiles();

        for (File file : files) {
            RankType type = adapter.adapt(file, File.class, RankType.class);
            if (type != null) rankTypeCache.insert(type.getIdentifier(), type);
        }

    }

}
