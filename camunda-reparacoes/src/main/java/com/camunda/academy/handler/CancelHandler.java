package com.camunda.academy.handler;

import com.camunda.academy.service.CancelService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class CancelHandler implements JobHandler {
    CancelService cancelService = new CancelService();

    public void handle(JobClient client, ActivatedJob job) throws Exception{
        cancelService.cancelaReparacao();
        client.newCompleteCommand(job.getKey()).send().join();
    }
}
