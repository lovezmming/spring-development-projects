package com.shev.itembank.latex.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LatexRequest implements Serializable
{
    private static final long serialVersionUID = 1409483993948701553L;

    private String tenantId;

    private int type;

    private String xmlContent;
}
