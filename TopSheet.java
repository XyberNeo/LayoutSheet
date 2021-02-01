/*
Last Updated : 1 Feb, 2021 at 10 : 24 : 43 IST
Author : XyberNeo
For Support and Queries get in touch via telegram (t.me/Clawser) or mail it to us at admin@xyberneo.com
*/
package com.xyberneo.topsheet;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.*;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.*;
import com.google.appinventor.components.runtime.*;

@DesignerComponent(version = 1,
        description =  "An extension to create TopSheet<br>Developed By Team XyberNeo<br><a href = \"https://docs.xyberneo.com/\" target = \"_blank\">Top Sheet Documentation</a>",
        nonVisible = true,
        iconName = "https://res.cloudinary.com/andromedaviewflyvipul/image/upload/c_scale,w_16/v1612012433/topsheet.png",
        category = ComponentCategory.EXTENSION)
@SimpleObject(external=true)
public class TopSheet extends AndroidNonvisibleComponent{
    public Context context;
    public Dialog dialog;
    public TopSheet(ComponentContainer container){
        super(container.$form());
        context = container.$context();
        dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }
    @SimpleFunction(description="Shows the given component as topsheet")
    public void Show(AndroidViewComponent component,int height,int width,float dimAmount,boolean isCancelable){
        View view = component.getView();
        ((ViewGroup)view.getParent()).removeView(view);
        dialog.setCancelable(isCancelable);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setDimAmount(dimAmount);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        params.gravity = Gravity.TOP;
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }
    @SimpleFunction()
    public void Hide(){
        dialog.hide();
    }
}
