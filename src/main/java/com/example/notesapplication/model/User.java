package com.example.notesapplication.model;


import com.mongodb.lang.NonNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
@Data
public class User {

    @Id
    private ObjectId userId;

    @Indexed(unique = true)
    @NotNull
    private String userName;

    @NonNull
    private String password;

    @DBRef
    private List<NotesEntry> notesEntries = List.of();

    private List<String> roles;
}
