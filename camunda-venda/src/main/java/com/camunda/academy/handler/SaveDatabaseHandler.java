package com.camunda.academy.handler;


import com.camunda.academy.service.SaveDatabaseService;

import io.camunda.zeebe.client.api.worker.JobHandler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;

public class SaveDatabaseHandler implements JobHandler {

	SaveDatabaseService saveDatabaseService = new SaveDatabaseService();
	
	@Override
	public void handle(JobClient client, ActivatedJob job) throws Exception {
		saveDatabaseService.saveSale();
		client.newCompleteCommand(job.getKey()).send().join();
	}
}
