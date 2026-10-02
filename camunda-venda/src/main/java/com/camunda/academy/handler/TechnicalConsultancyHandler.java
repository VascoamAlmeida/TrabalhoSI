package com.camunda.academy.handler;



import io.camunda.zeebe.client.api.worker.JobHandler;
import io.camunda.zeebe.client.api.worker.JobClient;

import com.camunda.academy.service.TechnicalConsultancyService;

import io.camunda.zeebe.client.api.response.ActivatedJob;



public class TechnicalConsultancyHandler implements JobHandler {
	
	TechnicalConsultancyService technicalConsultancyService = new TechnicalConsultancyService();
	
	@Override
	public void handle(JobClient client, ActivatedJob job) throws Exception {
		technicalConsultancyService.techinicalCunsultancy();
		client.newCompleteCommand(job.getKey()).send().join();
	}
}
