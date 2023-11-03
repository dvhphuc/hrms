package com.hrms.damservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SourceFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "source_file_id", nullable = false)
    private Integer id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "extension")
    private String extension;

    @Column(name = "uploaded_at")
    private Date uploadedAt;
}
