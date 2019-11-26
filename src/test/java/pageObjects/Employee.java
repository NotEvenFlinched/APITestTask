package pageObjects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import util.Constants;
import util.HttpRequestor;

import java.lang.reflect.Type;
import java.util.Collection;

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
        int numberOfDigits = 0;
        for (int i = 0; i < name.length(); i++) {
            if (Character.isDigit(name.charAt(i))) {
                numberOfDigits++;
            }
        }
        return numberOfDigits == 2;
    }

    public static String findEmployeeWithLongestName(Collection<Employee> employees) {
        int maxLength = 0;
        String longestString = "";
        for (Employee employee : employees) {
            if (employee.employee_name.length() > maxLength) {
                maxLength = employee.employee_name.length();
                longestString = employee.employee_name;
            }
        }
        return longestString;
    }

    public static void createNewEmployee(String name, int salary, int age) {
        String nameWithCurrentDate = name + "_" + java.time.LocalTime.now().toString();
        String requestBody = "{\"name\": \"" + nameWithCurrentDate + "\", \"salary\": \"" + salary + "\", \"age\": \"" + age + "\"}";
        System.out.println(HttpRequestor.post(Constants.createNewEmployee, requestBody));
    }
}
