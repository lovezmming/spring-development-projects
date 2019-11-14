package com.shev.itembank.latex.service;

import java.util.Map;

public interface LatexService
{
    public Map<String, Object> latex2Pic(String tenantId, int type, String xmlContent) throws Exception;
}
