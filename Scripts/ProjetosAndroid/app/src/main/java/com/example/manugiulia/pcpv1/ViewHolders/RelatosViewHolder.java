package com.example.manugiulia.pcpv1.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.manugiulia.pcpv1.Interface.ItemClickListener;
import com.example.manugiulia.pcpv1.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class RelatosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txtAutor,txtData,txtHora,txtTexto;
    public CircularImageView imgRelato;
    private ItemClickListener itemClickListener;
    public RelatosViewHolder(View itemView){
        super(itemView);
        txtAutor = (TextView)itemView.findViewById(R.id.relatosItem_Autor);
        txtData = (TextView)itemView.findViewById(R.id.relatosItem_Data);
        txtHora = (TextView)itemView.findViewById(R.id.relatosItem_Hora);
        txtTexto = (TextView)itemView.findViewById(R.id.relatosItem_Texto);
        imgRelato = (CircularImageView)itemView.findViewById(R.id.relatosItem_Imagem);
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
