package playground.document;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import playground.document.dao.DocumentDao;
import playground.document.dto.DocumentDto;
import playground.document.entity.Document;
import playground.user.dao.UserDao;
import playground.user.entity.User;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentDao documentDao;
    private final UserDao userDao;

    @Override
    public DocumentDto findById(Long id) {
        try {
            // dao를 분리해서 각각 찾아오기
            Document document = documentDao.findById(id);
            User drafter = userDao.findDrafterOf(document);
            return DocumentDto.of(document, drafter);
            // 테이블 조인해서 한 번에 찾아오기 -> service 단에서 sql을 관리해야 해서 별로임
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new NoSuchElementException("id에 맞는 document가 없음");
        }
    }
}
