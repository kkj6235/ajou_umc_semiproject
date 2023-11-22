package umc.spring.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.file.domain.S3File;

public interface S3FileRepository extends JpaRepository<S3File, Long> {
}
