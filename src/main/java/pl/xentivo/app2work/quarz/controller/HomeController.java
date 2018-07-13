package pl.xentivo.app2work.quarz.controller;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.xentivo.app2work.quarz.job.EmailJob;

@Controller
public class HomeController {


    @GetMapping("/")
    public String home() throws SchedulerException {
        try {
            JobDetail job1 = JobBuilder.newJob(EmailJob.class).withIdentity("emailJob", "group1").build();

            Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("simpleTrigger", "group1")
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(30)).build();
            Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
            scheduler1.start();
            scheduler1.scheduleJob(job1, trigger1);


        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }



        return "index";
    }
}
