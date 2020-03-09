package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ClinicalData;
import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;
import com.example.demo.util.BMICalculator;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PatientController {
	private PatientRepository repository;
	Map<String, String> filters = new HashMap<String, String>();

	@Autowired
	public PatientController(PatientRepository repository) {
		this.repository = repository;
	}

	@RequestMapping(value = "/patients", method = RequestMethod.GET)
	public List<Patient> getPatients() {
		return repository.findAll();
	}

	@RequestMapping(value = "/patients/{id}", method = RequestMethod.GET)
	public Patient getPatient(@PathVariable("id") int id) {
		return repository.findById(id).get();
	}

	@RequestMapping(value = "/patients", method = RequestMethod.POST)
	public Patient savePatient(@RequestBody Patient patient) {
		return repository.save(patient);
	}

	@RequestMapping(value = "/patient/analyze/{id}", method = RequestMethod.GET)
	public Patient analyse(@PathVariable("id") int id) {
		Patient patient = repository.findById(id).get();
		List<ClinicalData> clinicalData = patient.getClinicalData();
		List<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalData);
		for (ClinicalData clinicalData2 : duplicateClinicalData) {
			if (filters.containsKey(clinicalData2.getComponentName())) {
				clinicalData.remove(clinicalData2);
				continue;
			} else {
				filters.put(clinicalData2.getComponentName(), null);
			}

			BMICalculator.calculateBMI(clinicalData, clinicalData2);
		}
		filters.clear();
		return patient;
	}

}
