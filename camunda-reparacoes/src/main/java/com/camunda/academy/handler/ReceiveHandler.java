package com.camunda.academy.handler;

import com.camunda.academy.service.ReceiveService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class ReceiveHandler implements JobHandler {
    ReceiveService receiveService = new ReceiveService();

    public void handle(JobClient client, ActivatedJob job) throws Exception{
        receiveService.receberRelatorio();
        client.newCompleteCommand(job.getKey()).send().join();
    }
}
