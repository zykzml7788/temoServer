package com.creams.temo.mapper;

import com.creams.temo.entity.ExecutedRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

/**
 * @author ronin
 */
@Component
public class MongoMapper {

    @Autowired
    private MongoTemplate mongoTemplate;
    /**
     * 创建对象
     */
    public void save(ExecutedRow executedRow) {
        mongoTemplate.save(executedRow);
    }

    /**
     * 根据用例id查询对象
     * @return
     */
    public ExecutedRow findByCaseId(String caseId) {
        Query query=new Query(Criteria.where("caseId").is(caseId));
        ExecutedRow executedRow =  mongoTemplate.findOne(query, ExecutedRow.class);
        return executedRow;
    }

    /**
     * 更新对象
     */
    public void update(ExecutedRow executedRow) {
        Query query=new Query(Criteria.where("caseId").is(executedRow.getCaseId()));
        Update update= new Update()
                .set("logs",executedRow.getLogs())
                .set("status", executedRow.getStatus())
                .set("caseName", executedRow.getCaseName())
                .set("index", executedRow.getIndex());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,ExecutedRow.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,ExecutedRow.class);
    }

    /**
     * 删除对象
     * @param caseId
     */
    public void deleteByCaseId(String caseId) {
        Query query=new Query(Criteria.where("caseId").is(caseId));
        mongoTemplate.remove(query,ExecutedRow.class);
    }
}


