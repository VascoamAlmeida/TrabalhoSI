package com.camunda.academy.handler;

import com.camunda.academy.service.ArchiveService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class ArchiveHandler implements JobHandler {

    ArchiveService archiveService = new ArchiveService();

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception{
        archiveService.arquivarReparacao();
        client.newCompleteCommand(job.getKey()).send().join();
    }
}
