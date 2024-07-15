/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uas_java_database.db;

import java.util.List;

public class NoteService {
    private DataStorage storage;
    private List<String> notes;

    public NoteService(DataStorage storage) {
        this.storage = storage;
        this.notes = readNotes();
    }

    public void createNote(String note) {
        storage.writeData(note);
        notes.add(0, note); // Add to the beginning for reverse chronological order
    }

    public List<String> readNotes() {
        return storage.readData();
    }

    public void deleteNote(String note) {
        notes.remove(note);
        storage.deleteData(note);
    }

    public int getNoteCount() {
        return notes.size();
    }

    public String getNoteByIndex(int index) {
        return notes.get(index);
    }
}
