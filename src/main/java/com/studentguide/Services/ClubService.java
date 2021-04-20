package com.studentguide.Services;

import java.util.List;

import com.studentguide.dao.ClubRepository;
import com.studentguide.entities.Club;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClubService {

    @Autowired
    private ClubRepository clubRepository;

    public List<Club> getAllClubs() {
        List<Club> clubs = (List<Club>) this.clubRepository.findAll();
        return clubs;
    }

    public Club getClubById(int id) {
        Club club = null;
        try {
            club = clubRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return club;
    }

    public Club addClub(Club club) {
        Club b = clubRepository.save(club);
        return b;
    }

    public void deleteClub(int id) {
        // Club Club = list.stream().filter(e -> e.getId() == id).findFirst().get();
        // list.remove(Club);
        // return Club;
        clubRepository.deleteById(id);

    }

    public void updateClub(Club club, int id) {
        club.setId(id);
        clubRepository.save(club);
    }
}

