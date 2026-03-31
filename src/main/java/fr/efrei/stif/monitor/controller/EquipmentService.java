package fr.efrei.stif.monitor.controller;

import fr.efrei.stif.monitor.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository){
        this.equipmentRepository = equipmentRepository;
    }

    public Equipment findById(Integer id) {
        return equipmentRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        equipmentRepository.deleteById(id);
    }
}
