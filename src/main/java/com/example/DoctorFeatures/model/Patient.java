package com.example.DoctorFeatures.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int patientId;

    @NotEmpty
    @Size(min = 3, message = "Name must be minimum of 3 characters")
    private String name;

    @NotEmpty
    @Size(max = 20,message = "City name should be max of 20 characters")
    private String city;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$", message = "Email address is not valid")
    private String email;

    @NotEmpty
    @Pattern(regexp = "(0|91)?[6-9][0-9]{9}", message = "Phone number not valid")
    private String phoneNumber;

    @NotEmpty
    private String symptom;
}
