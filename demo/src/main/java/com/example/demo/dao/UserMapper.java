package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
	public List<Map<String, Object>> getUserList();
}
