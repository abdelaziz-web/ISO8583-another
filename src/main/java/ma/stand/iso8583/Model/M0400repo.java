
package ma.stand.iso8583.Model;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface M0400repo extends MongoRepository<iso0400, String> {

    @Override
    iso0400 save(iso0400 entity);

    // Find by PAN (Primary Account Number)
    Optional<iso0400> findById(String id);
}