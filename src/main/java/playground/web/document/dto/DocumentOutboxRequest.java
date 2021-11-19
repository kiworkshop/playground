package playground.web.document.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentOutboxRequest {

    private Long drafterId; //TODO: 2021/11/19 extract userId to loginMember

}
