package com.camunda.academy.service;

import java.util.Arrays;
import java.util.List;

public class MachineExistenceService {
	
    private static final List<String> EXISTING_MACHINES = Arrays.asList(
            "machine1", "machine2", "machine3" 
        );
    public boolean doesMachineExist(String machineID) {
        if (machineID == null) {
            System.out.println("machineID é nulo.");
            return false;
        }

        boolean exists = EXISTING_MACHINES.contains(machineID.toLowerCase());
        if (exists) {
            System.out.println("Máquina existe: " + machineID);
        } else {
            System.out.println("Máquina não existe: " + machineID);
        }

        return exists;
    }
}
