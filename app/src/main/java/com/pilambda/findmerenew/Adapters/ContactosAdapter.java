package com.pilambda.findmerenew.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pilambda.findmerenew.CustomViews.LetterTileProvider;
import com.pilambda.findmerenew.Model.Contacto;
import com.pilambda.findmerenew.MyInterfaces.MyInterfaces;
import com.pilambda.findmerenew.R;

import java.util.ArrayList;

/**
 * Created by Alexis on 1/26/2018.
 */

public class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ContactosViewHolder> {
    private ArrayList<Contacto> mContactos;
    private Context mContext;
    private MyInterfaces.MyOnclickListener mMyOnclickListener;

    public ContactosAdapter(Context context, MyInterfaces.MyOnclickListener myOnclickListener) {
        mContext = context;
        mContactos = new ArrayList<>();
        mMyOnclickListener = myOnclickListener;
    }

    @Override
    public ContactosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_contacto, parent, false);
        ContactosViewHolder contactosViewHolder = new ContactosViewHolder(view);
        return contactosViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactosViewHolder holder, int position) {
        Contacto contacto = mContactos.get(position);
        holder.mTextNombre.setText(contacto.getNombre());
        LetterTileProvider letterTileProvider = new LetterTileProvider(mContext);
        final Resources resources = mContext.getResources();
        final int tileSize = resources.getDimensionPixelSize(R.dimen.letter_tile_size);
        final LetterTileProvider tileProvider = new LetterTileProvider(mContext);
        final String colorKey = contacto.getNombre();
        final Bitmap letterTile = tileProvider.getLetterTile(contacto.getNombre(),colorKey, tileSize, tileSize);
        //holder.mImageContacto.setImageBitmap(letterTile);
    }

    @Override
    public int getItemCount() {
        return mContactos.size();
    }

    public void setContactos(ArrayList<Contacto> contactos) {
        mContactos = contactos;
        this.notifyDataSetChanged();
    }

    public class ContactosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView mTextNombre;
        //private final CircleImageView mImageContacto;

        public ContactosViewHolder(View itemView) {
            super(itemView);
            mTextNombre = itemView.findViewById(R.id.textContactoName);
            //mImageContacto = itemView.findViewById(R.id.image_userImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mMyOnclickListener != null ){
                mMyOnclickListener.onclick(view,getLayoutPosition());
            }
        }
    }
}
