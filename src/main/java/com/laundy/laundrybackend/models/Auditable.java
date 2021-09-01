package com.laundy.laundrybackend.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<U>{
    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date lastUpdatedDate;
}
