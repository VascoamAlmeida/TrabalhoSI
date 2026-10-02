package com.camunda.academy;

import com.camunda.academy.service.ArchiveService;
import com.camunda.academy.service.CancelService;
import com.camunda.academy.service.NotificationService;
import com.camunda.academy.service.ReceiveService;
import com.camunda.academy.service.Service;
import com.camunda.academy.service.StartRepairService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AppTest {
    @Test
    void repairServicesCanBeExecuted() {
        assertDoesNotThrow(() -> {
            new Service().receberOrdemServico();
            new NotificationService().receberNotificacao();
            new StartRepairService().iniciarReparacao();
            new CancelService().cancelaReparacao();
            new ReceiveService().receberRelatorio();
            new ArchiveService().arquivarReparacao();
        });
    }
}
