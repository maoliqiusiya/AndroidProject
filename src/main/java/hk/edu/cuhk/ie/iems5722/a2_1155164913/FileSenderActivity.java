package hk.edu.cuhk.ie.iems5722.a2_1155164913;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.text.MessageFormat;

import hk.edu.cuhk.ie.iems5722.a2_1155164913.common.Constants;
import hk.edu.cuhk.ie.iems5722.a2_1155164913.manager.WifiLManager;
import hk.edu.cuhk.ie.iems5722.a2_1155164913.model.FileTransfer;
import hk.edu.cuhk.ie.iems5722.a2_1155164913.service.FileSenderService;


public class FileSenderActivity extends BaseActivity {

    public static final String TAG = "FileSenderActivity";

    private static final int CODE_CHOOSE_FILE = 100;

    private FileSenderService fileSenderService;

    private ProgressDialog progressDialog;

    private final FileSenderService.OnSendProgressChangListener progressChangListener = new FileSenderService.OnSendProgressChangListener() {

        @Override
        public void onStartComputeMD5() {
            runOnUiThread(() -> {
                if (isCreated()) {
                    progressDialog.setTitle("Send file");
                    progressDialog.setMessage("calculating MD5");
                    progressDialog.setMax(100);
                    progressDialog.setProgress(0);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });
        }

        @Override
        public void onProgressChanged(final FileTransfer fileTransfer, final long totalTime, final int progress, final double instantSpeed, final long instantRemainingTime, final double averageSpeed, final long averageRemainingTime) {
            runOnUiThread(() -> {
                if (isCreated()) {
                    progressDialog.setTitle("sending file: " + new File(fileTransfer.getFilePath()).getName());
                    if (progress != 100) {
                        progressDialog.setMessage("file MD5：" + fileTransfer.getMd5()
                                + "\n\n" + "total time:" + totalTime + " sec"
                                + "\n\n" + "instant speed: " + (int) instantSpeed + " Kb/s"
                                + "\n" + "instant-remaining time: " + instantRemainingTime + " sec"
                                + "\n\n" + "average speed: " + (int) averageSpeed + " Kb/s"
                                + "\n" + " average-remaining time:" + averageRemainingTime + " sec"
                        );
                    }
                    progressDialog.setProgress(progress);
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                }
            });
        }

        @Override
        public void onTransferSucceed(FileTransfer fileTransfer) {
            runOnUiThread(() -> {
                if (isCreated()) {
                    progressDialog.setTitle("send succeed");
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                }
            });
        }

        @Override
        public void onTransferFailed(FileTransfer fileTransfer, final Exception e) {
            runOnUiThread(() -> {
                if (isCreated()) {
                    progressDialog.setTitle("send failed");
                    progressDialog.setMessage("exception： " + e.getMessage());
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                }
            });
        }
    };

    private final ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            FileSenderService.MyBinder binder = (FileSenderService.MyBinder) service;
            fileSenderService = binder.getService();
            fileSenderService.setProgressChangListener(progressChangListener);
            Log.e(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            fileSenderService = null;
            bindService(FileSenderService.class, serviceConnection);
            Log.e(TAG, "onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_sender);
        initView();
        bindService(FileSenderService.class, serviceConnection);
    }

    private void initView() {
        setTitle("Send file");
        TextView tv_hint = findViewById(R.id.tv_hint);
        tv_hint.setText(MessageFormat.format("First need to connect to WiFi hotspot\nHOTSPOT：{0} \nPASSWORD：{1}", Constants.AP_SSID, Constants.AP_PASSWORD));
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("sending");
        progressDialog.setMax(100);
        progressDialog.setIndeterminate(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fileSenderService != null) {
            fileSenderService.setProgressChangListener(null);
            unbindService(serviceConnection);
        }
        progressDialog.dismiss();
    }

    public void sendFile(View view) {
        if (!Constants.AP_SSID.equals(WifiLManager.getConnectedSSID(this))) {
            showToast("current WiFi is not WiFi hotspot in receiver, pls check");
            return;
        }
        navToChosePicture();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_CHOOSE_FILE) {
            if (resultCode == RESULT_OK) {
                String imageUri = data.getData().toString();
                Log.e(TAG, "file directory: " + imageUri);
                FileSenderService.startActionTransfer(this, imageUri,
                        WifiLManager.getHotspotIpAddress(this));
            }
        }
    }

    private void navToChosePicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, CODE_CHOOSE_FILE);
    }

}