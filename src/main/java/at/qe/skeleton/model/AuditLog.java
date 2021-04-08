package at.qe.skeleton.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.domain.Persistable;

@Entity
public class AuditLog implements Persistable<Long> {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @ManyToOne(optional=false)
    private User updateUser;

    @Column(nullable = true)
    private String classInformation;

    @Column(length = 4096, nullable = false)
    private String message;


    public void setId(long id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String msg) {
        this.message = msg;
    }
    public User getUpdateUser() {
        return updateUser;
    }
    public void setUpdateUser(User user) {
        this.updateUser = user;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }

    public String getClassInformation() {
        return classInformation;
    }
    public void setClassInformation(String classInformation) {
        this.classInformation = classInformation;
    }
    @Override
    public Long getId() {
        return id;
    }
    @Override
    public boolean isNew() {
        return (message == null);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditLog auditLog = (AuditLog) o;
        return id == auditLog.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
