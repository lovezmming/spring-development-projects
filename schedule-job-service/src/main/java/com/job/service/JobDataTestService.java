package com.job.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobDataTestService {
    private static final List<String> jobDataTestList = new ArrayList<>();
    private static final List<String> jobDataList = new ArrayList<>();

    @PostConstruct
    private void initData() {
        for (int i = 0; i < 3; i++) {
            jobDataTestList.add("test" + i);
            jobDataList.add("data" + i);
        }
    }

    public List<String> getJobDataTestList() {
        return jobDataTestList;
    }

    public List<String> getJobDataList() {
        return jobDataList;
    }
}
