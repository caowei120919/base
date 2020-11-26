package com.datacvg.sempmobile.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.utils.BitMapUtil;
import com.datacvg.sempmobile.baseandroid.utils.PLog;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.baseandroid.utils.ToastUtils;
import com.datacvg.sempmobile.bean.ScreenBean;
import com.datacvg.sempmobile.bean.VPNConfigBean;
import com.datacvg.sempmobile.presenter.ScanPresenter;
import com.datacvg.sempmobile.view.ScanView;
import com.google.gson.Gson;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-23
 * @Description :扫描二维码
 */
public class ScanActivity extends BaseActivity<ScanView,ScanPresenter> implements ScanView {

    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.tv_right)
    TextView tvRight ;
    @BindView(R.id.decor_content_parent)
    DecoratedBarcodeView decoratedBarcodeView ;

    private CaptureManager captureManager ;
    private boolean hasAlbum = true ;
    private int scanTag = 0 ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext,resources.getColor(R.color.c_FFFFFF));
        hasAlbum = getIntent().getBooleanExtra(Constants.EXTRA_DATA_FOR_ALBUM,true);
        scanTag = getIntent().getIntExtra(Constants.EXTRA_DATA_FOR_SCAN,0);
        tvTitle.setText(resources.getString(R.string.scan_the_qr_code));
        tvRight.setText(resources.getString(R.string.album));
        tvRight.setVisibility(hasAlbum ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        captureManager = new CaptureManager(mContext,decoratedBarcodeView);
        captureManager.initializeFromIntent(getIntent(),savedInstanceState);
        captureManager.decode();
        decoratedBarcodeView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                switch (scanTag){
                    /**
                     * VPN设置扫描
                     */
                    case Constants.SCAN_FOR_VPN :
                        VPNConfigBean bean
                                = new Gson().fromJson(result.getText(),VPNConfigBean.class);
                        if(bean == null){
                            ToastUtils.showShortToast(resources
                                    .getString(R.string.this_qr_code_is_not_supported));
                        }else{
                            Intent intent = new Intent();
                            intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,bean);
                            setResult(Constants.RESULT_SCAN_RESULT,intent);
                            finish();
                        }
                        break;

                    /**
                     * 扫码登录
                     */
                    case Constants.SCAN_FOR_LOGIN:
                            Intent loginIntent = new Intent(mContext,LoginWebActivity.class);
                            loginIntent.putExtra(Constants.EXTRA_DATA_FOR_SCAN,result.getText());
                            mContext.startActivity(loginIntent);
                            finish();
                         break;

                    /**
                     * 大屏投放扫码
                     */
                    case Constants.SCAN_FOR_SCREEN:
                            Intent screenIntent = new Intent(mContext,ConfirmInfoActivity.class);
                            screenIntent.putExtra(Constants.EXTRA_DATA_FOR_SCAN,result.getText());
                            screenIntent.putExtra("title",getIntent()
                                    .getStringExtra("title"));
                            mContext.startActivity(screenIntent);
                            finish();
                         break;

                    default:

                        break;
                }
                PLog.e(result.getText());
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return decoratedBarcodeView.onKeyDown(keyCode,event) || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constants.REQUEST_OPEN_CAMERA :
                    if(data != null){
                        Uri uri = data.getData() ;
                        String imagePath = BitMapUtil.getPicturePathFromUri(mContext,uri);
                        Bitmap bitmap = BitMapUtil.compressPicture(imagePath);
                        Result result = setZxingResult(bitmap);
                        if (result == null) {
                            ToastUtils.showShortToast(resources
                                    .getString(R.string.this_qr_code_is_not_supported));
                        } else {
                            VPNConfigBean bean
                                    = new Gson().fromJson(result.getText(),VPNConfigBean.class);
                            if(bean == null){
                                ToastUtils.showShortToast(resources
                                        .getString(R.string.this_qr_code_is_not_supported));
                            }else{
                                Intent intent = new Intent();
                                intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,bean);
                                setResult(Constants.RESULT_SCAN_RESULT,intent);
                                finish();
                            }
                        }
                    }
                break;

            default:
                break;
        }
    }

    private static Result setZxingResult(Bitmap bitmap) {
        if (bitmap == null){
            return null;
        }
        int picWidth = bitmap.getWidth();
        int picHeight = bitmap.getHeight();
        int[] pix = new int[picWidth * picHeight];
        bitmap.getPixels(pix, 0, picWidth, 0, 0, picWidth, picHeight);
        //构造LuminanceSource对象
        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(picWidth
                , picHeight, pix);
        BinaryBitmap bb = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
        //因为解析的条码类型是二维码，所以这边用QRCodeReader最合适。
        QRCodeReader qrCodeReader = new QRCodeReader();
        Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        hints.put(DecodeHintType.TRY_HARDER, true);
        Result result;
        try {
            result = qrCodeReader.decode(bb, hints);
            return result;
        } catch (NotFoundException | ChecksumException | FormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @OnClick({R.id.img_left,R.id.tv_right})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            case R.id.tv_right :
                    openAlbum();
                break;
        }
    }

    /**
     * 从相册选取图片
     */
    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Constants.REQUEST_OPEN_CAMERA);
    }
}
