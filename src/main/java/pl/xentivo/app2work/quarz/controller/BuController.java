package pl.xentivo.app2work.quarz.controller;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.xentivo.app2work.quarz.job.NuJob;

@Controller
public class BuController {

    @GetMapping("/bu")
    public String home() throws SchedulerException {
        JobDetail job2 = JobBuilder.newJob(NuJob.class).withIdentity("byeJob", "group2").build();
        Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("cronTrigger", "group2")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")).build();
        Scheduler scheduler2 = new StdSchedulerFactory().getScheduler();
        scheduler2.start();
        scheduler2.scheduleJob(job2, trigger2);



        return "index";
    }
}
