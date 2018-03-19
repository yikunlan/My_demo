/**
 *
 */
package com.example.androidtest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ykhuang.mydemo.R;

/**
 * @author hyk
 *2015-10-10
 *得到布局文件的截图
 */
public class GetLayoutScreen extends Activity implements OnClickListener{
	private View mView;
	private Bitmap mbBitmap;
	private ImageView mScreenImageView;
	private Button mButton;
	private ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_layout_screen);

		findView();
		mButton.setOnClickListener(this);


		mView = getLayoutInflater().inflate(R.layout.share_mama_ranking,
				null);
		// 打开图像缓存
		mView.setDrawingCacheEnabled(true);
		// 测量view的大小
		mView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		// 发送位置和尺寸到view及其所有的子view
		mView.layout(0, 0, mView.getMeasuredWidth(), mView.getMeasuredHeight());

		mbBitmap = mView.getDrawingCache();// 获得可视组件的截图

	}
	private boolean findView(){
		mButton = (Button)findViewById(R.id.button1);
		mImageView = (ImageView)findViewById(R.id.img_screen);
		return true;
	}
	private Bitmap creatView() {

		TextView tvPresenTextView = (TextView) mView
				.findViewById(R.id.tv_present);
		tvPresenTextView.setText("1000%");

		return mbBitmap;
	}
	@Override
	public void onClick(View arg0) {
		mImageView.setImageBitmap(mbBitmap);
	}
}
