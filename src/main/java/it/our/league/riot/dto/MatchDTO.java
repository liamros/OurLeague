
package it.our.league.riot.dto;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "match")
public class MatchDTO {

    private Metadata metadata;
    private Info info;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}
