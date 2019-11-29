package pageObjects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import util.Constants;
import util.HttpRequestor;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Employee {
    @Expose
    public int id;
    @Expose
    public String employee_name;

    //according to test task, there is no need to deserialize these fields
    public int employee_salary;
    public int employee_age;
    public String profile_image;

    /**
     * this method sends a http request, parses output from json file, and adds employees data to the collection
     */
    public static Collection<Employee> getAllEmployeesCollection() {
        String employeesJsonString = HttpRequestor.get(Constants.getAllEmployeeData);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Type collectionType = new TypeToken<Collection<Employee>>(){}.getType();
        return gson.fromJson(employeesJsonString, collectionType);
    }

    public static boolean checkIfEmployeeNameContains2Digits(String name) {
        return name.matches("^\\D*(\\d)\\D*(\\d)\\D*$");
    }

    public static String findEmployeeWithLongestName(Collection<Employee> employees) {
        Optional<String> longestName = employees.stream().map(employee -> employee.employee_name).max(Comparator.comparingInt(String::length));
        return longestName.orElse("");
    }

    public static void createNewEmployee(String name, int salary, int age) {
        // we need to replace any " symbol with \" to match proper data format
        String fixedName = name.replaceAll("\"", "\\\\\"");
        String nameWithCurrentDate = java.time.LocalTime.now().toString() + fixedName;
        String requestBody = "{\"name\": \"" + nameWithCurrentDate + "\", \"salary\": \"" + salary + "\", \"age\": \"" + age + "\"}";
        System.out.println(HttpRequestor.post(Constants.createNewEmployee, requestBody));
    }
}
