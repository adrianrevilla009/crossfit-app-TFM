package adrian.tfm.crossfit.documents.repository;

import adrian.tfm.crossfit.documents.model.Document;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends MongoRepository<Document, String> {
    List<Document> findAll();

    @Query("{'user.nif': ?0}")
    List<Document> findByUser(String nif);
}
