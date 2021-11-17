package playground.domain.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query(value = "select d from Document d " +
            "inner join DocumentApproval a " +
            "on d.id = a.document.id " +
            "where a.approver.id = :userId order by a.insertDate asc")
    List<Document> findOutBox(@Param(value = "userId") Long userId);
}
