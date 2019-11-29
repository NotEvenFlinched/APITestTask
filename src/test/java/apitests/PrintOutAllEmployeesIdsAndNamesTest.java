package apitests;

import org.testng.annotations.Test;
import pageObjects.Employee;

import java.util.Collection;

public class PrintOutAllEmployeesIdsAndNamesTest {

    /**
     * this method prints out all employee's ids and names, but only if employee name contains two digits
     */
    @Test
    public void printOutAllEmployeesIdsAndNames() {
        Collection<Employee> employees = Employee.getAllEmployeesCollection();
        employees.stream()
                .filter(e -> Employee.checkIfEmployeeNameContains2Digits(e.employee_name))
                .forEach(e -> System.out.println("id=<" + e.id + ">,name=<" + e.employee_name + ">"));
    }
}
