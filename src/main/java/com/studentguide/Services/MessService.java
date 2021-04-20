package com.studentguide.Services;

import java.util.List;

import com.studentguide.dao.MessRepository;
import com.studentguide.entities.Mess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessService {

    @Autowired
    private MessRepository messRepository;

    public List<Mess> getAllMesses() {
        List<Mess> messes = (List<Mess>) this.messRepository.findAll();
        return messes;
    }

    public Mess getMessById(int id) {
        Mess mess = null;
        try {
            mess = messRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mess;
    }

    public Mess addMess(Mess mess) {
        Mess b = messRepository.save(mess);
        return b;
    }

    public void deleteMess(int id) {
        // Mess Mess = list.stream().filter(e -> e.getId() == id).findFirst().get();
        // list.remove(Mess);
        // return Mess;
        messRepository.deleteById(id);

    }

    public void updateMess(Mess mess, int id) {
        mess.setId(id);
        messRepository.save(mess);
    }
}

