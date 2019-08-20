package com.shev.compilation.user.service;

import com.shev.compilation.common.support.web.RecordSet;
import com.shev.compilation.user.request.*;

import java.util.Map;

public interface DutyService
{

    RecordSet getDuties(DutyGetRequest dutyGetRequest);

    RecordSet getDutyDetail(DutyDetailGetRequest dutyDetailGetRequest);

    Map<String, Object> updateDuty(DutyUpdateRequest dutyUpdateRequest);

    Map<String, Object> createDuty(DutyCreateRequest dutyCreateRequest);

    void deleteDuty(DutyDeleteRequest dutyDeleteRequest);
}


