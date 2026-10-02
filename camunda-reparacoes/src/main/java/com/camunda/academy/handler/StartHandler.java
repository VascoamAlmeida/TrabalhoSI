package com.camunda.academy.handler;

import com.camunda.academy.service.StartRepairService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class StartHandler implements JobHandler {
    StartRepairService startRepairService = new StartRepairService();

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception{
        startRepairService.iniciarReparacao();
        client.newCompleteCommand(job.getKey()).send().join();
    }
}
