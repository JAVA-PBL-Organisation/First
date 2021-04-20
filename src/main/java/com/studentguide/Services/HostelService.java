package com.studentguide.Services;

import java.util.List;

import com.studentguide.dao.HostelRepository;
import com.studentguide.entities.Hostel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HostelService {

    @Autowired
    private HostelRepository hostelRepository;

    public List<Hostel> getAllHostels() {
        List<Hostel> hostels = (List<Hostel>) this.hostelRepository.findAll();
        return hostels;
    }

    public Hostel getHostelById(int id) {
        Hostel hostel = null;
        try {
            // Hostel = list.stream().filter(e -> e.getId() == id).findFirst().get();

            hostel = hostelRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hostel;
    }

    public Hostel addHostel(Hostel hostel) {
        Hostel b = hostelRepository.save(hostel);
        return b;
    }

    public void deleteHostel(int id) {
        // Hostel Hostel = list.stream().filter(e -> e.getId() == id).findFirst().get();
        // list.remove(Hostel);
        // return Hostel;
        hostelRepository.deleteById(id);

    }

    public void updateHostel(Hostel hostel, int id) {
        hostel.setId(id);
        hostelRepository.save(hostel);
    }
}

