package org.manjunath.voterapi.response.customization;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomPageImpl<T> extends PageImpl<T> {


    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CustomPageImpl(@JsonProperty("content") final List<T> content,
                          @JsonProperty("number") int number,
                          @JsonProperty("totalElements") final long totalElements,
                          @JsonProperty("pageable")JsonNode pageable,
                          @JsonProperty("last") boolean last,
                          @JsonProperty("totalPages") final int totalPages,
                          @JsonProperty("page") final int page,
                          @JsonProperty("size") final int size,
                          @JsonProperty("sort") final JsonNode sort,
                          @JsonProperty("first") boolean first,
                          @JsonProperty("numberOfElements") int numberOfElements) {
        super(content, PageRequest.of(number, size), totalElements);
    }

    public CustomPageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public CustomPageImpl(List<T> content) {
        super(content);
    }

    public CustomPageImpl() {
        super(new ArrayList<>());
    }

    @JsonProperty("sort")
    public List<String> getSorList() {
        return getSort().stream()
                .map(order -> order.getProperty()+","+order.getDirection().name())
                .collect(Collectors.toList());
    }
}
