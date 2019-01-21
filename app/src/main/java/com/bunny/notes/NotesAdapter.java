package com.bunny.notes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bunny.notes.database.Notes;
import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Notes> mNotesList = new ArrayList<>();
    private onAdapterItemClickListener onAdapterItemClickListener;

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_item_row_layout,viewGroup,false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder notesViewHolder, int i) {

        Notes notes = mNotesList.get(i);
        notesViewHolder.tvTitle.setText(notes.getNoteName());
        notesViewHolder.tvDescription.setText(notes.getNoteBody());

    }

    @Override
    public int getItemCount() {
        return mNotesList.size();
    }

    public void setNotes(List<Notes> notesList) {
        this.mNotesList = notesList;
        notifyDataSetChanged();
    }

    public Notes getNote(int position){
        return mNotesList.get(position);
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription;
        private NotesViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onAdapterItemClickListener != null && getAdapterPosition() !=RecyclerView.NO_POSITION){
                        onAdapterItemClickListener.onItemClicked(mNotesList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public interface onAdapterItemClickListener{
        void onItemClicked(Notes notes);
    }

    public void setOnAdapterItemClickListener(onAdapterItemClickListener onAdapterItemClickListener) {
        this.onAdapterItemClickListener = onAdapterItemClickListener;
    }
}
