package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OwnerServiceMapTest {

    OwnerServiceMap ownerServiceMap;

    @BeforeEach
    void setUp() {
        ownerServiceMap=new OwnerServiceMap(new PetTypeServiceMap()
                ,new PetServiceMap());
        Owner o=new Owner();
        o.setId(3L);
        ownerServiceMap.save(o);
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet=ownerServiceMap.findAll();
        assertEquals(1,ownerSet.size());
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(3L);
        assertEquals(0,ownerServiceMap.findAll().size());
    }

    @Test
    void delete() {
    }

    @Test
    void findbyLastName() {
    }
}