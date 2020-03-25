package guru.springframework.sfgpetclinic.bootsrtap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialtyService specialtyService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
        this.visitService = visitService;
    }


    @Override
    public void run(String... args) throws Exception {

        int count=petTypeService.findAll().size();
        if(count==0)
        loadData();
    }

    private void loadData() {
        PetType dog=new PetType();
        dog.setName("Dog");
        PetType SavedDogPetType=petTypeService.save(dog);

        PetType cat=new PetType();
        cat.setName("Cat");
        PetType SavedCatPetType=petTypeService.save(cat);


        Specialty radiology=new Specialty();
        radiology.setDescription("radiology");
        Specialty saved_radiology=specialtyService.save(radiology);

        Specialty surgery=new Specialty();
        surgery.setDescription("surgery");
        Specialty saved_surgery=specialtyService.save(surgery);


        Specialty dentistery=new Specialty();
        dentistery.setDescription("dentistery");
        Specialty saved_dentistery=specialtyService.save(dentistery);


        Owner owner1=new Owner();
        owner1.setFirstName("Jake");
        owner1.setLastName("Wilson");
        owner1.setAddress("Haji Sadeghi_mohaghegh amin_96");
        owner1.setTelephone("09390353105");
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
        owner2.setTelephone("09390353105");
        owner2.setCity("Tehran");

        Pet owner_2_pet=new Pet();
        owner_2_pet.setOwner(owner2);
        owner_2_pet.setPetType(SavedCatPetType);
        owner_2_pet.setBirthDate(LocalDate.now());
        owner_2_pet.setName("Garfild");
        owner2.getPets().add(owner_2_pet);

        ownerService.save(owner2);

        Visit catvisit=new Visit();
        catvisit.setDate(LocalDate.now());
        catvisit.setPet(owner_2_pet);
        catvisit.setDescription("sneezy cat");
        visitService.save(catvisit);

        System.out.println("Owners Loaded...");

        Vet vet1=new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Liami");
        vet1.getSpecialties().add(saved_radiology);
        vetService.save(vet1);

        Vet vet2=new Vet();
        vet2.setFirstName("Lisa");
        vet2.setLastName("Karter");
        vet2.getSpecialties().add(saved_dentistery);
        vet2.getSpecialties().add(saved_surgery);
        vetService.save(vet2);
        System.out.println("Vets Loaded...");
    }
}
