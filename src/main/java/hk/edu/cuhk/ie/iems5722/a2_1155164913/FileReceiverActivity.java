package hk.edu.cuhk.ie.iems5722.a2_1155164913;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.MessageFormat;
import java.util.Locale;

import hk.edu.cuhk.ie.iems5722.a2_1155164913.common.Constants;
import hk.edu.cuhk.ie.iems5722.a2_1155164913.model.FileTransfer;
import hk.edu.cuhk.ie.iems5722.a2_1155164913.service.FileReceiverService;


public class FileReceiverActivity extends BaseActivity {

    private FileReceiverService fileReceiverService;

    private ProgressDialog progressDialog;

    private static final String TAG = "ReceiverActivity";

    private final ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            FileReceiverService.MyBinder binder = (FileReceiverService.MyBinder) service;
            fileReceiverService = binder.getService();
            fileReceiverService.setProgressChangListener(progressChangListener);
            if (!fileReceiverService.isRunning()) {
                FileReceiverService.startActionTransfer(FileReceiverActivity.this);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            fileReceiverService = null;
            bindService(FileReceiverService.class, serviceConnection);
        }
    };

    private final FileReceiverService.OnReceiveProgressChangListener progressChangListener = new FileReceiverService.OnReceiveProgressChangListener() {

        private FileTransfer originFileTransfer;

        @Override
        public void onProgressChanged(final FileTransfer fileTransfer, final long totalTime, final int progress, final double instantSpeed, final long instantRemainingTime, final double averageSpeed, final long averageRemainingTime) {
            this.originFileTransfer = fileTransfer;
            runOnUiThread(() -> {
                if (isCreated()) {
                    progressDialog.setTitle("receiving file: " + originFileTransfer.getFileName());
                    if (progress != 100) {
                        progressDialog.setMessage("raw file MD5：" + originFileTransfer.getMd5()
                                + "\n\n" + "total transmitting time：" + totalTime + " 秒"
                                + "\n\n" + "instant speed：" + (int) instantSpeed + " Kb/s"
                                + "\n" + "instant-remaining time：" + instantRemainingTime + " 秒"
                                + "\n\n" + "average speed：" + (int) averageSpeed + " Kb/s"
                                + "\n" + "average-remaining time：" + averageRemainingTime + " 秒"
                        );
                    }
                    progressDialog.setProgress(progress);
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                }
            });
        }

        @Override
        public void onStartComputeMD5() {
            runOnUiThread(() -> {
                if (isCreated()) {
                    progressDialog.setTitle("transmitting finished, calculating MD5 of local file to verify integrity");
                    progressDialog.setMessage("raw file MD5：" + originFileTransfer.getMd5());
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });
        }

        @Override
        public void onTransferSucceed(final FileTransfer fileTransfer) {
            runOnUiThread(() -> {
                if (isCreated()) {
                    progressDialog.setTitle("transmitting succeed");
                    progressDialog.setMessage("raw file MD5:" + originFileTransfer.getMd5()
                            + "\n" + "local file MD5:" + fileTransfer.getMd5()
                            + "\n" + "file location:" + fileTransfer.getFilePath());
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                    Glide.with(FileReceiverActivity.this).load(fileTransfer.getFilePath()).into(iv_image);
                }
            });
        }

        @Override
        public void onTransferFailed(final FileTransfer fileTransfer, final Exception e) {
            runOnUiThread(() -> {
                if (isCreated()) {
                    progressDialog.setTitle("transmitting failed");
                    progressDialog.setMessage("raw file MD5: " + originFileTransfer.getMd5()
                            + "\n" + "local file MD5: " + fileTransfer.getMd5()
                            + "\n" + "file location: " + fileTransfer.getFilePath()
                            + "\n" + "exception: " + e.getMessage());
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                }
            });
        }
    };

    private ImageView iv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_receiver);
        initView();
        bindService(FileReceiverService.class, serviceConnection);
    }

    private void initView() {
        setTitle("Receive file");
        iv_image = findViewById(R.id.iv_image);
        TextView tv_hint = findViewById(R.id.tv_hint);
        tv_hint.setText(MessageFormat.format("First need to open WiFi hotspot for sender\nHOTSPOT：{0} \nPASSWORD：{1}", Constants.AP_SSID, Constants.AP_PASSWORD));
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("receiving");
        progressDialog.setMax(100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fileReceiverService != null) {
            fileReceiverService.setProgressChangListener(null);
            unbindService(serviceConnection);
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void openFile(String filePath) {
        String ext = filePath.substring(filePath.lastIndexOf('.')).toLowerCase(Locale.US);
        try {
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            String mime = mimeTypeMap.getMimeTypeFromExtension(ext.substring(1));
            mime = TextUtils.isEmpty(mime) ? "" : mime;
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(filePath)), mime);
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "file open exception: " + e.getMessage());
            showToast("file open exception: " + e.getMessage());
        }
    }

}