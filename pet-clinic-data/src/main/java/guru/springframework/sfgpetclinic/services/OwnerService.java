package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.model.Owner;

import java.util.Set;

public interface OwnerService {
    Owner findbyLastName(String lastname);
   Owner findById(Long id);
   Owner save(Owner owner);
   Set<Owner> findAll();
}
