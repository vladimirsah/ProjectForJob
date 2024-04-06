package com.example.projectforjob.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "Picture")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    private int pictureId;

    @Column(name = "name")
    private String name;

    @Column(name = "originalfilename")
    private String originalFileName;

    @Column(name = "size")
    private Long size;

    @Column(name = "contenttype")
    private String contentType;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    @Column(name = "image", columnDefinition = "longblob")
    private byte[] bytes;


    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Person person;

    public Picture() {
    }

    ;

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
