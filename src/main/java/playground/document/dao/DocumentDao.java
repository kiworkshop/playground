package playground.document.dao;

import playground.document.entity.Document;

public interface DocumentDao {

    Document findById(Long id);
}
