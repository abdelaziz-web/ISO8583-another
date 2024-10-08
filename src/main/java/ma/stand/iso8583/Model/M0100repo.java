
package ma.stand.iso8583.Model;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface M0100repo extends MongoRepository<iso0100, String> {

    @Override
    iso0100 save(iso0100 entity);

    // Find by PAN (Primary Account Number)
    Optional<iso0100> findById(String pan);
}