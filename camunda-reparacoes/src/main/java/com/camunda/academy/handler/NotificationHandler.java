package com.camunda.academy.handler;

import com.camunda.academy.service.NotificationService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class NotificationHandler implements JobHandler{
    NotificationService notificationService = new NotificationService();

    public void handle(JobClient client, ActivatedJob job) throws Exception{
        notificationService.receberNotificacao();
        client.newCompleteCommand(job.getKey()).send().join();
    }
}
