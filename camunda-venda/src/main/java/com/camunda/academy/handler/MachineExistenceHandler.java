package com.camunda.academy.handler;

import io.camunda.zeebe.client.api.worker.JobHandler;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;

import com.camunda.academy.service.MachineExistenceService;
import java.util.Map;

public class MachineExistenceHandler implements JobHandler {
	
    MachineExistenceService machineService = new MachineExistenceService();

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        // Obter os dados de entrada do Job
        Map<String, Object> variables = job.getVariablesAsMap();
        System.out.println("Job Variables: " + variables);

        // Obter a variável machineID
        String machineID = (String) variables.get("machineID");
        System.out.println("MachineID received: " + machineID);

        boolean machineExists = false;
        if (machineID != null) {
            machineExists = machineService.doesMachineExist(machineID);
            } else {
            System.out.println("machineID não foi fornecido. Definindo MachineExists como false.");
        }
        System.out.println("MachineExists result: " + machineExists);

        // Enviar o resultado (boolean) para o workflow
        client.newCompleteCommand(job.getKey())
                .variables(Map.of("MachineExists", machineExists)) 
                .send()
                .join();

        System.out.println("Job completed and MachineExists sent.");
    }
}
