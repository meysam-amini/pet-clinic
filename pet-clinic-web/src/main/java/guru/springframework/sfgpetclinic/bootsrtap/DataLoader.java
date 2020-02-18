package guru.springframework.sfgpetclinic.bootsrtap;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }


    @Override
    public void run(String... args) throws Exception {

        PetType dog=new PetType();
        dog.setName("Dog");
        PetType SavedDogPetType=petTypeService.save(dog);

        PetType cat=new PetType();
        cat.setName("Cat");
        PetType SavedCatPetType=petTypeService.save(cat);


        Owner owner1=new Owner();
        owner1.setFirstName("Jake");
        owner1.setLastName("Wilson");
        owner1.setAddress("Haji Sadeghi_mohaghegh amin_96");
        owner1.setTelephone("0212222222");
        owner1.setCity("Tehran");

        Pet owner_1_pet=new Pet();
        owner_1_pet.setOwner(owner1);
        owner_1_pet.setPetType(SavedDogPetType);
        owner_1_pet.setBirthDate(LocalDate.now());
        owner_1_pet.setName("Zombeh");
        owner1.getPets().add(owner_1_pet);

        ownerService.save(owner1);

        Owner owner2=new Owner();
        owner2.setFirstName("Martin");
        owner2.setLastName("Wayn");
        owner2.setAddress("Haji Sadeghi_mohaghegh amin_96");
        owner2.setTelephone("0212222222");
        owner2.setCity("Tehran");

        Pet owner_2_pet=new Pet();
        owner_2_pet.setOwner(owner2);
        owner_2_pet.setPetType(SavedCatPetType);
        owner_2_pet.setBirthDate(LocalDate.now());
        owner_2_pet.setName("Garfild");
        owner2.getPets().add(owner_2_pet);

        ownerService.save(owner2);


        System.out.println("Owners Loaded...");

        Vet vet1=new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Liami");
        vetService.save(vet1);

        Vet vet2=new Vet();
        vet2.setFirstName("Lisa");
        vet2.setLastName("Karter");
        vetService.save(vet2);
        System.out.println("Vets Loaded...");
    }
}
