package qwertyp4nts.notes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import static qwertyp4nts.notes.MainActivity.database;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        LinearLayout containerView;
        TextView textView;

        NoteViewHolder(View v) {
            super(v);
            containerView = v.findViewById(R.id.note_row);
            textView = v.findViewById(R.id.note_row_text);
            MaterialButton deleteButton = v.findViewById(R.id.delete_row);

            containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Note current = (Note) containerView.getTag();
                    Intent intent = new Intent(view.getContext(), NoteActivity.class);
                    intent.putExtra("id", current.id);
                    intent.putExtra("contents", current.contents);

                    view.getContext().startActivity(intent);
                }
            });


            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Note current = (Note) containerView.getTag();
                    database.noteDao().delete(current.id);
                    // MainActivity.adapter.reload();
                }
            });
        }
    }

    private List<Note> notes = new ArrayList<>();

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.note_row, parent, false);

            return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note current = notes.get(position);
        holder.textView.setText(current.contents);
        holder.containerView.setTag(current);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void reload() {
        notes = MainActivity.database.noteDao().getAllNotes();
        notifyDataSetChanged();
    }
}
