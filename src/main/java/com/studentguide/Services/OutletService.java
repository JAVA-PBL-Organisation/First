package com.studentguide.Services;

import java.util.List;

import com.studentguide.dao.OutletRepository;
import com.studentguide.entities.Outlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OutletService {

    @Autowired
    private OutletRepository outletRepository;

    public List<Outlet> getAllOutlets() {
        List<Outlet> outlets = (List<Outlet>) this.outletRepository.findAll();
        return outlets;
    }

    public Outlet getOutletById(int id) {
        Outlet outlet = null;
        try {
            outlet = outletRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outlet;
    }

    public Outlet addOutlet(Outlet outlet) {
        Outlet b = outletRepository.save(outlet);
        return b;
    }

    public void deleteOutlet(int id) {
        // Outlet Outlet = list.stream().filter(e -> e.getId() == id).findFirst().get();
        // list.remove(Outlet);
        // return Outlet;
        outletRepository.deleteById(id);

    }

    public void updateOutlet(Outlet outlet, int id) {
        outlet.setId(id);
        outletRepository.save(outlet);
    }
}

