package com.example.DoctorFeatures.controller;

import com.example.DoctorFeatures.model.Doctor;
import com.example.DoctorFeatures.repository.DoctorRepository;
import com.example.DoctorFeatures.service.DoctorService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @Autowired
    DoctorRepository doctorRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addDoctor(@RequestBody Doctor doctor) {

        List<ResponseEntity<String>> validateUser = validateUserRequest(doctor);

        Doctor newDoctor;
        if (validateUser.isEmpty()) {
            newDoctor = this.doctorService.createDoctor(doctor);
        } else {
            return new ResponseEntity<String>(validateUser.toString(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(newDoctor.toString(), HttpStatus.CREATED);
    }

    private List<ResponseEntity<String>> validateUserRequest(Doctor doctorData){

        List<ResponseEntity<String>> errorList = new ArrayList<>();

        String city = doctorData.getCity();
        if(!(city.equals("delhi")) && !(city.equals("faridabad")) && !(city.equals("noida"))){
            errorList.add(new ResponseEntity<>("Doctor must be from from delhi, faridabad or noida.", HttpStatus.BAD_REQUEST));
        }

        String speciality = doctorData.getSpeciality();
        if(!(speciality.equals("Orthopedic")) && !(speciality.equals("Dermatologist")) && !(speciality.equals("Gynecologist")) && !(speciality.equals("ENT"))) {
            errorList.add(new ResponseEntity<>("Doctor must have speciality in Orthopedic, Gynecology, Dermatology, ENT.", HttpStatus.BAD_REQUEST));
        }

        String email = doctorData.getEmail();
        if(!doctorRepository.findByEmail(email).isEmpty()){
            errorList.add(new ResponseEntity<>("Email already exists!!!", HttpStatus.BAD_REQUEST));
        }

        String phoneNumber = doctorData.getPhoneNumber();
        if(!doctorRepository.findByPhoneNumber(phoneNumber).isEmpty()){
            errorList.add(new ResponseEntity<>("Phone Number already exists!!!", HttpStatus.BAD_REQUEST));
        }
        return errorList;
    }

    @GetMapping(value = "/get-list")
    public ResponseEntity<String> getDoctors(@Nullable @RequestParam String doctorId) {

        JSONArray doctorArr = doctorService.getDoctors(doctorId);
        return new ResponseEntity<>(doctorArr.toString(), HttpStatus.OK);

    }

    @DeleteMapping("/delete-by-Id")
    public ResponseEntity<String> deleteDoctor(@RequestParam int doctorId){
        if(doctorRepository.findById(doctorId).isPresent()){
            doctorService.deleteDoctor(doctorId);
            return new ResponseEntity<>("Doctor with Id-> "+doctorId + " Deleted Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Doctor with given id does not exist",HttpStatus.BAD_REQUEST);
    }

}
