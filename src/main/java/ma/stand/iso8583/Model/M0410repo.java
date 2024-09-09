package ma.stand.iso8583.Model;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface M0410repo extends MongoRepository<iso0410, String> {
    @Override
    iso0410 save(iso0410 entity);

}