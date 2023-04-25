package com.example.DoctorFeatures.service;

import com.example.DoctorFeatures.model.Doctor;
import com.example.DoctorFeatures.model.Patient;
import com.example.DoctorFeatures.repository.DoctorRepository;
import com.example.DoctorFeatures.repository.PatientRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    public Patient createPatient(Patient patient) {
        patientRepository.save(patient);
        return patient;
    }

    public void deleteDoctor(int patientId) {
        patientRepository.deleteById(patientId);
    }

    public JSONArray getSuggestedDoctor(Integer patientId) {
        JSONArray response = new JSONArray();
        Patient patient=patientRepository.findById(patientId).get();
        String symptom = patient.getSymptom();
        String pCity = patient.getCity();

        if(symptom.equals("arthritis") || symptom.equals("back pain") || symptom.equals("tissue injuries")){
            List<Doctor> doctorsList=doctorRepository.findByCityAndSpeciality(pCity,"orthopedic");
            for (Doctor doctor:doctorsList) {
                JSONObject doctorObj = createResponse(doctor);
                response.put(doctorObj);
            }
        } else if (symptom.equals("Dysmenorrhea")) {
            List<Doctor> doctorsList=doctorRepository.findByCityAndSpeciality(pCity,"gynecology");
            for (Doctor doctor:doctorsList) {
                JSONObject doctorObj = createResponse(doctor);
                response.put(doctorObj);
            }
        } else if (symptom.equals("skin infection") || symptom.equals("skin burn") ) {
            List<Doctor> doctorsList=doctorRepository.findByCityAndSpeciality(pCity,"dermatology");
            for (Doctor doctor:doctorsList) {
                JSONObject doctorObj = createResponse(doctor);
                response.put(doctorObj);
            }
        } else if (symptom.equals("ear pain")) {
            List<Doctor> doctorsList=doctorRepository.findByCityAndSpeciality(pCity,"ENT");
            for (Doctor doctor:doctorsList) {
                JSONObject doctorObj = createResponse(doctor);
                response.put(doctorObj);
            }
        }
        return response;
    }

    private JSONObject createResponse(Doctor doctor) {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("doctorId", doctor.getDoctorId());
        jsonObj.put("Name", doctor.getName());
        jsonObj.put("email", doctor.getEmail());
        jsonObj.put("phoneNumber", doctor.getPhoneNumber());
        jsonObj.put("speciality", doctor.getSpeciality());
        return jsonObj;
    }
}
