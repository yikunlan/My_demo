package com.example.ykhuang.mydemo.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ykhuang.mydemo.R;

public class MulThreadDownload extends Activity {
	/** Called when the activity is first created. */
	private ProgressBar pb1 = null;
	private TextView tv1 = null;
	private ProgressBar pb2 = null;
	private TextView tv2 = null;

	private String root = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + File.separator;
//	private String downloadFile = "http://192.168.0.212:8080/nimahannihuijiale/nihao.txt";
	private String downloadFile = "http://gongxue.cn/yingyinkuaiche/UploadFiles_9323/201008/2010082909434077.mp3";

	// 锟斤拷锟斤拷锟窖撅拷锟斤拷锟斤拷某锟斤拷缺锟斤拷锟�
	private int hasRead = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download_thread);

		pb1 = (ProgressBar) findViewById(R.id.progressBar1);
		tv1 = (TextView) findViewById(R.id.textView1);

		pb2 = (ProgressBar) findViewById(R.id.progressBar2);
		tv2 = (TextView) findViewById(R.id.textView2);
		
		Button startDown = (Button)findViewById(R.id.start_download);
		startDown.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				download(downloadFile, root, pb1, tv1);

//				download(downloadFile1, root, pb2, tv2);
			}
		});
		
	}

	private void download(String url, String targetPath, ProgressBar pb,
			TextView tv) {
		DownloadThread dt = new DownloadThread(url, targetPath, pb, tv);

		dt.start();
	}

	// 锟皆讹拷锟斤拷一锟斤拷Handler锟洁，锟斤拷锟斤拷锟竭筹拷锟斤拷息
	public class MyHandler extends Handler {
		private ProgressBar progressBar;
		private TextView textView;

		// 通锟斤拷锟届函锟斤拷锟斤拷确锟斤拷锟斤拷锟侥革拷ProgressBar刷锟斤拷
		public MyHandler(ProgressBar progressBar, TextView textView) {
			this.progressBar = progressBar;
			this.textView = textView;
		}

		public void handleMessage(Message msg) {
			this.progressBar.setProgress(msg.arg1);
			this.textView.setText(msg.arg1 + "%");

			super.handleMessage(msg);
		}
	}

	// 锟斤拷锟斤拷锟竭筹拷
	public class DownloadThread extends Thread {
		private String url = "";
		private String targetPath = "";

		private int hasDownload = 0;

		private int len = -1;
		private byte buffer[] = new byte[4 * 1024];
		private int size = 0;
		private int rate = 0;

		private MyHandler myHandler = null;
		private Message msg = null;

		private ProgressBar pb = null;
		private TextView tv = null;

		public DownloadThread(String url, String targetPath, ProgressBar pb,
				TextView tv) {
			this.url = url;
			this.targetPath = targetPath;

			this.pb = pb;
			this.tv = tv;

			myHandler = new MyHandler(this.pb, this.tv);
		}

		public void run() {
			String targetFileName = this.targetPath
					+ this.url.substring(this.url.lastIndexOf("/") + 1,
							this.url.length());
			File downloadFile = new File(targetFileName);

			if (!downloadFile.exists()) {
				try {
					downloadFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try {
				URL fileUrl = new URL(this.url);
				HttpURLConnection conn = (HttpURLConnection) fileUrl
						.openConnection();

				// 锟斤拷取锟侥硷拷锟斤拷小
				size = conn.getContentLength();

				InputStream is = conn.getInputStream();

				OutputStream os = new FileOutputStream(targetFileName);

				while ((len = is.read(buffer)) != -1) {
					os.write(buffer);

					hasDownload += len;

					rate = (hasDownload * 100 / size);

					msg = new Message();

					msg.arg1 = rate;

					myHandler.sendMessage(msg);

					System.out.println(rate + "%");
				}
				
				//httpconnetion锟斤拷锟斤拷锟揭伙拷直冉锟斤拷辖锟斤拷锟叫达拷锟�
//				HttpURLConnection http = (HttpURLConnection) downUrl  
//                        .openConnection();  
//                http.setConnectTimeout(5 * 1000); // 锟斤拷锟斤拷锟斤拷锟接筹拷时  
//                http.setRequestMethod("GET"); // 锟斤拷锟斤拷锟斤拷锟襟方凤拷锟斤拷锟斤拷锟斤拷锟角★拷GET锟斤拷  
//                // 锟斤拷锟斤拷锟斤拷山锟斤拷艿锟組IME锟斤拷锟斤拷  
//                http.setRequestProperty(  
//                        "Accept",  
//                        "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");  
//                http.setRequestProperty("Accept-Language", "zh-CN"); // 锟斤拷锟斤拷锟斤拷锟较ｏ拷锟斤拷锟斤拷锟斤拷锟斤拷锟洁，锟斤拷锟斤拷锟斤拷锟斤拷锟杰癸拷锟结供一锟斤拷锟斤拷锟较碉拷锟斤拷锟皆版本时要锟矫碉拷  
//                http.setRequestProperty("Referer", downUrl.toString());// 锟斤拷一锟斤拷URL锟斤拷锟矫伙拷锟接革拷URL锟斤拷锟斤拷页锟斤拷锟斤拷锟斤拷锟斤拷实锟角帮拷锟斤拷锟斤拷页锟芥。  
//                http.setRequestProperty("Charset", "UTF-8"); // 锟街凤拷  
//                int startPos = block * (threadId - 1) + downLength;// 锟斤拷始位锟斤拷  
//                int endPos = block * threadId - 1;// 锟斤拷锟斤拷位锟斤拷  
//                http.setRequestProperty("Range", "bytes=" + startPos + "-"  
//                        + endPos);// 锟斤拷锟矫伙拷取实锟斤拷锟斤拷莸姆锟轿�  
//                // 锟斤拷锟斤拷锟斤拷锟斤拷停锟斤拷锟斤拷Servlet锟斤拷锟截碉拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷泄锟斤拷锟斤拷值锟角筹拷锟斤拷锟矫★拷  
//                http.setRequestProperty(  
//                        "User-Agent",  
//                        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");  
//                http.setRequestProperty("Connection", "Keep-Alive"); // 锟斤拷锟斤拷为锟街撅拷锟斤拷锟斤拷  
//  
//                // 锟矫碉拷锟斤拷锟斤拷锟斤拷  
//                InputStream inStream = http.getInputStream();  
//                byte[] buffer = new byte[1024];  
//                int offset = 0;  
//                print("Thread " + this.threadId  
//                        + " start download from position " + startPos);  
//                // 锟斤拷锟斤拷锟斤拷锟侥硷拷  
//                RandomAccessFile threadfile = new RandomAccessFile(  
//                        this.saveFile, "rwd");  
//                // 锟斤拷位锟斤拷pos位锟斤拷  
//                threadfile.seek(startPos);  
//                while (!downloader.getExit()  
//                        && (offset = inStream.read(buffer, 0, 1024)) != -1) {  
//                    // 写锟斤拷锟侥硷拷  
//                    threadfile.write(buffer, 0, offset);  
//                    downLength += offset; // 锟桔硷拷锟斤拷锟截的达拷小  
//                    downloader.update(this.threadId, downLength); // 锟斤拷锟斤拷指锟斤拷锟竭筹拷锟斤拷锟斤拷锟斤拷锟斤拷位锟斤拷  
//                    downloader.append(offset); // 锟桔硷拷锟斤拷锟斤拷锟截达拷小  
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
