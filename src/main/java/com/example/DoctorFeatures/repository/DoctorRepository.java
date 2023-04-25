package com.example.DoctorFeatures.repository;

import com.example.DoctorFeatures.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,Integer> {

    List<Doctor> findByEmail(String email);

    List<Doctor> findByPhoneNumber(String phoneNumber);

    @Query(value = "Select * from tbl_doctor where doctor_city=:city",nativeQuery = true)
    public List<Doctor> findByDoctorCity(String city);

    @Query(value = "Select * from tbl_doctor where speciality=:symptom and city=:city",nativeQuery = true)
    public List<Doctor> findByDoctorSpecializedInAndDoctorCity(String city,String symptom);

    List<Doctor> getDoctorsByDoctorId(Integer valueOf);

    List<Doctor> findByCityAndSpeciality(String pCity, String orthopedic);

}
