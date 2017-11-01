package pl.khuzzuk.wfrpchar.repo;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;

import java.util.HashMap;
import java.util.Map;

public class TypeIdResolver implements ObjectIdResolver {
    private Map<ObjectIdGenerator.IdKey, Object> types;

    public TypeIdResolver() {
        types = new HashMap<>();
    }

    @Override
    public void bindItem(ObjectIdGenerator.IdKey id, Object pojo) {
        types.put(id, pojo);
    }

    @Override
    public Object resolveId(ObjectIdGenerator.IdKey id) {
        return types.get(id);
    }

    @Override
    public ObjectIdResolver newForDeserialization(Object context) {
        return this;
    }

    @Override
    public boolean canUseFor(ObjectIdResolver resolverType) {
        return false;
    }
}
