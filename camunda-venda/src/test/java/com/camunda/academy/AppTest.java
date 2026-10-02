package com.camunda.academy;

import com.camunda.academy.service.MachineExistenceService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppTest {
    private final MachineExistenceService service = new MachineExistenceService();

    @Test
    void knownMachinesAreDetectedCaseInsensitively() {
        assertTrue(service.doesMachineExist("machine1"));
        assertTrue(service.doesMachineExist("MACHINE2"));
    }

    @Test
    void unknownOrMissingMachinesAreRejected() {
        assertFalse(service.doesMachineExist("machine99"));
        assertFalse(service.doesMachineExist(null));
    }
}
