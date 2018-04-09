package com.example.ykhuang.mydemo.speek;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.ykhuang.mydemo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 科大讯飞集成
 * @author HuangYK
 *
 */
public class SpeekActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speek);
		ButterKnife.bind(this);

	}

	@OnClick({R.id.IatDemo_btn,R.id.AsrDemo_btn,R.id.TtsDemo_btn,R.id.IseDemo_btn,R.id.VocalVerifyDemo_btn})
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.IatDemo_btn://语音转写
			intent = new Intent(SpeekActivity.this, IatDemo.class);
			break;
		case R.id.AsrDemo_btn://语法识别
			// 语法识别
			intent = new Intent(SpeekActivity.this, AsrDemo.class);
			break;
		case R.id.TtsDemo_btn://语音合成
			// 语音合成
			intent = new Intent(SpeekActivity.this, TtsDemo.class);
			break;
		case R.id.IseDemo_btn://语音评测
			// 语音评测
			intent = new Intent(SpeekActivity.this, IseDemo.class);
			break;
		case R.id.VocalVerifyDemo_btn://声纹
			// 声纹
			intent = new Intent(SpeekActivity.this, VocalVerifyDemo.class);
			break;

		default:
			break;
		}
		if (intent != null) {
			startActivity(intent);
		}
	}
	
}
