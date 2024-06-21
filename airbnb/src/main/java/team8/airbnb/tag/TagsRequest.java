package team8.airbnb.tag;

import java.util.List;

public record TagsRequest(
    List<String> tagsName
) {

}
