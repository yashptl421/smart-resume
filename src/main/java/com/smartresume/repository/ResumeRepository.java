package com.smartresume.repository;
import com.smartresume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface ResumeRepository extends JpaRepository<Resume,Long> {
    List<Resume> findAllByUser_IdOrderByUpdatedAtDesc(Long userId);
    Optional<Resume> findByIdAndUser_Id(Long id, Long userId);
}
