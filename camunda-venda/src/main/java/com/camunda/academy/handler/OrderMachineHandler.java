package com.camunda.academy.handler;

import com.camunda.academy.service.OrderMachineService;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class OrderMachineHandler  implements JobHandler{
	
	OrderMachineService orderMachineService = new OrderMachineService();
	
	@Override
	public void handle(JobClient client, ActivatedJob job) throws Exception {
		orderMachineService.orderMachine();
		client.newCompleteCommand(job.getKey()).send().join();
	}

}
