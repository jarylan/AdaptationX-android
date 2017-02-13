package com.jary.sample;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jary.adaptation_android.permissions.AfterPermissionGranted;
import com.jary.adaptation_android.permissions.AppSettingsDialog;
import com.jary.adaptation_android.permissions.EasyPermissions;
import com.jary.adaptation_android.screen.ScreenManager;
import com.jary.adaptation_android.widget.TextViewManager;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;

public class MainActivity extends AutoLayoutActivity implements EasyPermissions.PermissionCallbacks{

    private static final String TAG = "MainActivity";

    private static final int RC_CAMERA_PERM = 123;
    private static final int RC_LOCATION_CONTACTS_PERM = 124;
    private static final int RC_SETTINGS_SCREEN = 125;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraTask();
            }
        });
        findViewById(R.id.button_location_and_wifi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationAndContactsTask();
            }
        });
    }
    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    private void locationAndContactsTask() {
        String[] parms = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_CONTACTS};
        if(EasyPermissions.hasPermissions(this,parms)){
            // Have permissions, do the thing!
            Toast.makeText(this, "TODO: Location and Contacts things", Toast.LENGTH_LONG).show();
        }else{
            // Ask for both permissions
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_location_contacts),
                    RC_LOCATION_CONTACTS_PERM, parms);
        }
    }
    @AfterPermissionGranted(RC_CAMERA_PERM)
    private void cameraTask() {
        if(EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)){
            // Have permissions, do the thing!
            Toast.makeText(this, "TODO: Camera things", Toast.LENGTH_LONG).show();
        }else{
            // Ask for one permission
            EasyPermissions.requestPermissions(this,getString(R.string.rationale_camera),RC_CAMERA_PERM,Manifest.permission.CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"onPermissions---------" + requestCode + "   " + resultCode);
        if (requestCode == RC_SETTINGS_SCREEN) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(this, R.string.returned_from_app_settings_to_activity, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        if(requestCode==RC_CAMERA_PERM|requestCode==RC_LOCATION_CONTACTS_PERM) {//嵌套 Fragment 里面请求权限 需要加上；不然会分别在 Fragment 与 MainActivity 回调
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, getString(R.string.rationale_ask_again))
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setPositiveButton(getString(R.string.setting))
                    .setNegativeButton(getString(R.string.cancel), null /* click listener */)
                    .setRequestCode(RC_SETTINGS_SCREEN)
                    .build()
                    .show();
        }
    }
}
