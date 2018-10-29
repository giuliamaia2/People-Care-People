package com.example.manugiulia.pcpv1.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.manugiulia.pcpv1.Interface.ItemClickListener;
import com.example.manugiulia.pcpv1.R;

public class DiarioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtData,txtPreview;
    private ItemClickListener itemClickListener;

    public DiarioViewHolder(View itemView) {
        super(itemView);

        txtData = (TextView)itemView.findViewById(R.id.entrada_data);
        txtPreview = (TextView)itemView.findViewById(R.id.entrada_preview);

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
