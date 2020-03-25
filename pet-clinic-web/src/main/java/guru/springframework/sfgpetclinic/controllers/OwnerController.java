package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.exceptions.NotFoundException;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Set;

@RequestMapping("/owners")
@Controller
@Slf4j
public class OwnerController {

    private final OwnerService ownerService;

    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM="owners/createOrUpdateOwnerForm";

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

//    @RequestMapping({"","/","/index","/index.html"})
//    public String listOwners(Model model){
//
//        model.addAttribute("owners",ownerService.findAll());
//        return "owners/index";
//    }

    @InitBinder
    public void setNotAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping("/find")
    public String findOwners(Model model){
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result,Model model){

        if(owner.getLastName()==null){
            owner.setLastName("");
        }

        //find owners by last name:
        Set<Owner> results= ownerService.findAllByLastNameLike("%"+owner.getLastName()+"%");
        if(results.isEmpty()){
            //no owners found
            result.rejectValue("","","not found");
            return "owners/findOwners";
        }
        else if (results.size()==1){
            //1 owner found
            owner=results.iterator().next();
            return "redirect:/owners/"+owner.getId();
        }
        else{
            model.addAttribute("selections",results);
            return "owners/ownersList";
        }

    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId){

        ModelAndView mav=new ModelAndView("owners/ownerDetails");
        mav.addObject(ownerService.findById(ownerId));
        return mav;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model){
        model.addAttribute("owner",Owner.builder().build());
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Owner owner,BindingResult result){
        if(result.hasErrors()){
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        }else{
            Owner savedOwner=ownerService.save(owner);
            return "redirect:/owners/"+savedOwner.getId();
        }
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable Long ownerId,Model model){
        model.addAttribute(ownerService.findById(ownerId));
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }


    @PostMapping("/{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid Owner owner,BindingResult result
            ,@PathVariable Long ownerId){
        if(result.hasErrors()){
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        }else {
            owner.setId(ownerId);///chera? chera aslan variable id darim to vorudie inja??
            Owner savedOwner=ownerService.save(owner);
            return "redirect:/owners/"+savedOwner.getId();

        }
    }


    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception e){
        log.error("Handling Not Found Exception");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception",e);
        return modelAndView;
    }



}
