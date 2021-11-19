package playground.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import playground.domain.Category;
import playground.domain.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DocumentRequest {

    private String title;
    private String category;
    private String contents;
    private long drafterId;
    private List<Long> approverIds;


}
