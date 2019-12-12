package com.shev.java2python.controller;

import com.alibaba.fastjson.JSONObject;
import com.shev.java2python.common.utils.ServerUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
public class Java2PythonController
{

    private static final String SERVER_HOHUP = "nohup";

    private static final String SERVER_PATTEN = "&";

    private static final String COURSE_HTTP = "http://";

    private static final String URL = "192.168.1.123:8108/python2java?";

    @PostMapping("/java2Python")
    @ResponseBody
    public String java2Python(@RequestBody String requestJson)
    {
        Map<String, Object> serverMap = (Map<String, Object>) JSONObject.parse(requestJson);
        String serverIp = (String) serverMap.get("serverIp");
        String serverUserName = (String) serverMap.get("serverUserName");
        String serverUserPwd = (String) serverMap.get("serverUserPwd");
        int serverPort = Integer.valueOf((String) serverMap.get("serverPort"));
        new ServerUtil(serverIp, serverPort, serverUserName, serverUserPwd);
        Long dateTime = new Date().getTime();
        String userId = (String) serverMap.get("userId");
        String cmd = SERVER_HOHUP + "/home/python/pythonArrangement" + COURSE_HTTP + URL + " " + userId + " " + dateTime + " " + SERVER_PATTEN;
        ServerUtil.execCommand(userId + SERVER_PATTEN + dateTime, cmd);
        return "success";
    }

    @PostMapping("/python2Java")
    @ResponseBody
    public String python2Java(@RequestBody String requestJson)
    {
        Map<String, Object> serverMap = (Map<String, Object>) JSONObject.parse(requestJson);
        String dateTime = (String) serverMap.get("dateTime");
        String userId = (String) serverMap.get("userId");
        String resultFlag = (String) serverMap.get("resultFlag");
        System.out.println("result:" + resultFlag);
        // deal after
        ServerUtil.closeChannelSession(userId + SERVER_PATTEN + dateTime);
        return "success";
    }

}
