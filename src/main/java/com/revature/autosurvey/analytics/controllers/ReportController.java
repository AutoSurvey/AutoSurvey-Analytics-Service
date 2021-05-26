package com.revature.autosurvey.analytics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.autosurvey.analytics.beans.Report;
import com.revature.autosurvey.analytics.beans.Response.WeekEnum;
import com.revature.autosurvey.analytics.services.ReportService;

import reactor.core.publisher.Mono;


@RestController
public class ReportController {

	@Autowired
	private ReportService reportService;
	
	@GetMapping(params = {"surveyId"})
	public Mono<ResponseEntity<Report>> getReport(String surveyId) {
		return reportService.getReport(surveyId).map(report -> ResponseEntity.status(200).body(report));
	}
	
	@GetMapping(params = {"surveyId", "weekEnum"})
	public Mono<ResponseEntity<Report>> getReport(String surveyId, WeekEnum weekEnum) {
		return reportService.getReport(surveyId, weekEnum).map(report -> ResponseEntity.status(200).body(report));
	}
}
