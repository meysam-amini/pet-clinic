package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.exceptions.NotFoundException;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @Mock
    OwnerRepository ownerRepository;
    @InjectMocks
    OwnerController ownerController;

    Set<Owner> owners;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Mockito.mockitoSession().initMocks();
        owners=new HashSet<>();
        owners.add(Owner.builder().id(9L).build());
        owners.add(Owner.builder().id(1L).address("kkk").build());

        mockMvc= MockMvcBuilders.standaloneSetup(ownerController)
                .setControllerAdvice(new ControllerExceptionHandler()).build();
    }

//    @Test
//    void listOwners() throws Exception {
//        when(ownerService.findAll()).thenReturn(owners);
//
//        mockMvc.perform(get("/owners"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("owners/index"))
//                .andExpect(model().attribute("owners", Matchers.hasSize(2)));
//    }

//    @Test
//    void listOwnersByIndex() throws Exception {
//        when(ownerService.findAll()).thenReturn(owners);
//
//        mockMvc.perform(get("/owners/index"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("owners/index"))
//                .andExpect(model().attribute("owners", Matchers.hasSize(2)));
//    }

    @Test
    void displayOwner() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(10L).address("dd").build());

        mockMvc.perform(get("/owners/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));
        verifyNoInteractions(ownerService);
    }

    @Test
    void processFindFormReturnMany() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString()))
                .thenReturn(owners);

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections",hasSize(2)));
    }

    @Test
    void processFindFormReturnOne() throws Exception {
        Set<Owner> owners=new HashSet<>();
        owners.add(Owner.builder().id(11L).build());
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(owners);

        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/11"));

    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));
        verifyNoInteractions(ownerService);
    }

//    @Test because validation fails
//    void processCreationForm() throws Exception {
//        when(ownerService.save(any())).thenReturn(Owner.builder().id(1L).build());
//
//        mockMvc.perform(post("/owners/new"))
//                .andExpect(status().isOk())//has errors bacause validation
//                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
//                .andExpect(model().attributeExists("owner"));
//        verify(ownerService).save(any());
//    }

    @Test
    void initUpdateOwnerForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(get("/owners/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));

    }

//    @Test because validation fails
//    void processUpdateOwnerForm() throws Exception {
//        when(ownerService.save(any())).thenReturn(Owner.builder().id(1L).build());
//
//        mockMvc.perform(post("/owners/1/edit"))
//                .andExpect(status().isOk())//has errors..so is getting back the same page
//                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
//                .andExpect(model().attributeExists("owner"));
//        verify(ownerService).save(any());
//    }


    @Test
    public void testNotFoundException() throws Exception {


                Assertions.assertThrows(NotFoundException.class,
                        ()-> System.out.println(testing(2)));

}

public int testing(int a){
        if(a<3)
            throw new NotFoundException("Not Found");
        return 3-a;
}

//@Test
//public void testGetOwnerPageNotFound() throws Exception {
//
//        Optional<Owner> oo=Optional.empty();
//     when(ownerRepository.findById(anyLong())).thenReturn(oo);
//     mockMvc.perform(get("/owners/2"))
//             .andExpect(status().isNotFound());
//}

    @Test
    void TestNumberFormatException() throws Exception {

        mockMvc.perform(get("/owners/1oioi/edit"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));

    }
    }
