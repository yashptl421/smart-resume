package com.smartresume.entity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity @Table(name="education")
public class Education {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @JsonIgnore @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="resume_id",nullable=false) private Resume resume;
 @Column(nullable=false,length=180) private String institution;
 @Column(nullable=false,length=150) private String degree;
 @Column(length=150) private String fieldOfStudy;
 @Column(length=20) private String startMonth; private Integer startYear; @Column(length=20) private String endMonth; private Integer endYear;
 @Column(length=50) private String grade;
 public Long getId(){return id;} @JsonIgnore public Resume getResume(){return resume;} public void setResume(Resume v){resume=v;}
 public String getInstitution(){return institution;} public void setInstitution(String v){institution=v;}
 public String getDegree(){return degree;} public void setDegree(String v){degree=v;}
 public String getFieldOfStudy(){return fieldOfStudy;} public void setFieldOfStudy(String v){fieldOfStudy=v;}
 public String getStartMonth(){return startMonth;} public void setStartMonth(String v){startMonth=v;} public Integer getStartYear(){return startYear;} public void setStartYear(Integer v){startYear=v;}
 public String getEndMonth(){return endMonth;} public void setEndMonth(String v){endMonth=v;} public Integer getEndYear(){return endYear;} public void setEndYear(Integer v){endYear=v;}
 public String getGrade(){return grade;} public void setGrade(String v){grade=v;}
}
