package com.example.DoctorFeatures.service;

import com.example.DoctorFeatures.model.Doctor;
import com.example.DoctorFeatures.repository.DoctorRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    public Doctor createDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
        return doctor;
    }

    public JSONArray getDoctors(String doctorId) {
        JSONArray response = new JSONArray();
        if(null != doctorId) {
            List<Doctor> doctorsList = doctorRepository.getDoctorsByDoctorId(Integer.valueOf(doctorId));
            for (Doctor doctor:doctorsList) {
                JSONObject doctorObj = createResponse(doctor);
                response.put(doctorObj);
            }
        } else {
            List<Doctor> doctorsList = doctorRepository.findAll();
            for (Doctor doctor: doctorsList) {
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

    public void deleteDoctor(int doctorId) {
        doctorRepository.deleteById(doctorId);
    }

}



