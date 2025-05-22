package com.sylfie.service;

import com.sylfie.model.entity.Location;
import com.sylfie.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location save(Location location){
        return locationRepository.save(location);
    }

    public Location getById(Long id){
        return locationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Location not found with id: " + id));
    }

    public void delete(Long id){
        locationRepository.deleteById(id);
    }

    public List<Location> getAll(){
        return locationRepository.findAll();
    }
}
