package com.urban.oasis.urban.oasis_ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.urban.oasis.urban.oasis_ecommerce.domain.USER_ROLES;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String email;

    private String fullName;

    private String mobileNumber;

    private USER_ROLES role = USER_ROLES.ROLE_CUSTOMER;

    @OneToMany
    private Set<Address> address = new HashSet<>();

    @ManyToMany
    @JsonIgnore
    private Set<Coupon> usedCoupon = new HashSet<>();
}
