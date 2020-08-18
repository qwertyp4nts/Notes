package qwertyp4nts.notes;

import androidx.room.Dao; //data access object
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("INSERT INTO notes (contents) VALUES ('New note')")
    void create();

    @Query("DELETE FROM notes WHERE id = :id")
    void delete(int id);

    @Query("SELECT * FROM notes")
    List<Note> getAllNotes();

    @Query("UPDATE notes SET contents = :contents WHERE id = :id") //parameter binding, replace :x with var - safer way of getting user data in
    void save(String contents, int id);
}
