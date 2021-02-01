/*
Last Updated : 1 Feb, 2021 at 10 : 24 : 43 IST
Author : XyberNeo, Contributors : Sunny Gupta AKA vknow360 and XomaDev AKA Kumarasway BG
For Support and Queries get in touch via telegram (t.me/Clawser) or mail it to us at admin@xyberneo.com
Join our support group at : t.me/XyberNeoExtensions and Broadcasting channel at : t.me/XyberNeo
*/

package com.xyberneo.topsheet;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.FrameLayout;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;

@DesignerComponent(version = 1,
        description =  "An extension to create TopSheet<br>Developed By Team XyberNeo<br><a href = \"https://docs.xyberneo.com/\" target = \"_blank\">Top Sheet Documentation</a>",
        nonVisible = true,
        iconName = "https://res.cloudinary.com/andromedaviewflyvipul/image/upload/c_scale,w_16/v1612012433/topsheet.png",
        category = ComponentCategory.EXTENSION)

@SimpleObject(external=true)
public class TopSheet extends AndroidNonvisibleComponent implements DialogInterface.OnCancelListener{

    private final Activity activity;
    private final Dialog dialog;

    private FrameLayout componentLayout;

    private boolean cancelable = true;
    private float dimAmount = 0.1f;

    public TopSheet(final ComponentContainer container){
        super(container.$form());
        activity = container.$context();

        dialog = new Dialog(activity);

//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @SimpleFunction(
            description = "Register the given component")
    public void Register(AndroidViewComponent component) {
        View view = component.getView();
        componentLayout = new FrameLayout(activity);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        float height = displayMetrics.widthPixels * 35;

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, (int) height);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        componentLayout.setLayoutParams(params);

        if(view.getParent() != null)
            ((ViewGroup) view.getParent()).removeView(view);

        componentLayout.addView(view);
    }

    @SimpleProperty
    public void Cancelable(final boolean condition) {
//        dialog.setCancelable(condition);
        cancelable = condition;
    }

    @SimpleProperty
    public boolean Cancelable() {
        return cancelable;
    }

    @SimpleProperty
    public void DimAmount(final float amount) {
        dialog.getWindow().setDimAmount(dimAmount);
        dimAmount = amount;
    }

    @SimpleProperty
    public float DimAmount() {
        return dimAmount;
    }

    @SimpleFunction(
            description = "Show the topsheet")
    public void ShowTopSheet() {

        dialog.setCancelable(cancelable);
        dialog.setContentView(componentLayout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setDimAmount(dimAmount);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();

        params.gravity = Gravity.TOP;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes(params);
        dialog.setOnCancelListener(this);
        dialog.show();
    }

    @SimpleEvent(description = "Event fired when dialog cancelled")
    public void DialogCancelled() {
        EventDispatcher.dispatchEvent(this, "DialogCancelled");
    }

//    @SimpleFunction(description="Shows the given component as topsheet")
//    public void Show(AndroidViewComponent component,int height,int width,float dimAmount,boolean isCancelable){
//        View view = component.getView();
//        ((ViewGroup)view.getParent()).removeView(view);
//        dialog.setCancelable(isCancelable);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().setDimAmount(dimAmount);
//        dialog.setContentView(view);
//        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
//        params.width = width;
//        params.height = height;
//        params.gravity = Gravity.TOP;
//        dialog.getWindow().setAttributes(params);
//        dialog.show();
//    }

    @SimpleFunction
    public void Hide(){
        dialog.hide();
        DialogCancelled();
    }

    @SimpleProperty
    public boolean Visible() {
        return dialog.isShowing();
    }

    @Override
    public void onCancel(final DialogInterface dialogInterface) {
        DialogCancelled();
    }
}
