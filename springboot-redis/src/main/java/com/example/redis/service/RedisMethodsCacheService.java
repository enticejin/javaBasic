package com.example.redis.service;

public interface RedisMethodsCacheService {
	String getData();
	String getData(String parameter);
	String saveData(String parameter);
	String putData(String parameter);
	String deleteData(String parameter);
	String deleteAllData(String parameter);
	
}
