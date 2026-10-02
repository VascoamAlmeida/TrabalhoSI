package com.camunda.academy.handler;

import com.camunda.academy.service.Service;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class ServiceHandler implements JobHandler {
    Service service = new Service();

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception{
        service.receberOrdemServico();
        client.newCompleteCommand(job.getKey()).send().join();
    }
}
