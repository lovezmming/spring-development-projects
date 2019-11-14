package com.shev.itembank.common.Enum;

public enum ExerciseRelationEnum
{

    EXERCISE_RELATION_SIMILAR("1", "SIMILAR"),

    EXERCISE_RELATION_SIMILAR_TO("2", "SIMILAR_TO"),

    EXERCISE_RELATION_CHILD_EXERCISE("3", "CHILD_EXERCISE"),

    EXERCISE_RELATION_RELEVANCY_STRONG("4", "STRONG_RELEVANCY"),

    EXERCISE_RELATION_RELEVANCY_WEAK("5", "WEAK_RELEVANCY");

    private String key;

    private String value;

    public String getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }

    ExerciseRelationEnum(String key, String value)
    {
        this.key = key;
        this.value = value;
    }

}
