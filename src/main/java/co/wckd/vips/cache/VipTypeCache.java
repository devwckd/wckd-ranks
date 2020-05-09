package co.wckd.vips.cache;

import co.wckd.boilerplate.object.DAO;
import co.wckd.vips.entity.VipType;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class VipTypeCache implements DAO<String, VipType> {

    private final Map<String, VipType> vipTypes;

    public VipTypeCache() {
        this.vipTypes = new ConcurrentHashMap<>();
    }

    @Override
    public VipType find(String key) {
        return vipTypes.get(key);
    }

    @Override
    public void insert(String key, VipType vipType) {
        vipTypes.put(key, vipType);
    }

    @Override
    public void delete(String key) {
        vipTypes.remove(key);
    }

}
