
package Model;
import Model.iso0100;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface M0100repo extends MongoRepository<iso0100, String> {



}