package model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter;

import com.jolbox.bonecp.BoneCPDataSource;

import javax.sql.DataSource;

import java.util.Properties;

@ComponentScan(basePackages = { "model" })
public class ApplicationContext {

    @Bean
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass("org.hsqldb.jdbc.JDBCDriver");
        dataSource.setJdbcUrl("jdbc:hsqldb:mem:aname");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setLogStatementsEnabled(true);
        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager() throws ClassNotFoundException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws ClassNotFoundException {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        //entityManagerFactoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        entityManagerFactoryBean.setDataSource(dataSource());

        //entityManagerFactoryBean.setPersistenceProviderClass(PersistenceProvider.class);
        //entityManagerFactoryBean.setPersistenceProviderClass(PersistenceProviderImpl.class);

        OpenJpaVendorAdapter jpaVendorAdapter = new OpenJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        jpaVendorAdapter.setDatabase(Database.HSQL);
        jpaVendorAdapter.setGenerateDdl(true);
        Properties jpaProperties = new Properties();

        //jpaProperties.put("openjpa.Log", "slf4j");
        /*        jpaProperties.put("openjpa.jdbc.SynchronizeMappings", "buildSchema(ForeignKeys=true)");*/
        jpaProperties.put("openjpa.jdbc.MappingDefaults", "ForeignKeyDeleteAction=restrict, JoinForeignKeyDeleteAction=restrict");
        //jpaProperties.put("openjpa.RuntimeUnenhancedClasses", "supported");
        jpaProperties.put("openjpa.InverseManager", "false");
        jpaProperties.put("eclipselink.ddl-generation", "create-tables");
        jpaProperties.put("eclipselink.ddl-generation.output-mode", "database");
        /*
         * <property name="eclipselink.ddl-generation" value="drop-and-create-tables"/>
            <property name="eclipselink.create-ddl-jdbc-file-name" value="createDDL_ddlGeneration.jdbc"/>
            <property name="eclipselink.drop-ddl-jdbc-file-name" value="dropDDL_ddlGeneration.jdbc"/>
            <property name="eclipselink.ddl-generation.output-mode" value="both"/>
         */

        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        return entityManagerFactoryBean;
        /*
         *  <property name="loadTimeWeaver">
        <bean class="org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver"/>
        </property>
         */
    }
}
