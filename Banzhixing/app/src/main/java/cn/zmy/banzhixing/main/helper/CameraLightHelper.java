package cn.zmy.banzhixing.main.helper;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;

public final class CameraLightHelper {
    private static Camera sCamera;

    private CameraLightHelper() {

    }

    public static void enableLight(Context context, boolean enable) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                if (cameraManager == null) {
                    return;
                }
                String[] ids  = cameraManager.getCameraIdList();
                for (String id : ids) {
                    CameraCharacteristics c = cameraManager.getCameraCharacteristics(id);
                    Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
                    if (flashAvailable != null && flashAvailable && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                        cameraManager.setTorchMode(id, enable);
                    }
                }
            } else {
                if (sCamera == null) {
                    sCamera = Camera.open();
                }
                Camera.Parameters mParameters;
                mParameters = sCamera.getParameters();
                mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                sCamera.setParameters(mParameters);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            release();
        }

    }

    public static void release() {
        if (sCamera != null) {
            sCamera.release();
            sCamera = null;
        }
    }
}
