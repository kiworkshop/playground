package playground.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import playground.domain.DocumentDto;

import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.ok;

@RestController(value = "api")
@EnableAutoConfiguration
public class DocumentController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/documents/{documentId}", method = RequestMethod.GET)
    public ResponseEntity<DocumentDto> getDocument(@PathVariable String documentId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Transfer-Encoding", "chunked");
            headers.setDate(ZonedDateTime.now());
            headers.add("keep-Alive", "timeout=60");
            headers.add("Connection", "keep-Alive");

            DocumentDto document = jdbcTemplate.queryForObject("SELECT * FROM DOCUMENT WHERE ID = ?", String.class, documentId);

            return ResponseEntity<DocumentDto>.ok()
                    .headers(headers)
                    .body(document);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity<DocumentDto>.internalServerError()
                    .body(null);
        }
    }

    @RequestMapping(value = "/documents/outbox", method = RequestMethod.GET)
    public ResponseEntity<List<DocumentDto>> getOutbox(@RequestParam String drafterId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Transfer-Encoding", "chunked");
            headers.setDate(ZonedDateTime.now());
            headers.add("keep-Alive", "timeout=60");
            headers.add("Connection", "keep-Alive");

            List<DocumentDto> outbox = jdbcTemplate.queryForList("SELECT * FROM DOCUMENT WHERE ID = ? WHERE APPROVALSTATE = 'DRAFTING'", String.class,drafterId);

            return ResponseEntity<List<DocumentDto>>.ok()
                    .headers(headers)
                    .body(outbox);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity<List<DocumentDto>>.internalServerError()
                    .body(null);
        }
    }




}
