package com.job.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataScheduleJob implements Job {
    Logger log = LoggerFactory.getLogger(DataScheduleJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String triggerName = context.getJobDetail().getKey().getName();
        log.info("triggerName: {}", triggerName);
    }
}
