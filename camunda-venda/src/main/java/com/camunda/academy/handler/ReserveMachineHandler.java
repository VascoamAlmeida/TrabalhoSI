package com.camunda.academy.handler;
import io.camunda.zeebe.client.api.worker.JobHandler;
import io.camunda.zeebe.client.api.worker.JobClient;

import com.camunda.academy.service.ReserveMachineService;

import io.camunda.zeebe.client.api.response.ActivatedJob;

public class ReserveMachineHandler implements JobHandler {

	ReserveMachineService reserveMachineService = new ReserveMachineService();
	
	@Override
	public void handle(JobClient client, ActivatedJob job) throws Exception {
		reserveMachineService.reserveMachine();
		client.newCompleteCommand(job.getKey()).send().join();
	}
}

