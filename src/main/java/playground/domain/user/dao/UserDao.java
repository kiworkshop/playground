package playground.domain.user.dao;

import playground.domain.document.entity.Document;
import playground.domain.user.entity.User;

public interface UserDao {

    User findDrafterOf(Document document);
}
