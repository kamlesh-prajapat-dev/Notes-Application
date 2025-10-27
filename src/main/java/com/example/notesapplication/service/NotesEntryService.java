package com.example.notesapplication.service;

import com.example.notesapplication.model.NotesEntry;
import com.example.notesapplication.model.User;
import com.example.notesapplication.repository.NotesEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class NotesEntryService {

    @Autowired
    private NotesEntryRepository notesEntryRepository;

    @Autowired
    private UserService userService;


    public void saveEntry(NotesEntry notesEntry, String userName) {
        try {
            User user = userService.findByUserName(userName);
            notesEntry.setDate(LocalDateTime.now());
            NotesEntry saved = notesEntryRepository.save(notesEntry);
            user.getNotesEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void saveEntry(NotesEntry journalEntry) {
        try {
            notesEntryRepository.save(journalEntry);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<NotesEntry> getAll() {
        return notesEntryRepository.findAll();
    }

    public Optional<NotesEntry> findById(ObjectId id) {
        return notesEntryRepository.findById(id);
    }

    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try {

            User user = userService.findByUserName(userName);
            removed = user.getNotesEntries().removeIf(x -> x.getId().equals(id));

            if (removed) {
                userService.saveUser(user);
                notesEntryRepository.deleteById(id);
            }

        }catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleting the entry.", e);
        }

        return removed;
    }
}
