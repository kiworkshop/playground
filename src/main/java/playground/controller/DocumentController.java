package playground.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import playground.domain.ApproveGenerationRequestDto;
import playground.domain.DocumentDto;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
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

            DocumentDto document = jdbcTemplate.queryForObject("SELECT * FROM DOCUMENT WHERE ID = ?", DocumentDto.class, documentId);

            return new ResponseEntity<>(document, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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

            List<DocumentDto> outbox = jdbcTemplate.queryForList("SELECT * FROM DOCUMENT WHERE ID = ? WHERE APPROVALSTATE = 'DRAFTING'", DocumentDto.class, drafterId);

            return new ResponseEntity<>(outbox, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "documents", method = RequestMethod.POST)
    public void approveGenerationRequest(@RequestBody ApproveGenerationRequestDto approveGenerationRequest) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String title = approveGenerationRequest.getTitle();
            String category = approveGenerationRequest.getCategory();
            String contents = approveGenerationRequest.getContents();
            int drafterId = approveGenerationRequest.getDrafterId();

            jdbcTemplate.update("INSERT INTO DOCUMENT values");
        } catch (Exception e) {

        }
    }

}
