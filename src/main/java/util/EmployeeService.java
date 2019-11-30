package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pageObjects.Employee;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

@Component
@PropertySource("employee.properties")
public class EmployeeService {
    @Autowired
    HttpRequestor httpRequestor;

    @Value("${defaultSalaryForNewEmployee}")
    private int defaultSalaryForNewEmployee;

    @Value("${defaultAgeForNewEmployee}")
    private int defaultAgeForNewEmployee;

    /**
     * this method prints out all employee's ids and names, but only if employee name contains two digits
     */
    public void printOutAllEmployeesIdsAndNames() {
        Collection<Employee> employees = getAllEmployeesCollection();
        employees.stream()
                .filter(e -> checkIfEmployeeNameContains2Digits(e.getEmployeeName()))
                .forEach(e -> System.out.println("id=<" + e.getId() + ">,name=<" + e.getEmployeeName() + ">"));
    }

    /**
     * this method sends a http request, parses output from json file, and adds employees data to the collection
     */
    public Collection<Employee> getAllEmployeesCollection() {
        String employeesJsonString = httpRequestor.get(Constants.getAllEmployeeData);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Type collectionType = new TypeToken<Collection<Employee>>(){}.getType();
        return gson.fromJson(employeesJsonString, collectionType);
    }

    public static boolean checkIfEmployeeNameContains2Digits(String name) {
        return name.matches("^\\D*(\\d)\\D*(\\d)\\D*$");
    }

    public static String findEmployeeWithLongestName(Collection<Employee> employees) {
        Optional<String> longestName = employees.stream().map(Employee::getEmployeeName).max(Comparator.comparingInt(String::length));
        return longestName.orElse("");
    }

    /**
     * this method creates a new employee based on current employee with longest name
     */
    public void createNewEmployee() {
        Collection<Employee> employees = getAllEmployeesCollection();
        String longestName = findEmployeeWithLongestName(employees);
        // we need to replace any " symbol with \" to match proper data format
        String fixedName = longestName.replaceAll("\"", "\\\\\"");
        String nameWithCurrentDate = java.time.LocalTime.now().toString() + fixedName;
        String requestBody = "{\"name\": \"" + nameWithCurrentDate + "\", \"salary\": \"" + defaultSalaryForNewEmployee + "\", \"age\": \"" + defaultAgeForNewEmployee + "\"}";
        System.out.println(httpRequestor.post(Constants.createNewEmployee, requestBody));
    }
}
