package com.shev.itembank.common.search.task;

import com.shev.itembank.common.search.service.index.IndexManageService;
import com.shev.itembank.paper.custom.PaperCustomMapper;
import com.shev.itembank.paper.entity.Paper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class IndexingDeletePaperRelatedTask implements Callable<String>
{
    private static final Logger logger = LoggerFactory.getLogger(IndexingDeletePaperRelatedTask.class);

    private IndexManageService indexManageService;

    private PaperCustomMapper paperCustomMapper;

    private Date dateFrom;

    private Date dateThru;

    public IndexingDeletePaperRelatedTask(PaperCustomMapper paperCustomMapper, IndexManageService indexManageService, Date dateFrom, Date dateThru)
    {
        this.paperCustomMapper = paperCustomMapper;
        this.indexManageService = indexManageService;
        this.dateFrom = dateFrom;
        this.dateThru = dateThru;
    }

    @Override
    public String call() throws Exception
    {
        try
        {
            logger.info("dateFrom:" + dateFrom);
            Map<String, Object> params = new HashMap<>();
            if (dateFrom != null)
            {
                params.put("dateFrom", dateFrom);
                params.put("dateThru", dateThru);
            } else
            {
                params.put("dateFrom", null);
                params.put("dateThru", null);
            }
            List<Paper> papers = paperCustomMapper.selectByParameter(params);
            logger.info("paper totalCount:" + papers.size());
            for (Paper paper : papers)
            {
                indexManageService.deletePaperIndex(paper.getId());
            }
            logger.info("delete paper realtime indexing task submited.");
        } catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}
