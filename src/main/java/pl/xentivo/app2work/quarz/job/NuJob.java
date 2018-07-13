package pl.xentivo.app2work.quarz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.xentivo.app2work.quarz.service.EmailService;

public class NuJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("Should you send BUUUUUUUUUUUUUUU");
    }
}
