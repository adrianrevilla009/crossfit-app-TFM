package adrian.tfm.crossfit.documents.repository;

import adrian.tfm.crossfit.documents.model.Document;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends MongoRepository<Document, String> {
    List<Document> findAll();

    // TODO ponerse con esto
    // @Aggregation(pipeline = {"{$group: {\"_id\" : \"$CA\", \"numProvincias\": {$sum:1}}}"})
    List<Document> findByUser(String id);
}
