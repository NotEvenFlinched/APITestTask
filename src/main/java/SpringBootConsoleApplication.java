import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import util.EmployeeService;

@SpringBootApplication
@ComponentScan(basePackages = {"util"})
public class SpringBootConsoleApplication implements CommandLineRunner {
    @Autowired
    private EmployeeService employeeService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootConsoleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        employeeService.printOutAllEmployeesIdsAndNames();
        employeeService.createNewEmployee();
    }
}
