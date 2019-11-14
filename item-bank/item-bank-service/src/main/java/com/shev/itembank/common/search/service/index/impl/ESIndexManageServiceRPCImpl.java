package com.shev.itembank.common.search.service.index.impl;

import java.net.InetAddress;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.shev.itembank.common.Enum.ConstantEnum;
import com.shev.itembank.common.Enum.ExerciseRelationEnum;
import com.shev.itembank.common.base.exception.BusinessException;
import com.shev.itembank.common.base.util.TextUtil;
import com.shev.itembank.common.elasticsearch.index.IndexName;
import com.shev.itembank.common.search.service.index.IndexManageService;
import com.shev.itembank.edumeta.custom.ProficiencyCustomMapper;
import com.shev.itembank.edumeta.entity.Proficiency;
import com.shev.itembank.edumeta.entity.TextbookStructureCategory;
import com.shev.itembank.edumeta.mapper.ProficiencyMapper;
import com.shev.itembank.edumeta.mapper.TextbookStructureCategoryMapper;
import com.shev.itembank.exercise.custom.*;
import com.shev.itembank.exercise.entity.*;
import com.shev.itembank.exercise.mapper.ExerciseAddInfoMapper;
import com.shev.itembank.exercise.mapper.ExerciseMapper;
import com.shev.itembank.paper.custom.PaperCityRelationCustomMapper;
import com.shev.itembank.paper.custom.PaperProvinceRelationCustomMapper;
import com.shev.itembank.paper.custom.PaperSectionExerciseRelationCustomMapper;
import com.shev.itembank.paper.entity.Paper;
import com.shev.itembank.paper.entity.PaperCityRelation;
import com.shev.itembank.paper.entity.PaperProvinceRelation;
import com.shev.itembank.paper.entity.PaperSectionExerciseRelation;
import com.shev.itembank.paper.mapper.PaperMapper;

@Service("ESIndexManageServiceRPCImpl")
public class ESIndexManageServiceRPCImpl implements IndexManageService
{

    private static final Logger logger = LoggerFactory.getLogger(ESIndexManageServiceRPCImpl.class);

    @Autowired
    private ExerciseMapper exerciseMapper;

    @Autowired
    private PaperMapper paperMapper;

    public static final String REGION_CITY_TYPE = "CITY";

    public static final String REGION_PROVINCE_TYPE = "PROVINCE";

    public static String NUMBER = "^[-]|^[0-9]*$";

    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String clustNodes;

    @Value("${spring.data.elasticsearch.cluster-name}")
    private String clusterName;

    private TransportClient client;

