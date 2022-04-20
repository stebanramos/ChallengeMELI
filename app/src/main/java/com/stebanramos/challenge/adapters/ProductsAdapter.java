package com.stebanramos.challenge.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.stebanramos.challenge.R;
import com.stebanramos.challenge.models.Product;
import com.stebanramos.challenge.utilies.Utils;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {

    private ArrayList<Product> productsList;
    private Context context;
    private OnItemClickListener mListener;
    private final String TAg = "";

    public interface OnItemClickListener {
        void onItemClick(int position, Product product, ImageView imageView);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public ProductsAdapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.productsList = list;

    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.products_item, viewGroup, false);
        return new ProductsViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductsViewHolder productsViewHolder, int position) {

        try {
            Product currentItem = productsList.get(position);

            String productImageUrl = currentItem.getThumbnail();
            String productTittle = currentItem.getTitle();
            productsViewHolder.tv_products_tittle.setText(productTittle);
            productsViewHolder.tv_products_price.setText(currentItem.getPrice());
            //Picasso.get().load(newsImageUrl).centerCrop().fit().into(newsViewHolder.newsImageView);

            if (productImageUrl.startsWith("http://"))
                productImageUrl = productImageUrl.replace("http://", "https://");

            Glide.with(context)
                    .asBitmap()
                    .load(productImageUrl)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            productsViewHolder.products_progress_bar.setVisibility(View.GONE);
                            productsViewHolder.iv_products_image.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        } catch (Exception e) {
            Utils.printtCatch(e, "onBindViewHolder", "ProductsAdapter");
        }

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_products_image;
        public TextView tv_products_tittle;
        public TextView tv_products_price;
        public ProgressBar products_progress_bar;

        public ProductsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            iv_products_image = itemView.findViewById(R.id.iv_products_image);
            tv_products_tittle = itemView.findViewById(R.id.tv_products_tittle);
            tv_products_price = itemView.findViewById(R.id.tv_products_price);
            products_progress_bar = itemView.findViewById(R.id.products_progress_bar);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (listener != null) {
                            int position = getAdapterPosition();
                            ViewCompat.setTransitionName(iv_products_image, productsList.get(position).getTitle());
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onItemClick(position, productsList.get(position), iv_products_image);
                            }
                        }
                    } catch (Exception e) {
                        Utils.printtCatch(e, "ProductsViewHolder onClick", "ProductsAdapter");

                    }

                }
            });
        }
    }
}
