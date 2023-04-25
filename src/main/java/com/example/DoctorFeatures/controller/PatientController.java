package com.example.DoctorFeatures.controller;

import com.example.DoctorFeatures.model.Doctor;
import com.example.DoctorFeatures.model.Patient;
import com.example.DoctorFeatures.repository.PatientRepository;
import com.example.DoctorFeatures.service.PatientService;
import jakarta.validation.Valid;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    PatientService patientService;

    @Autowired
    PatientRepository patientRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addPatient(@Valid @RequestBody Patient patient) {

        List<ResponseEntity<String>> validateUser = validateUserRequest(patient);

        Patient newPatient;
        if (validateUser.isEmpty()) {
            newPatient = this.patientService.createPatient(patient);
        } else {
            return new ResponseEntity<String>(validateUser.toString(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(newPatient.toString(), HttpStatus.CREATED);
    }

    private List<ResponseEntity<String>> validateUserRequest(Patient patientData){

        List<ResponseEntity<String>> errorList = new ArrayList<>();

        String city = patientData.getCity();
        if(!(city.equals("delhi")) && !(city.equals("faridabad")) && !(city.equals("noida"))){
            errorList.add(new ResponseEntity<>("We are still waiting to expand to your location.", HttpStatus.BAD_REQUEST));
        }

        String email = patientData.getEmail();
        if(!patientRepository.findByEmail(email).isEmpty()){
            errorList.add(new ResponseEntity<>("Email already exists!!!", HttpStatus.BAD_REQUEST));
        }

        String phoneNumber = patientData.getPhoneNumber();
        if(!patientRepository.findByPhoneNumber(phoneNumber).isEmpty()){
            errorList.add(new ResponseEntity<>("Phone Number already exists!!!", HttpStatus.BAD_REQUEST));
        }
        return errorList;
    }

    @GetMapping("/suggested-doctors")
    public ResponseEntity<String> getSuggestedDoctor(@RequestParam Integer patientId) {
        JSONArray doctorArr = patientService.getSuggestedDoctor(patientId);
        if(doctorArr.isEmpty()){
            return new ResponseEntity<String>("There isn’t any doctor present at your location for your symptom", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(doctorArr.toString(),HttpStatus.FOUND);
    }

    @DeleteMapping("/delete-by-Id")
    public ResponseEntity<String> deleteDoctor(@RequestParam int patientId){
        if(patientRepository.findById(patientId).isPresent()){
            patientService.deleteDoctor(patientId);
            return new ResponseEntity<>("Patient with Id-> "+patientId + " Deleted Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Patient with given id does not exist",HttpStatus.BAD_REQUEST);
    }

}
