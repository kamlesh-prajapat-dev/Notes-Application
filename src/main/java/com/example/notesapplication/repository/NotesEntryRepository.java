package com.example.notesapplication.repository;

import com.example.notesapplication.model.NotesEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotesEntryRepository extends MongoRepository<NotesEntry, ObjectId> {

}
