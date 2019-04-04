package com.ttn.linksharing.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class DocumentResource extends Resource implements Serializable {
    @Column(name = "document_path")
    private String documentPath;

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
}
