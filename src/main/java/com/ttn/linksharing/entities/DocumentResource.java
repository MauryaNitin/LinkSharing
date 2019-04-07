package com.ttn.linksharing.entities;

import com.ttn.linksharing.CO.DocumentResourceCO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class DocumentResource extends Resource implements Serializable {
    @Column(name = "document_path")
    private String documentPath;

    public DocumentResource(DocumentResourceCO documentResourceCO, Topic topic){
        this.documentPath = documentResourceCO.getDocument().getOriginalFilename();
        this.setDescription(documentResourceCO.getDescription());
        this.setTopic(topic);
    }

    public DocumentResource(){}

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
}
