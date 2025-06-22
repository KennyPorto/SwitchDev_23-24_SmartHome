package SmartHome.persistence.springdata;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "SmartHome.persistence.springdata")
@ComponentScan("SmartHome.persistence.springdata")
public class SpringDataConfig {

    LocalContainerEntityManagerFactoryBean em;

    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        em = new LocalContainerEntityManagerFactoryBean();

        em.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
        em.setPersistenceUnitName("smarthome");

        em.setPackagesToScan("SmartHome.persistence.jpa.datamodel");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
