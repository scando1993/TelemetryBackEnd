package net.pacificsoft.springbootcrudrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import net.pacificsoft.springbootcrudrest.model.WifiScan;

@Repository
public interface WifiScanRepository extends JpaRepository<WifiScan, Long>{

}
