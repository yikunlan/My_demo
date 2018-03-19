/**
 *
 */
package com.example.ykhuang.mydemo.loading;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ykhuang.mydemo.R;

/**
 * @author hyk
 *2015-11-30
 *自定义的加载图(可以是任何的图片)
 */
public class MProgressDialog extends Activity implements OnClickListener{
	private Button showDialogBtn;
	private Dialog mDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prpgress_dialog);

		showDialogBtn = (Button)findViewById(R.id.show_dialog);
		showDialogBtn.setOnClickListener(this);

	}


	/**
	 * 得到自定义的progressDialog
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog createLoadingDialog(Context context, String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view

		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.loading_animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText(msg);// 设置加载信息

		final Dialog loadingDialog = new Dialog(context, R.style.dialog);// 创建自定义样式dialog
		loadingDialog.setCancelable(false);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				loadingDialog.dismiss();
			}
		});
		return loadingDialog;

	}


	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
			case R.id.show_dialog:
				mDialog =  createLoadingDialog(this, "正在加载");
				mDialog.show();
				break;

			default:
				break;
		}

	}


}
