package com.shev.itembank.paper.service;

import com.shev.itembank.common.base.result.RecordSet;
import com.shev.itembank.paper.request.GetPaperDetailRequest;
import com.shev.itembank.paper.request.GetPaperListRequest;

public interface PaperBusinessManageService
{

    /**
     * getPaperList
     * 
     * @param request
     *            required
     * @return
     * @throws Exception
     */
    public RecordSet getPaperList(GetPaperListRequest request) throws Exception;

    /**
     * getPaperDetail
     * 
     * @param request
     *            required
     * @return
     * @throws Exception
     */
    public RecordSet getPaperDetail(GetPaperDetailRequest request) throws Exception;

}
