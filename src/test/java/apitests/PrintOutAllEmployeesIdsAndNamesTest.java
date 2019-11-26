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

        for (Employee employee : employees) {
            if (Employee.checkIfEmployeeNameContains2Digits(employee.employee_name))
                System.out.println("id=<" + employee.id + ">,name=<" + employee.employee_name + ">");
        }
    }
}
