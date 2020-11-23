package com.tutorial.repositories;


import com.tutorial.domains.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DocumentRepository {

    public static final String MAIN_KEY = "Document";
    @Autowired
    private RedisTemplate redisTemplate;

    /**Saves document in Redis
     * Parameter: Document
     * Return: Saved Document
     */
    public Document save(Document doc){
        redisTemplate.opsForHash().put(MAIN_KEY,doc.getId(),doc);
        return doc;
    }


    /**Find all documents
     * Return: List of Documents saved in Redis
     */
    public List<Object> findAll(){
        return redisTemplate.opsForHash().values(MAIN_KEY);
    }


    /**Find specific document by ID*/
    public Document findDocumentById(int id){
        System.out.println("DB called ****");
        return (Document) redisTemplate.opsForHash().get(MAIN_KEY,id);
    }


    /**Delete specific document by ID
     * Parameter: int id
     * Return: if correctly deleted return String "Deleted" if not return String "Error Message"
     */
    public String deleteDocumentById(int id){
        try{
            redisTemplate.opsForHash().delete(MAIN_KEY,id);
            return "Deleted";
        } catch (Error e){
            return e.getMessage();
        }
    }


}
