package withoutXML;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan(basePackages="withoutXML")
public class JavaConfig {

    @Bean
    public Employee getEmployee(){
        Employee employee = new Employee();
        return employee;
    }
}
