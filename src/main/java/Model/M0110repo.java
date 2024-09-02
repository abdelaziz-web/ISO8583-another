package Model;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface M0110repo extends MongoRepository<iso0110, String> {
    @Override
    iso0110 save(iso0110 entity);
}