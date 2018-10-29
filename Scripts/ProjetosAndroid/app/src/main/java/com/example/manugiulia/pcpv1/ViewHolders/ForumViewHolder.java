package com.example.manugiulia.pcpv1.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.manugiulia.pcpv1.Interface.ItemClickListener;
import com.example.manugiulia.pcpv1.R;

public class ForumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtTItulo,txtPreview,txtCriadoPor,txtData;
    private ItemClickListener itemClickListener;
public ForumViewHolder(View itemView){
    super(itemView);
    txtTItulo = (TextView)itemView.findViewById(R.id.itemForum_titulo);
    txtPreview = (TextView)itemView.findViewById(R.id.itemForum_preview);
    txtCriadoPor = (TextView)itemView.findViewById(R.id.itemForum_autor);
    txtData = (TextView)itemView.findViewById(R.id.itemForum_data);
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
