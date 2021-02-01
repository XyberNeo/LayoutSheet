package com.xyberneo.topsheet;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.*;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.common.*;

@DesignerComponent(version = 2,
        description =  "An extension to create TopSheet<br>Developed By Team XyberNeo<br><a href = \"https://docs.xyberneo.com/\" target = \"_blank\">Top Sheet Documentation</a>",
        nonVisible = true,
        iconName = "https://res.cloudinary.com/andromedaviewflyvipul/image/upload/c_scale,w_16/v1612012433/topsheet.png",
        category = ComponentCategory.EXTENSION)
@SimpleObject(external=true)
public class TopSheet extends AndroidNonvisibleComponent implements DialogInterface.OnDismissListener, DialogInterface.OnShowListener {

    public float deviceDensity;
    public float dimAmount = 0.2f;

    public boolean isCancelable;
    public final Context context;

    public Dialog dialog;

    public TopSheet(final ComponentContainer container) {
        super(container.$form());
        context = container.$context();
        deviceDensity = container.$form().deviceDensity();

        initializeDialog();
    }

    private void initializeDialog() {
        dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setOnDismissListener(this);
    }

    public int calculate(final int p){
        return p == -1 || p == -2 ? p :  Math.round(p / deviceDensity);
    }

    @SimpleProperty
    public void Cancelable(final boolean b){
        isCancelable = b;
    }

    @SimpleProperty
    public boolean IsCancelable(){
        return isCancelable;
    }

    @SimpleProperty
    public void DimAmount(final float d){
        dimAmount = d;
    }

    @SimpleProperty
    public float DimAmount(){
        return dimAmount;
    }

    @SimpleFunction
    public void Show(){
        if (dialog != null) dialog.show();
    }
    @SimpleFunction(description="Register the given component as topsheet")
    public void Register(final AndroidViewComponent component, final int height, final int width){
        View view = component.getView();
        ((ViewGroup) view.getParent()).removeView(view);

        if (dialog == null) initializeDialog();

        dialog.setCancelable(isCancelable);
        dialog.getWindow().setDimAmount(dimAmount);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = calculate(width);
        params.height = calculate(height);
        params.gravity = Gravity.TOP;
        dialog.getWindow().setAttributes(params);
    }

    @SimpleFunction
    public void Dismiss(){
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @SimpleFunction
    public boolean IsShowing(){
        return dialog == null ? false : dialog.isShowing();
    }

    @SimpleEvent(description = "Fired when dialog dismissed")
    public void Dismissed(){
        EventDispatcher.dispatchEvent(this,"Dismissed");
    }

    @SimpleEvent(description = "Fired when dialog is shown")
    public void Shown(){
        EventDispatcher.dispatchEvent(this,"Shown");
    }

    @Override
    public void onDismiss(final DialogInterface dialogInterface) {
        Dismissed();
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        Shown();
    }
}
