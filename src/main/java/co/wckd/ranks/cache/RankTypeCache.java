package co.wckd.ranks.cache;

import co.wckd.boilerplate.object.DAO;
import co.wckd.ranks.entity.rank.RankType;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class RankTypeCache implements DAO<String, RankType> {

    private final Map<String, RankType> vipTypes;

    public RankTypeCache() {
        this.vipTypes = new ConcurrentHashMap<>();
    }

    @Override
    public RankType find(String key) {
        return vipTypes.get(key);
    }

    @Override
    public void insert(String key, RankType rankType) {
        vipTypes.put(key, rankType);
    }

    @Override
    public void delete(String key) {
        vipTypes.remove(key);
    }

}
