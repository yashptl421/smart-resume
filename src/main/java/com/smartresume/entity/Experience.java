package com.smartresume.entity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
@Entity @Table(name="experience")
public class Experience {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @JsonIgnore @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="resume_id",nullable=false) private Resume resume;
 @Column(nullable=false,length=180) private String companyName;
 @Column(nullable=false,length=150) private String jobTitle;
 private LocalDate startDate; private LocalDate endDate;
 @Column(columnDefinition="TEXT") private String description;
 public Long getId(){return id;} @JsonIgnore public Resume getResume(){return resume;} public void setResume(Resume v){resume=v;}
 public String getCompanyName(){return companyName;} public void setCompanyName(String v){companyName=v;}
 public String getJobTitle(){return jobTitle;} public void setJobTitle(String v){jobTitle=v;}
 public LocalDate getStartDate(){return startDate;} public void setStartDate(LocalDate v){startDate=v;}
 public LocalDate getEndDate(){return endDate;} public void setEndDate(LocalDate v){endDate=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
}
