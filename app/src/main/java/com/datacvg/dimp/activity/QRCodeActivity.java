package com.datacvg.dimp.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.DisplayUtils;
import com.datacvg.dimp.baseandroid.utils.ShareContentType;
import com.datacvg.dimp.baseandroid.utils.ShareUtils;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.VPNConfigBean;
import com.datacvg.dimp.presenter.QRCodePresenter;
import com.datacvg.dimp.view.QRCodeView;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.util.HashMap;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-23
 * @Description : vpn配置二维码展示页面
 */
public class QRCodeActivity extends BaseActivity<QRCodeView, QRCodePresenter> implements QRCodeView {

    @BindView(R.id.img_qrcode)
    ImageView imgQRCode ;
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.img_right)
    ImageView imgRight ;

    /**
     * 布局中二维码展示页面宽高
     */
    private int width ;
    private int height ;
    private VPNConfigBean vpnConfigBean ;
    private Uri mUri ;
    private Bitmap mBitmap;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(this,mContext.getResources()
                .getColor(R.color.c_FFFFFF));

        tvTitle.setText(resources.getString(R.string.qr_code));
        imgRight.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_share));
        width = DisplayUtils.getWidth() * 1/3 ;
        height = width ;
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        vpnConfigBean = (VPNConfigBean) getIntent()
                .getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        createQRCode();
    }


    /**
     * 生成二维码
     */
    private void createQRCode() {
        if (vpnConfigBean == null){
            ToastUtils.showShortToast(resources.getString(R.string.qr_code_generation_fails));
            finish();
            return;
        }
        String configStr = new Gson().toJson(vpnConfigBean);

        /**
         * 二维码参数设置
         */
        HashMap hashMap = new HashMap();
        hashMap.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hashMap.put(EncodeHintType.MARGIN,1);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter()
                    .encode(configStr, BarcodeFormat.QR_CODE,width,width,hashMap);
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }
            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            mBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            imgQRCode.setImageBitmap(mBitmap);
            mUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver()
                    , mBitmap, null,null));
        } catch (WriterException e) {
            e.printStackTrace();
            ToastUtils.showShortToast(resources.getString(R.string.qr_code_generation_fails));
        }
    }

    @OnClick({R.id.img_left,R.id.img_right})
    public void OnCLick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            case R.id.img_right :
                    new ShareUtils
                            .Builder(mContext)
                            .setContentType(ShareContentType.IMAGE)
                            .setShareFileUri(mUri)
                            .build()
                            .shareBySystem();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBitmap !=null){
            mBitmap.recycle();
        }
    }
}
