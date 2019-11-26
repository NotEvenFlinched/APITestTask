package apitests;

import org.testng.annotations.Test;
import pageObjects.Employee;

import java.util.Collection;

public class CreateANewEmployee {

    /**
     * this method creates a new employee based on current employee with longest name
     */
    @Test
    public void createANewEmployee() {
        Collection<Employee> employees = Employee.getAllEmployeesCollection();
        String longestName = Employee.findEmployeeWithLongestName(employees);
        Employee.createNewEmployee(longestName, 584111, 111);
    }
}
