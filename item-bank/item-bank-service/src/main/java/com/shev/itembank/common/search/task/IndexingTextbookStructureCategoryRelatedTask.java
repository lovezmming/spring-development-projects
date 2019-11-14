package com.shev.itembank.common.search.task;

import com.shev.itembank.common.Enum.ConstantEnum;
import com.shev.itembank.common.search.service.index.IndexManageService;
import com.shev.itembank.edumeta.custom.TextbookStructureCategoryCustomMapper;
import com.shev.itembank.edumeta.entity.TextbookStructureCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class IndexingTextbookStructureCategoryRelatedTask implements Callable<String>
{

    private static final Logger logger = LoggerFactory.getLogger(IndexingTextbookStructureCategoryRelatedTask.class);
            
    private IndexManageService indexManageService;
    
    private TextbookStructureCategoryCustomMapper textbookStructureCategoryCustomMapper;
    
    private Date date;
    
    private String tenantId;    
    
    public IndexingTextbookStructureCategoryRelatedTask(String tenantId, IndexManageService indexManageService, TextbookStructureCategoryCustomMapper textbookStructureCategoryCustomMapper, Date date)
    {
        this.indexManageService = indexManageService;
        this.textbookStructureCategoryCustomMapper = textbookStructureCategoryCustomMapper;
        this.date = date;
        this.tenantId = tenantId;
    }

    /**
     * @see Callable#call()
     */
    @Override
    public String call() throws Exception
    {        
        logger.info("type:JC  date："+date+" indexing task begin.");

        Map<String, Object> params = new HashMap<>();
        params.put("updateTime", date);
        params.put("type", ConstantEnum.SUBSECTION.getValue());
        List<TextbookStructureCategory> textbookStructureCategories = textbookStructureCategoryCustomMapper.selectByParameter(params);
        for (TextbookStructureCategory textbookStructureCategory : textbookStructureCategories)
        {
            String textbookStructureCategoryId = textbookStructureCategory.getId();
            String textbookId = textbookStructureCategory.getTextbookId();
            indexManageService.IndexingTextbookStructureCategoryExercise(tenantId, textbookStructureCategoryId, textbookId, null);
        }
        
        logger.info("type:JC  date："+date+" indexing task end.");
        return null;
    }

}
