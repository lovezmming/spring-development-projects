package com.shev.mongodb.controller;

import com.shev.mongodb.entity.TenantNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
public class MongodbController
{
    private Random random = new Random();

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/saveSystem")
    public String saveSystem(@RequestBody String json)
    {
        Date date = new Date();
        TenantNotice tenantNotice = new TenantNotice();
        String id = "10";
        for (int i=0; i <= 1; i++)
        {
            id = id + random.nextInt();
        }
        tenantNotice.setId(id);
        tenantNotice.setContent(json);
        tenantNotice.setTenantId("000001");
        tenantNotice.setTitle(json);
        tenantNotice.setCreateTime(date);
        tenantNotice.setUpdateTime(date);
        mongoTemplate.insert(tenantNotice);
        return tenantNotice.getId();
    }

    @GetMapping("/getSystem")
    public TenantNotice getSystem(String id)
    {
        Query query = Query.query(Criteria.where("_id").is(id));
        List<TenantNotice> tenantNotice = mongoTemplate.find(query, TenantNotice.class);
        return tenantNotice.get(0);
    }

    @PostMapping("/delSystem")
    public void delSystem(String id)
    {
        Query query = Query.query(Criteria.where("_id").is(id));
        mongoTemplate.findAndRemove(query, TenantNotice.class);
    }

}
