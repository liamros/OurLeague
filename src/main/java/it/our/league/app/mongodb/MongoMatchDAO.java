package it.our.league.app.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.ReplaceRootOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import it.our.league.riot.dto.Participant;

public class MongoMatchDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Participant> findParticipantsByPuuid(String puuid) {

        MatchOperation filterOp = Aggregation.match(new Criteria("info.participants.puuid").is(puuid));
        ReplaceRootOperation rootOp = Aggregation.replaceRoot("$info");
        UnwindOperation unwindOp = Aggregation.unwind("$participants");
        MatchOperation filterOp2 = Aggregation.match(new Criteria("participants.puuid").is(puuid));
        ReplaceRootOperation rootOp2 = Aggregation.replaceRoot("$participants");
        ProjectionOperation projectionOp = Aggregation.project().andExclude("perks");
        

        Aggregation aggregation = Aggregation.newAggregation(filterOp, rootOp, unwindOp, filterOp2, rootOp2, projectionOp);
        AggregationResults<Participant> results = mongoTemplate.aggregate(aggregation, "match", Participant.class);
        return results.getMappedResults();
    }

}
