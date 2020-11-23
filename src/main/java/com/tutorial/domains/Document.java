package com.tutorial.domains;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Indexed;

import java.io.Serializable;


@Data //getters and setters all set from lombok
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Document")
public class Document implements Serializable{

    //All the properties a document should have or the most important that were extracted from the API response
    //this is information that is going to be cached
    @Id
    private int id;

    private String caseName;
    private String caseUrlToPdf;
    private String wordsQuery;



}
