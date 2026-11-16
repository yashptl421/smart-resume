package com.smartresume.entity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
@Entity @Table(name="certifications")
public class Certification {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @JsonIgnore @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="resume_id",nullable=false) private Resume resume;
 @Column(nullable=false,length=180) private String certificateName;
 @Column(length=180) private String issuingOrganization;
 private LocalDate issueDate;
 @Column(length=500) private String credentialUrl;
 public Long getId(){return id;} @JsonIgnore public Resume getResume(){return resume;} public void setResume(Resume v){resume=v;}
 public String getCertificateName(){return certificateName;} public void setCertificateName(String v){certificateName=v;}
 public String getIssuingOrganization(){return issuingOrganization;} public void setIssuingOrganization(String v){issuingOrganization=v;}
 public LocalDate getIssueDate(){return issueDate;} public void setIssueDate(LocalDate v){issueDate=v;}
 public String getCredentialUrl(){return credentialUrl;} public void setCredentialUrl(String v){credentialUrl=v;}
}
