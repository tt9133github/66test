package com.prism.backendfile.repository;

import com.prism.backendfile.domain.SysFiles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesRepository extends JpaRepository<SysFiles, Integer>
{
}
