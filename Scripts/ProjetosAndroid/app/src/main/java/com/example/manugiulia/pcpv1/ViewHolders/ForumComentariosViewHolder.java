package com.example.manugiulia.pcpv1.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.manugiulia.pcpv1.Interface.ItemClickListener;
import com.example.manugiulia.pcpv1.R;

public class ForumComentariosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txtAutor,txtData,txtTexto;
    private ItemClickListener itemClickListener;
    public ForumComentariosViewHolder(View itemView){
        super(itemView);
        txtAutor = (TextView)itemView.findViewById(R.id.comentarioAutor);
        txtData = (TextView)itemView.findViewById(R.id.comentarioData);
        txtTexto = (TextView)itemView.findViewById(R.id.comentarioTexto);
        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
