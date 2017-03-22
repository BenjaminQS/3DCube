package com.example.denjamin.spinningcube;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class CubeActivity extends Activity {

    private ServiceConnection conn;
    private ICubeInterface iCubeInterface;
    public String cubeData;

    private void bindCubeService(){
        Intent intentService =  new Intent();
        intentService.setClassName(this,"com.example.denjamin.spinningcube.InnerCubeService");
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder iBinder) {
                iCubeInterface = ICubeInterface.Stub.asInterface(iBinder);
                try{

                    Log.d("SAP","你好" + iCubeInterface.getCubeVertexInfo());
                    cubeData = iCubeInterface.getCubeVertexInfo();
                }catch (RemoteException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(intentService, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindCubeService();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(new CubeRenderer());
        setContentView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.open_gldemo, menu);
        return true;
    }

}
