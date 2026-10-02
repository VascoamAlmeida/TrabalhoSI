# Information Systems — Business Processes with Camunda

Academic Information Systems project focused on **business-process analysis and BPMN automation** for a company that sells, rents, and repairs textile and industrial machinery.

The project combines business-analysis artefacts with two executable Camunda 8 / Zeebe proof-of-concept workflows: **machine sales** and **post-sale machine repair**.

## What the project demonstrates

- Business Model Canvas (BMC)
- process mapping
- SIPOC analysis
- BPMN process modelling
- Camunda 8 / Zeebe job workers
- Java worker handlers and service separation
- workflow variables and decision gateways
- Maven multi-module project structure
- basic automated tests
- configuration through environment variables rather than embedded credentials

## Business context

The case study addresses problems such as fragmented information, stock-management difficulties, and repair-process coordination in the textile/industrial machinery market.

A broader process map was created for sales, transport, parts, repairs, rentals, technical consultancy, stock management, suppliers, marketing, finance, and internal management processes. Machine sales and post-sale repair were selected as critical processes for detailed SIPOC/BPMN modelling and Camunda automation.

## Repository structure

```text
.
├── MachineSale.bpmn
├── ReparacaoMaquinas.bpmn
├── camunda-venda/
│   ├── pom.xml
│   └── src/
├── camunda-reparacoes/
│   ├── pom.xml
│   └── src/
├── artefactos/
│   ├── BMC.pdf
│   ├── MapaProcessos.pdf
│   ├── SIPOC-Venda.pdf
│   ├── SIPOC-Reparacao.pdf
│   ├── MachineSale.pdf
│   └── ReparacaoMaquinas.pdf
├── docs/
│   └── PROCESS_OVERVIEW.md
├── scripts/
│   └── validate_bpmn_workers.py
├── CAMUNDA_CONFIGURATION.md
├── PORTFOLIO_CHANGES.md
└── pom.xml
```

## Process 1 — Machine sale

The sales process covers requirement identification, technical consultancy, machine presentation, customer decision, stock/database verification, reservation or ordering, and sale persistence.

The Java worker module registers these Zeebe job types:

- `techinicalCunsultancy`
- `searchDB`
- `reserveMachine`
- `orderMachine`
- `saveSale`

The `searchDB` worker produces the `MachineExists` workflow variable, which is used by the BPMN gateway to decide whether the machine should be reserved or ordered.

## Process 2 — Machine repair

The repair process covers the repair request, technical-visit scheduling, notification, customer decision, repair start/cancellation, repair completion, report receipt, and archival.

The Java worker module registers:

- `marcarReparacao`
- `receiveNotification`
- `cancelaReparacao`
- `iniciarReparacao`
- `receiveReport`
- `saveRepair`

See [`docs/PROCESS_OVERVIEW.md`](docs/PROCESS_OVERVIEW.md) for a more detailed mapping.

## Technology

- Java 17
- Maven
- Camunda 8 / Zeebe Java Client
- BPMN 2.0
- JUnit 5
- Python 3 for the BPMN/worker consistency check

## Build

Requirements:

- JDK 17+
- Maven 3.8+

Build and run tests for both worker modules from the repository root:

```bash
mvn clean test
```

## Validate BPMN/worker contracts

The repository includes a small script that verifies that every Zeebe job type used by the BPMN service/send tasks has a corresponding Java worker registration:

```bash
python scripts/validate_bpmn_workers.py
```

A successful result shows matching job-type sets for both processes.

## Camunda configuration

Real Camunda credentials are **not** stored in the repository. Configure these environment variables before starting either worker application:

```text
CAMUNDA_ZEEBE_ADDRESS
CAMUNDA_ZEEBE_CLIENT_ID
CAMUNDA_ZEEBE_CLIENT_SECRET
```

See [`CAMUNDA_CONFIGURATION.md`](CAMUNDA_CONFIGURATION.md) and [`.env.example`](.env.example).

The BPMN models must also be deployed to a compatible Camunda 8 environment before the corresponding jobs can be processed.

## Running the worker applications

After configuring Camunda, run the main class for the process you want to service:

- Sales: `com.camunda.academy.TechnicalConsultancy`
- Repairs: `com.camunda.academy.machineHub`

The applications register all workers for their respective process and remain active until interrupted.

## Analysis artefacts

The [`artefactos`](artefactos/) folder contains the Business Model Canvas, process map, SIPOC matrices, and PDF exports of both BPMN models.

The original group report and presentation are not included in the public repository because they contain personal information about other project participants.

## Scope and limitations

This is an academic proof of concept rather than a production system. The Java service implementations simulate business actions through simple service methods; real database, ERP, inventory, notification, and repair-management integrations are outside the project scope.

The public portfolio version also contains a small number of maintenance and security corrections made after the academic submission. These are documented in [`PORTFOLIO_CHANGES.md`](PORTFOLIO_CHANGES.md).
