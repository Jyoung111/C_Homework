package com.jYIM.jy.chomework;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class CustomProgressDialog extends Dialog {
    private AnimationDrawable animationDrawable;

    public CustomProgressDialog(Context context) {
        super(context);
        setCancelable(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        ImageView gifImageView = this.findViewById(R.id.loading_gif);
        //Glide로 GIF 적용
        Glide.with(context).asGif().load(R.drawable.loading_gif).into(gifImageView);
    }
}
