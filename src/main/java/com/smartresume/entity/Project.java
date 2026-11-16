package com.smartresume.entity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity @Table(name="projects")
public class Project {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @JsonIgnore @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="resume_id",nullable=false) private Resume resume;
 @Column(nullable=false,length=180) private String projectName;
 @Column(columnDefinition="TEXT") private String description;
 @Column(length=500) private String technologies;
 @Column(length=500) private String projectUrl;
 public Long getId(){return id;} @JsonIgnore public Resume getResume(){return resume;} public void setResume(Resume v){resume=v;}
 public String getProjectName(){return projectName;} public void setProjectName(String v){projectName=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public String getTechnologies(){return technologies;} public void setTechnologies(String v){technologies=v;}
 public String getProjectUrl(){return projectUrl;} public void setProjectUrl(String v){projectUrl=v;}
}
