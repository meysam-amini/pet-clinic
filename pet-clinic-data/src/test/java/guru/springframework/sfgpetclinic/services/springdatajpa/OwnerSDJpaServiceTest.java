package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService service;

    Owner returned_owner;


    @BeforeEach
    void setUp() {

        returned_owner=Owner.builder().id(4L).lastName("corona").build();
    }

    @Test
    void findbyLastName() {
        Owner o=Owner.builder().id(3L).lastName("corona").build();
        when(ownerRepository.findByLastName(any())).thenReturn(o);
        Owner o2=service.findbyLastName("corona");
        assertEquals(o.getLastName(),"corona");
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong()))
                .thenReturn(Optional.of(returned_owner));
        Owner owner=service.findById(1L);
        assertNotNull(owner);
    }

    @Test
    void save() {
        Owner ownerToSave=Owner.builder().id(1L).build();
        when(ownerRepository.save(any())).thenReturn(returned_owner);

        Owner saved=service.save(ownerToSave);

        assertNotNull(saved);
        verify(ownerRepository,times(1)).save(any());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet=new HashSet<>();
        ownerSet.add(Owner.builder().id(6L).build());
        ownerSet.add(Owner.builder().id(66L).address("jjj").build());
        ownerSet.add(Owner.builder().id(666L).address("jjj5").build());


        when(ownerRepository.findAll()).thenReturn(ownerSet);
        Set<Owner> owners=service.findAll();

        assertNotNull(owners);
        assertEquals(3,owners.size());
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }
}