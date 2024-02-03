package com.Sai.Image.FileStoreInDb.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "FilesUpload")
public class FilesUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "file", columnDefinition = "BLOB")
    private byte[] file;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "filetype", nullable = false)
    private String filetype;

    @Column(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
