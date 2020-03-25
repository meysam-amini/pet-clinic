package guru.springframework.sfgpetclinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "owners")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Owner extends Person {

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "owner")
    private Set<Pet> pets=new HashSet<>();

    @NotBlank(message = "{owner.address.not.blank}")
    @Column(name = "address")
    private String address;

    @NotNull
    @Pattern(regexp="(^$|[0-9]{11})",message = "{telephone.not.valid}")
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "city")
    private String city;

    /**
     * Return the Pet with the given name, or null if none found for this Owner.
     * @param name to test
     * @return true if pet name is already in use
     */
    public Pet getPet(String name) {
        return getPet(name, false);
    }

    /**
     * Return the Pet with the given name, or null if none found for this Owner.
     * @param name to test
     * @return true if pet name is already in use
     */
    public Pet getPet(String name, boolean ignoreNew) {
        name = name.toLowerCase();
        for (Pet pet : pets) {
            if (!ignoreNew || !pet.isNew()) {
                String compName = pet.getName();
                compName = compName.toLowerCase();
                if (compName.equals(name)) {
                    return pet;
                }
            }
        }
        return null;
    }




}
