package pl.xentivo.app2work.quarz.config;



import org.quartz.SimpleTrigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import pl.xentivo.app2work.quarz.job.EmailJob;
import pl.xentivo.app2work.quarz.quartz.QuartzJobFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by gkatzioura on 6/7/16.
 */
@Configuration
public class QuartzConfig {

    @Value("${org.quartz.scheduler.instanceName}")
    private String instanceName;

    @Value("${org.quartz.scheduler.instanceId}")
    private String instanceId;

    @Value("${org.quartz.threadPool.threadCount}")
    private String threadCount;



    @Autowired
    private DataSource dataSource;

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {

        QuartzJobFactory sampleJobFactory = new QuartzJobFactory();
        sampleJobFactory.setApplicationContext(applicationContext);
        return sampleJobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext) {

        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        factory.setJobFactory(jobFactory(applicationContext));
        Properties quartzProperties = new Properties();
        quartzProperties.setProperty("org.quartz.scheduler.instanceName",instanceName);
        quartzProperties.setProperty("org.quartz.scheduler.instanceId",instanceId);
        quartzProperties.setProperty("org.quartz.threadPool.threadCount",threadCount);
        factory.setDataSource(dataSource);
        factory.setAutoStartup(false);
        factory.setQuartzProperties(quartzProperties);

        return factory;
    }

    @Bean
    public SimpleTriggerFactoryBean emailJobTrigger() {


        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(emailJobDetails().getObject());
        factoryBean.setStartDelay(0);
        factoryBean.setRepeatInterval(123);
        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
        return factoryBean;
    }

    @Bean
    public JobDetailFactoryBean emailJobDetails() {

        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(EmailJob.class);
        jobDetailFactoryBean.setDescription("ada");
        jobDetailFactoryBean.setDurability(true);
        jobDetailFactoryBean.setName("Ada");

        return jobDetailFactoryBean;
    }


}