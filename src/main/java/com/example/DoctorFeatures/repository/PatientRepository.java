package com.example.DoctorFeatures.repository;

import com.example.DoctorFeatures.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
    List<Patient> findByEmail(String email);

    List<Patient> findByPhoneNumber(String phoneNumber);
}
