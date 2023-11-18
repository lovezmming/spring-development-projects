package com.job.job;

import com.job.service.JobDataTestService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class jobDefineManager {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobDataTestService jobDataTestService;

    @PostConstruct
    public void init() {
        // 初始化定时任务: 服务启动查询需要定时执行的任务加入job执行
        List<String> jobDataList = jobDataTestService.getJobDataList();
        jobDataList.forEach(data -> {
            try {
                addScheduleJob(data, "JOB_DATA", "0 0/5 * * * ?");
            } catch (Exception e) {
                log.error("addScheduleJob error ", e);
            }
        });

        List<String> jobDataTestList = jobDataTestService.getJobDataTestList();
        jobDataTestList.forEach(testData -> {
            try {
                addScheduleJob(testData, "JOB_DATA_TEST", "0 0/5 * * * ?");
            } catch (Exception e) {
                log.error("addScheduleJob error ", e);
            }
        });
    }

    public void addScheduleJob(String triggerName, String triggerGroup, String triggerCron) throws SchedulerException {
        JobDetail detail = JobBuilder.newJob(DataScheduleJob.class)
                .withIdentity(triggerName, triggerGroup)
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, triggerGroup)
                .startAt(new Date())
//                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(triggerCron))
                .build();
        scheduler.scheduleJob(detail, trigger);
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }
    }

    public void removeScheduleJob(String triggerName, String triggerGroup) throws SchedulerException {
        // 暂停trigger
        scheduler.pauseTrigger(TriggerKey.triggerKey(triggerName, triggerGroup));
        // 取消调度job
        scheduler.unscheduleJob(TriggerKey.triggerKey(triggerName, triggerGroup));
        // 删除job
        scheduler.deleteJob(JobKey.jobKey(triggerName, triggerGroup));
    }
}
