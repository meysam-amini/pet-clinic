package guru.springframework.sfgpetclinic.services.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMapService<T,ID> {

    protected Map<ID,T> map=new HashMap<>();

    Set<T> findAll(){
        return new HashSet(map.values());
    }

    T findById(ID id){
        return (T) map.get(id);
    }

    T save(T object,ID id){
        map.put(id,object);
        return object;
    }

    void deleteById(ID id){
        map.remove(id);
    }

    void delete(T object){
        map.entrySet().removeIf(tidEntry -> tidEntry.getValue().equals(object));
    }
}
