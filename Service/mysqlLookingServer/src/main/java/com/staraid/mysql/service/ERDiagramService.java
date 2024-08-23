package com.staraid.mysql.service;

import com.staraid.mysql.utils.Result;

import java.sql.SQLException;
import java.util.Map;

public interface ERDiagramService {
    // 获取E-R模型关系
    Result getTableDiagram(Long dbId,String dbName);
}
