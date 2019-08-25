package com.example.demo.generator.service;

import java.util.List;
import java.util.Map;

public interface IGeneratorService {

	List<Map<String,String>> selectColumnsByTableName(String tableName);
}
