# Process overview

This repository contains two BPMN processes selected from a broader information-systems analysis for a business that sells, rents, and repairs textile and industrial machinery.

The broader analysis also included a Business Model Canvas, a process map, and SIPOC matrices. The two processes treated as critical and automated as proof-of-concept workflows were machine sales and post-sale machine repair.

## Machine sale

Main BPMN flow:

1. receive a machine-sale request;
2. identify customer requirements;
3. perform technical consultancy;
4. present suitable machines;
5. process the customer's decision;
6. check machine availability;
7. reserve an existing machine or order a missing machine;
8. save the sale;
9. finish the sale process.

Camunda/Zeebe job types implemented by the Java worker module:

- `techinicalCunsultancy`
- `searchDB`
- `reserveMachine`
- `orderMachine`
- `saveSale`

The original spelling of `techinicalCunsultancy` is intentionally preserved because the BPMN model and Java worker use it as the same job-type contract.

The `searchDB` worker evaluates the selected machine and returns the workflow variable `MachineExists`. The BPMN gateway uses that value to choose between reservation and ordering.

## Machine repair

Main BPMN flow:

1. receive a repair request/service order;
2. schedule the technical visit;
3. notify the relevant participant;
4. evaluate the customer's response;
5. start or cancel the repair;
6. complete the repair;
7. receive the repair report;
8. archive the repair;
9. finish the process.

Camunda/Zeebe job types implemented by the Java worker module:

- `marcarReparacao`
- `receiveNotification`
- `cancelaReparacao`
- `iniciarReparacao`
- `receiveReport`
- `saveRepair`

## Scope of the Java workers

The worker/service implementations are a proof of concept for process orchestration. They demonstrate Camunda job registration, job completion, workflow variables, and BPMN-to-worker integration. The service classes intentionally remain lightweight and do not implement production integrations such as a real stock database, ERP, notification provider, or repair-management system.
