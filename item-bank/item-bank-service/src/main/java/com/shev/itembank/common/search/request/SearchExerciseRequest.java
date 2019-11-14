package com.shev.itembank.common.search.request;

import com.shev.itembank.common.base.util.TextUtil;
import com.shev.itembank.common.base.web.HttpServletBasicRequest;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchExerciseRequest extends HttpServletBasicRequest
{

    /** serialVersionUID */
    private static final long serialVersionUID = -994547384211951830L;

    private String type ;
    
    private String referenceId;
    
    private String formType;
    
    private String difficulty;
    
    private String significance;
    
    private String strategy;
    
    private String text;
    
    private String kp;
    
    private String section;

    private String ability;
    
    private String semanticType;
    
    private String subject;
    
    private String educationalStageId;
    
    private String region;
    
    private String year;
    
    private String completeness;
    
    private String errorMeta;
    
    private String exerciseIds;   
    
    private String searchType;

    private String paramKey;

    private String proficiencyId;

    private Boolean searchChildExerciseFlag;

    private Long updateTimeStart;

    private Long updateTimeEnd;

    private String sortType;

    private String sortOrder;

    public List<Integer> getFormTypeList()
    {
        List<Integer> list = new ArrayList<Integer>();
        if(!TextUtil.isEmpty(formType))
        {
            String[] values = formType.trim().split(",");
            for(String value : values)
            {
                if(!TextUtil.isEmpty(value))
                    list.add(Integer.valueOf(value.trim()));
            }
        }
        return list;        
    }

    public List<Integer> getDifficultyList()
    {
        List<Integer> list = new ArrayList<Integer>();
        if(!TextUtil.isEmpty(difficulty))
        {
            String[] values = difficulty.trim().split(",");
            for(String value : values)
            {
                if(!TextUtil.isEmpty(value))
                    list.add(Integer.valueOf(value.trim()));
            }
        }
        return list;        
    }

    public List<Integer> getSemanticTypeList()
    {
        List<Integer> list = new ArrayList<Integer>();
        if(!TextUtil.isEmpty(semanticType))
        {
            String[] values = semanticType.trim().split(",");
            for(String value : values)
            {
                if(!TextUtil.isEmpty(value))
                    list.add(Integer.valueOf(value.trim()));
            }
        }
        return list;        
    }

    public List<Integer> getSubjectList()
    {
        List<Integer> list = new ArrayList<Integer>();
        if(!TextUtil.isEmpty(subject))
        {
            String[] values = subject.trim().split(",");
            for(String value : values)
            {
                if(!TextUtil.isEmpty(value))
                    list.add(Integer.valueOf(value.trim()));
            }
        }
        return list;        
    }

    public List<Integer> getSignificanceList()
    {
        List<Integer> list = new ArrayList<Integer>();
        if(!TextUtil.isEmpty(significance))
        {
            String[] values = significance.trim().split(",");
            for(String value : values)
            {
                if(!TextUtil.isEmpty(value))
                    list.add(Integer.valueOf(value.trim()));
            }
        }
        return list;        
    }

    public List<Integer> getEducationalStageIdList()
    {
        List<Integer> list = new ArrayList<Integer>();
        if(!TextUtil.isEmpty(educationalStageId))
        {
            String[] values = educationalStageId.trim().split(",");
            for(String value : values)
            {
                if(!TextUtil.isEmpty(value))
                    list.add(Integer.valueOf(value.trim()));
            }
        }
        return list;        
    }

    public List<Integer> getYearList()
    {
        List<Integer> list = new ArrayList<Integer>();
        if(!TextUtil.isEmpty(year))
        {
            String[] values = year.trim().split(",");
            for(String value : values)
            {
                if(!TextUtil.isEmpty(value))
                    list.add(Integer.valueOf(value.trim()));
            }
        }
        return list;        
    }
    
    public List<Integer> getCompletenessList()
    {
        List<Integer> list = new ArrayList<Integer>();
        if(!TextUtil.isEmpty(completeness))
        {
            String[] values = completeness.trim().split(",");
            for(String value : values)
            {
                if(!TextUtil.isEmpty(value))
                    list.add(Integer.valueOf(value.trim()));
            }
        }
        return list;        
    }
    
    public List<Integer> getErrorMetaList()
    {
        List<Integer> list = new ArrayList<Integer>();
        if(!TextUtil.isEmpty(errorMeta))
        {
            String[] values = errorMeta.trim().split(",");
            for(String value : values)
            {
                if(!TextUtil.isEmpty(value))
                    list.add(Integer.valueOf(value.trim()));
            }
        }
        return list;        
    }
    
    public List<String> getKpList()
    {
        List<String> list = new ArrayList<String>();
        if(!TextUtil.isEmpty(kp))
        {
            String[] values = kp.trim().split(",");
            for(String value : values)
            {
                if(!TextUtil.isEmpty(value))
                    list.add(value.trim());
            }
        }
        return list;
    }

    public List<String> getSectionList()
    {
        List<String> list = new ArrayList<String>();
        if(!TextUtil.isEmpty(section))
        {
            String[] values = section.trim().split(",");
            for(String value : values)
            {
                if(!TextUtil.isEmpty(value))
                    list.add(value.trim());
            }
        }
        return list;
    }

    public List<String> getProficiencyIdList()
    {
        List<String> list = new ArrayList<String>();
        if(!TextUtil.isEmpty(proficiencyId))
        {
            String[] values = proficiencyId.trim().split(",");
            for(String value : values)
            {
                if(!TextUtil.isEmpty(value))
                    list.add(value.trim());
            }
        }
        return list;
    }

    public List<String> getParamKeyList()
    {
        List<String> list = new ArrayList<String>();
        if(!TextUtil.isEmpty(paramKey))
        {
            String[] values = paramKey.trim().split(",");
            for(String value : values)
            {
                if(!TextUtil.isEmpty(value))
                    list.add(value.trim());
            }
        }
        return list;
    }

    public List<String> getAbilityList()
    {
        List<String> list = new ArrayList<String>();
        if(!TextUtil.isEmpty(ability))
        {
            String[] values = ability.trim().split(",");
            for(String value : values)
            {
                if(!TextUtil.isEmpty(value))
                    list.add(value.trim());
            }
        }
        return list;
    }
    
    public List<String> getRegionList()
    {
        List<String> list = new ArrayList<String>();
        if(!TextUtil.isEmpty(region))
        {
            String[] values = region.trim().split(",");
            for(String value : values)
            {
                if(!TextUtil.isEmpty(value))
                    list.add(value.trim());
            }
        }
        return list;
    }
    
    public List<String> getExerciseIdsList()
    {
        List<String> list = new ArrayList<String>();
        if(!TextUtil.isEmpty(exerciseIds))
        {
            String[] values = exerciseIds.trim().split(",");
            for(String value : values)
            {
                if(!TextUtil.isEmpty(value))
                    list.add(value.trim());
            }
        }
        return list;
    }

}
