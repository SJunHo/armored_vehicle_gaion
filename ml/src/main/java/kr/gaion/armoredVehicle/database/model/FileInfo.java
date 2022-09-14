package kr.gaion.armoredVehicle.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.gaion.armoredVehicle.auth.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "FILEINFO_BACKUP")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {
    @Id
    @Column(name = "FILEID")
    private String fileId;

    @Column(name = "FILENM")
    private String fileName;

    @Column(name = "FILEPATH")
    private String filePath;

    @Column(name = "FILESNSR")
    private String fileSnsr;

    @Column(name = "FILETYPE")
    private String fileType;

    @Column(name = "FILEDIV")
    private String fileDiv;

    @Column(name = "FILEPT")
    private String filePt;

    @CreationTimestamp
    @Column(name="`CRTDT`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name="`CRTOR`")
    private User createdBy;

    @Column(name="`CRTOR`", updatable = false, insertable = false, length = 20)
    private String createdById;

    @UpdateTimestamp
    @Column(name="`MDFCDT`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name="`MDFR`")
    private User updatedBy;

    @Column(name="`MDFR`", updatable = false, insertable = false, length = 20)
    private String updatedById;
}
