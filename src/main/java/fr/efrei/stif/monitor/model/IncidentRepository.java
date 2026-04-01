package fr.efrei.stif.monitor.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentRepository extends JpaRepository<IncidentReport, Integer>{
    List<IncidentReport> findByStatusFalse();
    List<IncidentReport> findByEquipmentIdOrderByDateTimeDesc(Integer equipmentId);
    List<IncidentReport> findTop3ByEquipmentIdAndIdNotOrderByDateTimeDesc(Integer equipmentId, Integer reportId);
}
