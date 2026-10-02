#!/usr/bin/env python3
from pathlib import Path
import re
import sys
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parents[1]
NS = {
    "bpmn": "http://www.omg.org/spec/BPMN/20100524/MODEL",
    "zeebe": "http://camunda.org/schema/zeebe/1.0",
}
CONFIG = [
    (ROOT / "MachineSale.bpmn", ROOT / "camunda-venda/src/main/java/com/camunda/academy/TechnicalConsultancy.java"),
    (ROOT / "ReparacaoMaquinas.bpmn", ROOT / "camunda-reparacoes/src/main/java/com/camunda/academy/machineHub.java"),
]

def bpmn_job_types(path: Path) -> set[str]:
    root = ET.parse(path).getroot()
    result = set()
    for task_tag in ("serviceTask", "sendTask"):
        for task in root.findall(f".//bpmn:{task_tag}", NS):
            definition = task.find("bpmn:extensionElements/zeebe:taskDefinition", NS)
            if definition is not None and definition.get("type"):
                result.add(definition.get("type"))
    return result

def java_job_types(path: Path) -> set[str]:
    source = path.read_text(encoding="utf-8")
    return set(re.findall(r'\.jobType\("([^\"]+)"\)', source))

def main() -> int:
    ok = True
    for bpmn, java in CONFIG:
        model_types = bpmn_job_types(bpmn)
        worker_types = java_job_types(java)
        print(f"{bpmn.name}: BPMN={sorted(model_types)}")
        print(f"{java.name}: workers={sorted(worker_types)}")
        missing = model_types - worker_types
        extra = worker_types - model_types
        if missing:
            print(f"  Missing Java workers: {sorted(missing)}")
            ok = False
        if extra:
            print(f"  Java workers without BPMN task: {sorted(extra)}")
            ok = False
    return 0 if ok else 1

if __name__ == "__main__":
    sys.exit(main())