    @PostConstruct
    public void init()
    {
        try
        {
            String[] clusts = clustNodes.split(":");
            String clustHost = clusts[0];
            Integer clustPort = Integer.valueOf(clusts[1]);
            logger.info("es name:{}, host:{}, post:{}", clusterName, clustHost, clustPort);
            Settings settings = Settings.builder().put("cluster.name", clusterName).put("client.transport.sniff", true).build();
//            client = new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(clustHost), clustPort));
            client = new PreBuiltTransportClient(settings).addTransportAddress(new TransportAddress(InetAddress.getByName(clustHost), clustPort));

            PutIndexTemplateRequest request = new PutIndexTemplateRequest("itembank");
            List<String> indexPatterns = new ArrayList<>();
            indexPatterns.add("exercise*");
            indexPatterns.add("paper*");
            request.patterns(indexPatterns);

            XContentBuilder jsonBuilder = XContentFactory.jsonBuilder()
                    .startObject()
                        .startObject("_source")
                            .field("enabled", false)
                        .endObject()
                        .startObject("properties")
                            .startObject("host_name")
                                .field("type", "keyword")
                            .endObject()
                            .startObject("created_at")
                                .field("type", "date")
                                .field("format", "yyyy-MM-dd HH:mm:ss")
                            .endObject()
                        .endObject()
                    .endObject();
            request.mapping("_doc", jsonBuilder);
            Map<String, Object> templateSettings = new HashMap<>();
            templateSettings.put("number_of_shards", 1);
            templateSettings.put("number_of_replicas", 0);
            request.settings(templateSettings);


            IndicesAdminClient adminClient = client.admin().indices();

            adminClient.putTemplate(request);

            IndicesExistsResponse response = adminClient.exists(new IndicesExistsRequest().indices(new String[] { IndexName.INDEX_EXERCISE_SUBSECTION })).actionGet();
            if (!response.isExists())
                adminClient.prepareCreate(IndexName.INDEX_EXERCISE_SUBSECTION).get();

            response = adminClient.exists(new IndicesExistsRequest().indices(new String[] { IndexName.INDEX_EXERCISE_SP })).actionGet();
            if (!response.isExists())
            {
                adminClient.prepareCreate(IndexName.INDEX_EXERCISE_SP).get();
                adminClient.preparePutMapping(IndexName.INDEX_EXERCISE_SP).setType(IndexName.INDEX_EXERCISE_SP).setSource(XContentFactory.jsonBuilder().startObject().startObject(IndexName.INDEX_EXERCISE_SP).startObject("properties").startObject("content").field("type", "text").field("analyzer",
                        "ik_smart").field("search_analyzer","ik_smart").endObject().startObject("answersOptions").field("type", "text").field("analyzer", "ik_smart").field("search_analyzer","ik_smart").endObject()
                        .startObject("answers").field("type", "text").field("analyzer", "ik_smart").field("search_analyzer","ik_smart").endObject()
                        .startObject("solutions").field("type", "text").field("analyzer", "ik_smart").field("search_analyzer","ik_smart").endObject().endObject().endObject().endObject()).execute().actionGet();
            }

            response = adminClient.exists(new IndicesExistsRequest().indices(new String[] { IndexName.INDEX_PAPER })).actionGet();
            if (!response.isExists())
            {
                adminClient.prepareCreate(IndexName.INDEX_PAPER).get();
                adminClient.preparePutMapping(IndexName.INDEX_PAPER).setType(IndexName.INDEX_PAPER).setSource(XContentFactory.jsonBuilder().startObject().startObject(IndexName.INDEX_PAPER).startObject("properties").startObject("name").field("type", "text").field("analyzer",
                        "ik_smart").field("search_analyzer","ik_smart").endObject().endObject().endObject().endObject()).execute().actionGet();
            }
        } catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void preDestory()
    {
        if (client != null)
        {
            client.close();
            client = null;
        }
    }

    @Override
    public void truncateIndex(String tenantId, String indexName) throws Exception
    {
        if (TextUtil.isEmpty(tenantId))
        {
            IndicesExistsResponse response = client.admin().indices().exists(new IndicesExistsRequest().indices(new String[]{ indexName })).actionGet();
            if (response.isExists())
            {
                client.admin().indices().prepareDelete(indexName).execute().actionGet();
            }
            IndicesAdminClient indicesAdminClient = client.admin().indices();
            indicesAdminClient.prepareCreate(indexName).get();
        } else
        {
            deleteIndexData(tenantId, indexName);
        }
    }

    private void deleteIndexData(String tenantId, String indexName)
    {
        BoolQueryBuilder tenantIdBoolQuery = QueryBuilders.boolQuery().must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("tenantId", tenantId)));
        int count = 0;
        int size = 1000;
        do
        {
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            SearchResponse response = client.prepareSearch(indexName).setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(QueryBuilders.boolQuery().must(tenantIdBoolQuery)).setSize(size).get();
            SearchHit[] hits = response.getHits().getHits();
            count = hits.length;
            for (SearchHit hit : hits)
            {
                String id = hit.getId();
                bulkRequest.add(client.prepareDelete(indexName, indexName, id).request());
            }
            if(count != 0)
                bulkRequest.get();
        }while(count > 0);
    }

    @Override
    public void updateSpExercise(Map<String, Object> map) throws Exception
    {
        String exerciseId = (String) map.get("exerciseId");
        if (TextUtil.isEmpty(exerciseId))
            throw new BusinessException("Invalid.exerciseId", "exerciseId is null or empty");
        BoolQueryBuilder exerciseIdBoolQuery = QueryBuilders.boolQuery().must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("exerciseId", exerciseId)));

        SearchResponse response = client.prepareSearch(IndexName.INDEX_EXERCISE_SP).setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(QueryBuilders.boolQuery().must(exerciseIdBoolQuery)).get();
        if (response.getHits().totalHits > 0)
        {
            for (SearchHit hit : response.getHits().getHits())
            {
                String id = hit.getId();
                client.prepareUpdate(IndexName.INDEX_EXERCISE_SP, IndexName.INDEX_EXERCISE_SP, id).setDoc(map).get();
            }
        } else
            client.prepareIndex(IndexName.INDEX_EXERCISE_SP, IndexName.INDEX_EXERCISE_SP).setSource(map).get();
    }

    @Override
    public void deleteSpExercise(String exerciseId) throws Exception
    {
        if (TextUtil.isEmpty(exerciseId))
            throw new BusinessException("Invalid.exerciseId", "exerciseId is null or empty");

        BoolQueryBuilder userIdBoolQuery = QueryBuilders.boolQuery().must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("exerciseId", exerciseId)));

        SearchResponse response = client.prepareSearch(IndexName.INDEX_EXERCISE_SP).setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(QueryBuilders.boolQuery().must(userIdBoolQuery)).get();

        for (SearchHit hit : response.getHits().getHits())
        {
            String id = hit.getId();
            client.prepareDelete(IndexName.INDEX_EXERCISE_SP, IndexName.INDEX_EXERCISE_SP, id).get();
        }
    }

    @Override
    public void createPaperIndex(Map<String, Object> map) throws Exception
    {
        String paperId = (String) map.get("id");
        if (TextUtil.isEmpty(paperId))
            throw new BusinessException("Invalid.paperId", "paper id is null or empty");

        client.prepareIndex(IndexName.INDEX_PAPER, IndexName.INDEX_PAPER).setSource(map).get();
    }

    @Override
    public void updatePaperIndex(Map<String, Object> map) throws Exception
    {
        String paperId = (String) map.get("id");
        if (TextUtil.isEmpty(paperId))
            throw new BusinessException("Invalid.paperId", "paper id is null or empty");
        BoolQueryBuilder paperIdBoolQuery = QueryBuilders.boolQuery().must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("id", paperId)));

        SearchResponse response = client.prepareSearch(IndexName.INDEX_PAPER).setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(QueryBuilders.boolQuery().must(paperIdBoolQuery)).get();
        if (response.getHits().totalHits > 0)
        {
            for (SearchHit hit : response.getHits().getHits())
            {
                String id = hit.getId();
                client.prepareUpdate(IndexName.INDEX_PAPER, IndexName.INDEX_PAPER, id).setDoc(map).get();
            }
        } else
            client.prepareIndex(IndexName.INDEX_PAPER, IndexName.INDEX_PAPER).setSource(map).get();
    }

    @Override
    public void deletePaperIndex(String paperId) throws Exception
    {
        if (TextUtil.isEmpty(paperId))
            throw new BusinessException("Invalid.paperId", "paper id is null or empty");
        BoolQueryBuilder paperIdBoolQuery = QueryBuilders.boolQuery().must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("id", paperId)));

        SearchResponse response = client.prepareSearch(IndexName.INDEX_PAPER).setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(QueryBuilders.boolQuery().must(paperIdBoolQuery)).get();

        for (SearchHit hit : response.getHits().getHits())
        {
            String id = hit.getId();
            client.prepareDelete(IndexName.INDEX_PAPER, IndexName.INDEX_PAPER, id).get();
        }
    }

    @Override
    public void updateExerciseES(Exercise exercise, Boolean isPublic, String tenantId) throws Exception
    {
        if (exercise == null)
            return;
        Map<String, Object> map = new HashMap<String, Object>();
        String exerciseId = exercise.getId();
        map.put("exerciseId", exerciseId);
        updateSpExercise(map);
    }

    @Override
    public void updatePaperES(Paper paper, Boolean isPublic, String tenantId) throws Exception
    {
        Map<String, Object> map = new HashMap<>();
        map.put("id", paper.getId());
        updatePaperIndex(map);
    }

}
