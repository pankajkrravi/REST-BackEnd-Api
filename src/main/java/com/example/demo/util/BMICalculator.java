package com.example.demo.util;

import java.util.List;

import com.example.demo.model.ClinicalData;

public class BMICalculator {
	public static void calculateBMI(List<ClinicalData> clinicalData, ClinicalData eachEntry) {
		if (eachEntry.getComponentName().equals("hw")) {
			String[] heighAndWeight = eachEntry.getComponentValue().split("/");
			if (heighAndWeight != null && heighAndWeight.length > 1) {
				float heightinmetres = Float.parseFloat(heighAndWeight[0]) * 0.4536F;
				float bmi = Float.parseFloat(heighAndWeight[1]) / (heightinmetres * heightinmetres);
				ClinicalData bmidata = new ClinicalData();
				bmidata.setComponentName("bmi");
				bmidata.setComponentValue(Float.toString(bmi));
				clinicalData.add(bmidata);
			}
		}
	}
}
