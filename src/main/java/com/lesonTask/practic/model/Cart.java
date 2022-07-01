package com.lesonTask.practic.model;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_generator")
    private Long id;
    @NotBlank(message = "Cart owner Name is necessary")
    private String ownerName;
    @NotBlank(message = "Owner email is necessary")
    private String ownerEmail;

    @OneToMany(mappedBy = "cart")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Product> products;

}
