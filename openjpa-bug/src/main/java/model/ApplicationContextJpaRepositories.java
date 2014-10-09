package model;

import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Import(ApplicationContext.class)
@EnableJpaRepositories(basePackages = "model")
public class ApplicationContextJpaRepositories {

}
