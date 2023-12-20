package com.example.tastifybackend.domain.templates;

import java.util.List;

public interface GenericCRUD <T, R>{
    R save(T input);
    List<R> saveAll(List<T> inputs);
    R findById(String id);
    List<R> findAll();
    void delete(String id);
    void deleteAll(List<String> ids);
}
