package scheduling.business.logic.scheduler;

import scheduling.business.logic.entity.Employee;
import scheduling.business.logic.entity.MachineTool;
import scheduling.business.logic.entity.Operation;
import scheduling.business.logic.entity.type.EmployeeType;
import scheduling.business.logic.time.intervals.TimeIntervalSet;
import scheduling.business.logic.work.Work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scheduler {

    Map<EmployeeType, List<Employee>> employeeMap;
    Map<Long, MachineTool> machineToolMap;
    Map<Long, Operation> operationMap;

    public Scheduler(Map<EmployeeType, List<Employee>> employeeMap,
                     Map<Long, MachineTool> machineToolMap,
                     Map<Long, Operation> operationMap) {
        this.employeeMap = employeeMap;
        this.machineToolMap = machineToolMap;
        this.operationMap = operationMap;
    }

    public Scheduler(Scheduler scheduler) {
        employeeMap = new HashMap<>();
        for (Map.Entry<EmployeeType, List<Employee>> entry: scheduler.employeeMap.entrySet()) {
            List<Employee> employees = new ArrayList<>();
            for (Employee employee: entry.getValue()) {
                employees.add(new Employee(employee));
            }
            employeeMap.put(entry.getKey(), employees);
        }

        machineToolMap = new HashMap<>();
        for (Map.Entry<Long, MachineTool> entry: scheduler.machineToolMap.entrySet()) {
            machineToolMap.put(entry.getKey(), new MachineTool(entry.getValue()));
        }

        operationMap = new HashMap<>();
        for (Map.Entry<Long, Operation> entry: scheduler.operationMap.entrySet()) {
            operationMap.put(entry.getKey(), new Operation(entry.getValue()));
        }
    }

    public double getOperationDuration(Long id){
        return operationMap.get(id).getTime();
    }

    public Map<Long, MachineTool> getMachineToolMap(){ return machineToolMap; }

    public Map<Long, Operation> getOperationMap() { return operationMap; }

    public void clear() {
        for (Map.Entry<EmployeeType, List<Employee>> entry : employeeMap.entrySet()) {
            for (Employee employee : entry.getValue()) {
                employee.clearTimeIntervalSet();
                employee.addInterval(0, 0);
            }
        }
        for (Map.Entry<Long, MachineTool> entry : machineToolMap.entrySet()) {
            MachineTool machine = entry.getValue();
            machine.clearTimeIntervalSet();
            machine.addInterval(0, 0);
        }
    }

    public int addToScheduleWithMachinePriorities(Work work, int start, HashMap<Long, Double> priorities){
        Long res = priorities.entrySet().iterator().next().getKey();

        Operation operation = operationMap.get(work.getOperationId());
        List<Employee> employees = employeeMap.get(operation.getEmployeeType());
        List<MachineTool> machines = new ArrayList<>();
        for (MachineTool machineTool : operation.getMachineTools()) {
            machines.add(machineToolMap.get(machineTool.getId()));
        }

        int time = operation.getTime();
        int end = start + time;

        Employee chosenEmployee = null;
        MachineTool chosenMachineTool = null;
        int bestStart = Integer.MAX_VALUE;
        int bestStartWithPriority = Integer.MAX_VALUE;
        for (Employee employee : employees) {
            for (MachineTool machine : machines) {
                TimeIntervalSet emploeeSet = employee.getTimeIntervalSet();
                TimeIntervalSet machineSet = machine.getTimeIntervalSet();
                TimeIntervalSet intersection = emploeeSet.getUnionWith(machineSet);

                int currentStart = intersection.findEarliestStart(start, end);
                int currentStartWithPriority = currentStart + (int)Math.floor(priorities.get(machine.getId()));
                if (currentStartWithPriority < bestStartWithPriority) {
                    bestStart = currentStart;
                    bestStartWithPriority = currentStartWithPriority;
                    chosenEmployee = employee;
                    chosenMachineTool = machine;
                }
            }
        }

        if (bestStart == Integer.MAX_VALUE || chosenEmployee == null || chosenMachineTool == null) {
            throw new RuntimeException("Can't find any employee or/and machine for operation " +
                    operation.getName() + " for lot " + work.getLotId());
        } else {
            end = bestStart + time;
            int empStart = chosenEmployee.addInterval(bestStart, end);
            int machStart = chosenMachineTool.addInterval(bestStart, end);
            if (empStart != bestStart || machStart != bestStart) {
                throw new RuntimeException("Do not match employee and machine start point for operation " +
                        operation.getName() + " for lot " + work.getLotId());
            } else {
                work.setData(chosenMachineTool.getId(), chosenEmployee.getId(), bestStart, end);
            }
        }

        return end;
    }

    // Find first possible position for work in schedule after time start
    // Return operation end time
    public int addToSchedule(Work work, int start) {
        Operation operation = operationMap.get(work.getOperationId());
        List<Employee> employees = employeeMap.get(operation.getEmployeeType());
        List<MachineTool> machines = new ArrayList<>();
        for (MachineTool machineTool : operation.getMachineTools()) {
            machines.add(machineToolMap.get(machineTool.getId()));
        }

        int time = operation.getTime();
        int end = start + time;

        Employee chosenEmployee = null;
        MachineTool chosenMachineTool = null;
        int bestStart = Integer.MAX_VALUE;
        for (Employee employee : employees) {
            for (MachineTool machine : machines) {
                TimeIntervalSet emploeeSet = employee.getTimeIntervalSet();
                TimeIntervalSet machineSet = machine.getTimeIntervalSet();
                TimeIntervalSet intersection = emploeeSet.getUnionWith(machineSet);

                int currentStart = intersection.findEarliestStart(start, end);
                if (currentStart < bestStart) {
                    bestStart = currentStart;
                    chosenEmployee = employee;
                    chosenMachineTool = machine;
                }
            }
        }

        if (bestStart == Integer.MAX_VALUE || chosenEmployee == null || chosenMachineTool == null) {
            throw new RuntimeException("Can't find any employee or/and machine for operation " +
                    operation.getName() + " for lot " + work.getLotId());
        } else {
            end = bestStart + time;
            int empStart = chosenEmployee.addInterval(bestStart, end);
            int machStart = chosenMachineTool.addInterval(bestStart, end);
            if (empStart != bestStart || machStart != bestStart) {
                throw new RuntimeException("Do not match employee and machine start point for operation " +
                        operation.getName() + " for lot " + work.getLotId());
            } else {
                work.setData(chosenMachineTool.getId(), chosenEmployee.getId(), bestStart, end);
            }
        }

        return end;
    }

}
