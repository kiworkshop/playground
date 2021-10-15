package playground.user.dao;

import playground.document.entity.Document;
import playground.user.entity.User;

public interface UserDao {

    User findDrafterOf(Document document);
}
