package com.shev.itembank.common.search.service.search.impl;

import com.shev.itembank.common.base.result.RecordSet;
import com.shev.itembank.common.base.util.TextUtil;
import com.shev.itembank.common.elasticsearch.index.IndexName;
import com.shev.itembank.common.search.service.search.SearchManageService;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.util.*;

@Service("ESManageServiceRPCImpl")
public class ElasticSearchManageServiceRPCImpl implements SearchManageService
{
    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchManageServiceRPCImpl.class);

    public static String HIGHLIGHT_START_TAGS = "START#&#";

    public static String HIGHLIGHT_END_TAGS = "END&#&";

    private static String SORT_TYPE_CREATETIME = "C";

    private static String SORT_TYPE_UPDATETIME = "U";

    private static String SORT_ORDER_ASC = "ASC";

    private static String SORT_ORDER_DESC = "DESC";

    private static String INDEX_EXERCISE = "exercise";

    private static String INDEX_EXERCISE_SUBSECTION = "exercise_subsection";

    private static String INDEX_EXERCISE_SP = "exercise_sp";

    private static String INDEX_PAPER = "paper";

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
            IndicesExistsResponse response = client.admin().indices().exists(new IndicesExistsRequest().indices(new String[] { IndexName.INDEX_EXERCISE })).actionGet();
            if (response.isExists())
            {
                client.admin().indices().prepareUpdateSettings(IndexName.INDEX_EXERCISE).setSettings(Settings.builder().put("index.max_result_window", 50000000)).get();
            }

            response = client.admin().indices().exists(new IndicesExistsRequest().indices(new String[]{ IndexName.INDEX_EXERCISE_SP })).actionGet();
            if (response.isExists())
            {
                client.admin().indices().prepareUpdateSettings(IndexName.INDEX_EXERCISE_SP).setSettings(Settings.builder().put("index.max_result_window", 1000000)).get();
            }

            response = client.admin().indices().exists(new IndicesExistsRequest().indices(new String[]{ IndexName.INDEX_PAPER })).actionGet();
            if (response.isExists())
            {
                client.admin().indices().prepareUpdateSettings(IndexName.INDEX_PAPER).setSettings(Settings.builder().put("index.max_result_window", 10000000)).get();
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
    public RecordSet searchExerciseByKeyword(Boolean isPublic, String tenantId, String knowledgePointId, List<Integer> formType, List<Integer> difficulty, List<Integer> significance, Integer subjectId, String categoryId, String paramKey, String proficiencyId, Integer start, Integer max)
    {
        BoolQueryBuilder paramKeyBoolQuery = QueryBuilders.boolQuery();
        if (!TextUtil.isEmpty(paramKey))
        {
            MatchQueryBuilder contentBoolQuery = QueryBuilders.matchQuery("content", paramKey);
            MatchQueryBuilder answerOptionsBoolQuery = QueryBuilders.matchQuery("answerOptions", paramKey);
            MatchQueryBuilder sectionsBoolQuery = QueryBuilders.matchQuery("sections", paramKey);
            paramKeyBoolQuery.should(contentBoolQuery).should(answerOptionsBoolQuery).should(sectionsBoolQuery);
        }
        BoolQueryBuilder proficiencyBoolQuery = QueryBuilders.boolQuery();
        if (!TextUtil.isEmpty(proficiencyId))
        {
            proficiencyBoolQuery.should(QueryBuilders.matchQuery("sections", proficiencyId));
        }

        HighlightBuilder hiBuilder = new HighlightBuilder();
        hiBuilder.preTags(HIGHLIGHT_START_TAGS);
        hiBuilder.postTags(HIGHLIGHT_END_TAGS);
        hiBuilder.field("content");
        hiBuilder.field("answerOptions");
        hiBuilder.fragmentSize(1024);
        hiBuilder.numOfFragments(5);

        SearchResponse response = client.prepareSearch(INDEX_EXERCISE_SP).setTypes(IndexName.INDEX_EXERCISE_SP).setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(QueryBuilders.boolQuery().must(knowledgePointBoolQuery).must(typeBoolQuery).must(hardBoolQuery).must(subTypeBoolQuery).must(
                subjectBoolQuery).must(sectionBoolQuery).must(paramKeyBoolQuery).must(proficiencyBoolQuery).must(tenantIdBoolQuery).must(isPublicBoolQuery)).highlighter(hiBuilder).setFrom(start).setSize(max).get();

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : response.getHits().getHits())
        {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            result.add(resultMap);
        }
        int total = response.getHits().totalHits >= Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) response.getHits().totalHits;
        return new RecordSet(start, max, total, result.toArray(new Object[0]));
    }

    private StringBuilder getHighLight(SearchHit hit, String field)
    {
        HighlightField hiField = hit.getHighlightFields().get(field);
        if (hiField != null)
        {
            Text[] contentFragments = hiField.getFragments();
            StringBuilder contentBuilder = new StringBuilder();
            for (Text text : contentFragments)
            {
                contentBuilder.append(text);
            }
            return contentBuilder;
        }
        return null;
    }

    @Override
    public RecordSet searchPapers(String tenantId, Boolean isPublic, String queryText, Integer examTypeId, Integer subject, Integer educationalStage, Integer year, String cityId, String provinceId, Boolean isSchool, Boolean isContest, Integer gradeId, Integer start, Integer max) throws Exception
    {
        BoolQueryBuilder textNameBoolQuery = QueryBuilders.boolQuery();
        if (!TextUtil.isEmpty(queryText))
        {
            if (queryText.matches("^[A-Za-z0-9]+$"))
            {
                textNameBoolQuery.should(QueryBuilders.wildcardQuery("name", ("*" + queryText + "*").toLowerCase()));
            } else
            {
                textNameBoolQuery.should(QueryBuilders.matchPhraseQuery("name", queryText));
            }
        }
        SearchResponse response = client.prepareSearch(INDEX_PAPER).setTypes(IndexName.INDEX_PAPER).setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(QueryBuilders.boolQuery()/*.must(tenantIdBoolQuery).must(isPublicBoolQuery)*/.must(textNameBoolQuery).must(examTypeIdBoolQuery).must(
                subjectBoolQuery).must(educationalStageBoolQuery).must(yearBoolQuery).must(cityIdBoolQuery).must(provinceIdBoolQuery).must(isSchoolBoolQuery).must(isContestBoolQuery).must(gradeIdBoolQuery)).setFrom(start).setSize(max).get();
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : response.getHits().getHits())
        {
            Map<String, Object> resultMap = hit.getSourceAsMap();
            result.add(resultMap);
        }
        int total = response.getHits().totalHits >= Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) response.getHits().totalHits;
        return new RecordSet(start, max, total, result.toArray());
    }

}
