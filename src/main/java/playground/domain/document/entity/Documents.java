package playground.domain.document.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class Documents {

    private final List<Document> elements;

    public Documents(List<Document> elements) {
        this.elements = Collections.unmodifiableList(sort(elements));
    }

    private List<Document> sort(List<Document> documents) {
        List<Document> elements = new ArrayList<>(documents);
        elements.sort(Comparator.comparing(Document::getCreatedAt).reversed());
        return elements;
    }

    public Stream<Document> stream() {
        return elements.stream();
    }
}
