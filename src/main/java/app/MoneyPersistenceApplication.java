package app;

import config.SwaggerConfig;
import model.Person;
import model.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import rabbit.QueueConsumer;
import repository.TransactionRepository;
import resource.TransactionPersistenceResource;
import service.TransactionService;



@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = TransactionRepository.class)
@EntityScan(basePackageClasses = Transaction.class)
@ComponentScan(basePackageClasses = {TransactionPersistenceResource.class,
        Person.class,
        SwaggerConfig.class,
        TransactionService.class,
        QueueConsumer.class})
public class MoneyPersistenceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoneyPersistenceApplication.class, args);
    }

}