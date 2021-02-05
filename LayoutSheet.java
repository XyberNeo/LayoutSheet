package com.xyberneo.layoutsheet;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.*;
import android.view.*;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.common.*;

@DesignerComponent(version = 2,
        description =  "An extension to create LayoutSheet<br><a href = \"//www.github.com/XyberNeo/LayoutSheet/\" target = \"_blank\">GitHub Repository</a><br><a href = \"https://docs.xyberneo.com/\" target = \"_blank\">Layout Sheet Documentation</a>",
        nonVisible = true,
        iconName = "https://res.cloudinary.com/andromedaviewflyvipul/image/upload/c_scale,w_16/v1612012433/topsheet.png",
        category = ComponentCategory.EXTENSION)
@SimpleObject(external=true)
public class LayoutSheet extends AndroidNonvisibleComponent implements DialogInterface.OnDismissListener, DialogInterface.OnShowListener {

    public float deviceDensity;
    public float dimAmount = 0.2f;

    public boolean isCancelable;
    public final Context context;
	public int round;
	public int tipColor;

    public Dialog dialog;

    public LayoutSheet(final ComponentContainer container) {
        super(container.$form());
        context = container.$context();
        deviceDensity = container.$form().deviceDensity();
		
		initializeDialog();
    }

    public int calculate(final int p){
        return p == -1 || p == -2 ? p :  Math.round(p / deviceDensity);
    }
	
	private void initializeDialog() {
        dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setOnDismissListener(this);
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean") 
	@SimpleProperty(description = "To set the dialog cancleable")
    public void Cancelable(final boolean b){
        isCancelable = b;
    }

    @SimpleProperty
    public boolean IsCancelable(){
        return isCancelable;
    }

    @DesignerProperty(defaultValue = "0.5",editorType = "string")
	@SimpleProperty(description = "To set the dim amount")
    public void DimAmount(final float d){
        dimAmount = d;
    }

    @SimpleProperty
    public float DimAmount(){
        return dimAmount;
    }
	
	@DesignerProperty(defaultValue = "50",editorType = "int")
	@SimpleProperty(description = "To set the dim amount")
    public void RoundCornersAmount(final int d){
        round = d;
    }
	
	@DesignerProperty(defaultValue = "&HFF000000", editorType = "color")
    @SimpleProperty
    public void BackgoundColor(int argb) {
        tipColor = argb;
    }
	
	@SimpleProperty
    public final int BottomCenter() {
        return 81;
    }

    @SimpleProperty
    public final int BottomLeft() {
        return 83;
    }

    @SimpleProperty
    public final int BottomRight() {
        return 85;
    }

    @SimpleProperty
    public final int CenterLeft() {
        return 19;
    }

    @SimpleProperty
    public final int CenterRight() {
        return 21;
    }

    @SimpleProperty
    public final int TopCenter() {
        return 49;
    }

    @SimpleProperty
    public final int TopLeft() {
        return 51;
    }

    @SimpleProperty
    public final int TopRight() {
        return 53;
    }

    @SimpleFunction
    public void Show(){
        if (dialog != null){
		dialog.show();
		}
    }
    @SimpleFunction(description="Register the given component as LayoutSheet")
    public void Register(final AndroidViewComponent component, final int height, final int width, int gravity, float leftTopRudius, float rightTopRudius, float leftBottomRudius, float rightBottomRudius){
        float[] Radii = {leftTopRudius, leftTopRudius, rightTopRudius, rightTopRudius, leftBottomRudius, leftBottomRudius, rightBottomRudius, rightBottomRudius};
		View view = component.getView();
        ((ViewGroup) view.getParent()).removeView(view);

        if (dialog == null) initializeDialog();

        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = calculate(width);
        params.height = calculate(height);
        params.gravity = gravity;
		dialog.setCancelable(isCancelable);
		dialog.getWindow().setDimAmount(dimAmount);
        dialog.getWindow().setAttributes(params);
		
		GradientDrawable shape = new GradientDrawable();
		shape.setCornerRadii(Radii);
		shape.setColor(tipColor);
		component.getView().setBackgroundDrawable(shape);
    }

    @SimpleFunction
    public void Dismiss(){
        if (dialog != null) {
            dialog.dismiss();
        }
    }
	
	@SimpleFunction
    public void UnRegister(){
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
