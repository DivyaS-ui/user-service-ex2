package com.conditionmanagementsystem.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "users_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Column (name = "last_name", nullable = false, length = 50)
    private String lastName;
    @Column (name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column (name = "patient")
    private Diabetes patient;

    @Column (name = "diagonized_date",nullable = false, length = 50)
    private Date diagonizedDate;

    @ElementCollection // 1
    @CollectionTable(name = "my_list", joinColumns = @JoinColumn(name = "id")) // 2
    @Enumerated(EnumType.STRING)
    @Column(name = "DiabetesType") // 3
    private List<DiabetesTypes> diabetesType;

    @OneToOne(mappedBy = "userDetails")
    @JsonIgnore
    private User user;



}
