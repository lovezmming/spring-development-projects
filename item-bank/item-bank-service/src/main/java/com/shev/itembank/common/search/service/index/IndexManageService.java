package com.shev.itembank.common.search.service.index;

import com.shev.itembank.exercise.entity.Exercise;
import com.shev.itembank.paper.entity.Paper;

import java.util.List;
import java.util.Map;

public interface IndexManageService
{
    /**
     * indexing TextbookStructureCategoryExercise 
     * @param tenantId  optional . default 000001. 
     * @param textbookStructureCategoryId required.
     * @param textbookId required
     * @param textbookVersionId optional
     * @return
     */
    public Object IndexingTextbookStructureCategoryExercise(String tenantId, String textbookStructureCategoryId, String textbookId, String textbookVersionId) throws Exception;

    /**
     * indexing TextbookStructureCategoryExercise 
     * @param list required.
     * @return
     */
    public Object IndexingTextbookStructureCategoryExercise(List<Object> list) throws Exception;
    
    /**
     * indexing kp's exercise 
     * @param list required.
     * @return
     */
    public Object IndexingKpExercise(List<Object> list) throws Exception;

    /**
     * delete  data of the specified index
     * @param tenantId indexName required ..
     * @return
     */
    public void truncateIndex(String tenantId, String indexName) throws Exception;
    
    /**
     * update or create exercise index at es
     * @param map exercise property map .required .
     * @return
     */
    public void updateSpExercise(Map<String, Object> map) throws Exception;

    public void updateExercise(Map<String, Object> map) throws Exception;

    /**
     * @param exerciseId
     * @throws Exception
     */
    public void deleteSpExercise(String exerciseId)  throws Exception;

    /**
     * createPaperIndex
     * @param map
     * @throws Exception
     */
    public void createPaperIndex(Map<String, Object> map) throws Exception;
   
    /**
     * updatePaperIndex
     * @param map
     * @throws Exception
     */
    public void updatePaperIndex(Map<String, Object> map) throws Exception;
  
    /**
     * deletePaperIndex
     * @param paperId
     * @throws Exception
     */
    public void deletePaperIndex(String paperId) throws Exception;

    /**
     * updateExercise
     * @param exercise
     * @param isPublic
     * @param tenantId
     * @throws Exception
     */
    public void updateExerciseES(Exercise exercise, Boolean isPublic, String tenantId) throws Exception;

    public Map<String, Object> getRegions(String exerciseId, String regionType);

    public void updatePaperES(Paper paper, Boolean isPublic, String tenantId) throws Exception;
}
