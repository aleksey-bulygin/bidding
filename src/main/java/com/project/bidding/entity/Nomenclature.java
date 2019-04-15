package com.project.bidding.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nomenclature implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String mmn;

    private String name;

    private String manufacture;

    private String vital;

    private Double cost_vital;

    private Double quantity;

    private Double cost;

    private Double min_cost;

   // @Temporal(TemporalType.DATE)
    //@DateTimeFormat(pattern = "DD.MM.yyyy")
    private String expiration;

    private Double prorogation;

    //@ManyToMany
    //@JoinColumn(name = "user_id")
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER)
    //private Set<User> authors;
    private Map<User, PlayCosts> tenders;



   // @ElementCollection(targetClass = User.class, fetch = FetchType.EAGER)
   // @CollectionTable(name = "authors", joinColumns = @JoinColumn(name = "user_id"))
    //@Enumerated(EnumType.STRING)
   // private Set<User> authors;

}
