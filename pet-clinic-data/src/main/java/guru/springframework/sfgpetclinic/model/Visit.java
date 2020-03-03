package guru.springframework.sfgpetclinic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "visits")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Visit extends BaseEntity{

    @Column(name ="date")
    private LocalDate date;

    @Column(name = "description")
    private String description;


    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

}
