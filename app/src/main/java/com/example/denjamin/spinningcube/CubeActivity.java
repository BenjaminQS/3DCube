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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class CubeActivity extends Activity {

    private ServiceConnection conn;
    private ICubeInterface iCubeInterface;
    public String cubeStringData = "[{\"id\":\"ITEM - 1\",\"w\":48,\"h\":40,\"d\":24,\"coordinates\":{\"x1\":\"0\",\"y1\":\"0\",\"z1\":\"0\",\"x2\":40,\"y2\":48,\"z2\":24}},{\"id\":\"ITEM - 1\",\"w\":48,\"h\":40,\"d\":24,\"coordinates\":{\"x1\":\"0\",\"y1\":\"0\",\"z1\":\"24\",\"x2\":40,\"y2\":48,\"z2\":48}},{\"id\":\"ITEM - 1\",\"w\":48,\"h\":40,\"d\":24,\"coordinates\":{\"x1\":\"40\",\"y1\":\"0\",\"z1\":\"0\",\"x2\":80,\"y2\":48,\"z2\":24}},{\"id\":\"ITEM - 1\",\"w\":48,\"h\":40,\"d\":24,\"wg\":0,\"coordinates\":{\"x1\":\"40\",\"y1\":\"0\",\"z1\":\"24\",\"x2\":80,\"y2\":48,\"z2\":48}},{\"id\":\"ITEM - 4\",\"w\":48,\"h\":32,\"d\":32,\"coordinates\":{\"x1\":\"0\",\"y1\":\"48\",\"z1\":\"0\",\"x2\":48,\"y2\":80,\"z2\":32}},{\"id\":\"ITEM - 1\",\"w\":48,\"h\":40,\"d\":24,\"coordinates\":{\"x1\":\"80\",\"y1\":\"0\",\"z1\":\"0\",\"x2\":120,\"y2\":48,\"z2\":24}},{\"id\":\"ITEM - 2\",\"w\":32,\"h\":32,\"d\":24,\"coordinates\":{\"x1\":\"0\",\"y1\":\"48\",\"z1\":\"32\",\"x2\":32,\"y2\":80,\"z2\":56}},{\"id\":\"ITEM - 4\",\"w\":48,\"h\":32,\"d\":32,\"coordinates\":{\"x1\":\"80\",\"y1\":\"0\",\"z1\":\"24\",\"x2\":112,\"y2\":48,\"z2\":56}},{\"id\":\"ITEM - 3\",\"w\":48,\"h\":24,\"d\":16,\"coordinates\":{\"x1\":\"112\",\"y1\":\"0\",\"z1\":\"24\",\"x2\":128,\"y2\":48,\"z2\":48}},{\"id\":\"ITEM - 4\",\"w\":48,\"h\":32,\"d\":32,\"coordinates\":{\"x1\":\"48\",\"y1\":\"48\",\"z1\":\"0\",\"x2\":96,\"y2\":80,\"z2\":32}},{\"id\":\"ITEM - 2\",\"w\":32,\"h\":32,\"d\":24,\"coordinates\":{\"x1\":\"96\",\"y1\":\"48\",\"z1\":\"0\",\"x2\":128,\"y2\":80,\"z2\":24}},{\"id\":\"ITEM - 2\",\"w\":32,\"h\":32,\"d\":24,\"coordinates\":{\"x1\":\"32\",\"y1\":\"48\",\"z1\":\"32\",\"x2\":64,\"y2\":80,\"z2\":56}},{\"id\":\"ITEM - 2\",\"w\":32,\"h\":32,\"d\":24,\"coordinates\":{\"x1\":\"64\",\"y1\":\"48\",\"z1\":\"32\",\"x2\":96,\"y2\":80,\"z2\":56}},{\"id\":\"ITEM - 2\",\"w\":32,\"h\":32,\"d\":24,\"coordinates\":{\"x1\":\"96\",\"y1\":\"48\",\"z1\":\"24\",\"x2\":128,\"y2\":80,\"z2\":48}}]";
    private FloatBuffer vertexBuffer;
    private List<int[]> cubeDataList = new ArrayList<int[]>();
    private List<float[]> cubeVertexList = new ArrayList<float[]>();
    private final float multi = 3.0f;
    private final  float coreX = 0.5f;
    private final  float coreY = 0.3125f;
    private final  float coreZ = 0.21875f;
    private final  float baseNum = 128f;
    private final  int hIndex = 0;
    private final  int wIndex = 1;
    private final  int dIndex = 2;
    private final  int x1Index = 3;
    private final  int y1Index = 4;
    private final  int z1Index = 5;
    private final  int x2Index = 6;
    private final  int y2Index = 7;
    private final  int z2Index = 8;
    private int cubeIndex = 0;



    private void setVerticles(int index ) throws Exception {
        float v0X = 0;
        float v0Y = 0;
        float v0Z = 0;
        float v1X = 0;
        float v1Y = 0;
        float v1Z = 0;
        float v2X = 0;
        float v2Y = 0;
        float v2Z = 0;
        float v3X = 0;
        float v3Y = 0;
        float v3Z = 0;
        float v4X = 0;
        float v4Y = 0;
        float v4Z = 0;
        float v5X = 0;
        float v5Y = 0;
        float v5Z = 0;
        float v6X = 0;
        float v6Y = 0;
        float v6Z = 0;
        float v7X = 0;
        float v7Y = 0;
        float v7Z = 0;


        v0X = ((cubeDataList.get(index)[x1Index]/baseNum)-coreX)*multi;
        v0Y = ((cubeDataList.get(index)[y1Index]/baseNum)-coreY)*multi;
        v0Z = ((cubeDataList.get(index)[z1Index]/baseNum)-coreZ)*multi;

        v1X = (cubeDataList.get(index)[x2Index]/baseNum -coreX)*multi;
        v1Y = ((cubeDataList.get(index)[y2Index]-cubeDataList.get(index)[hIndex])/baseNum - coreY)*multi;
        v1Z = ((cubeDataList.get(index)[z2Index]- cubeDataList.get(index)[dIndex])/baseNum - coreZ)*multi;

        v2X = (cubeDataList.get(index)[x2Index]/baseNum -coreX)*multi;
        v2Y = ((cubeDataList.get(index)[y2Index])/baseNum - coreY)*multi;
        v2Z = ((cubeDataList.get(index)[z2Index]- cubeDataList.get(index)[dIndex])/baseNum - coreZ)*multi;

        v3X = ((cubeDataList.get(index)[x1Index]/baseNum)-coreX)*multi;
        v3Y = ((cubeDataList.get(index)[y1Index] + cubeDataList.get(index)[hIndex])/baseNum-coreY)*multi;
        v3Z = ((cubeDataList.get(index)[z1Index]/baseNum)-coreZ)*multi;

        v4X = ((cubeDataList.get(index)[x1Index]/baseNum)-coreX)*multi;
        v4Y = ((cubeDataList.get(index)[y1Index] )/baseNum-coreY)*multi;
        v4Z = ((cubeDataList.get(index)[z1Index] +  cubeDataList.get(index)[dIndex])/baseNum-coreZ)*multi;

        v5X = (cubeDataList.get(index)[x2Index]/baseNum -coreX)*multi;
        v5Y = ((cubeDataList.get(index)[y2Index]-cubeDataList.get(index)[hIndex])/baseNum - coreY)*multi;
        v5Z = (cubeDataList.get(index)[z2Index]/baseNum - coreZ)*multi;

        v6X = (cubeDataList.get(index)[x2Index]/baseNum -coreX)*multi;
        v6Y = (cubeDataList.get(index)[y2Index]/baseNum - coreY)*multi;
        v6Z = (cubeDataList.get(index)[z2Index]/baseNum - coreZ)*multi;

        v7X = ((cubeDataList.get(index)[x1Index]/baseNum)-coreX)*multi;
        v7Y = ((cubeDataList.get(index)[y1Index] +  cubeDataList.get(index)[hIndex])/baseNum-coreY)*multi;
        v7Z = ((cubeDataList.get(index)[z1Index] +  cubeDataList.get(index)[dIndex])/baseNum-coreZ)*multi;

        float vertices[] = {
                v0X, v0Y, v0Z,
                v1X, v1Y, v1Z,
                v2X, v2Y, v2Z,
                v3X, v3Y, v3Z,
                v4X, v4Y, v4Z,
                v5X, v5Y, v5Z,
                v6X, v6Y, v6Z,
                v7X, v7Y, v7Z
//                0.5f, -0.5f, -0.5f,
//                0.5f, -0.5f, -0.5f,
//                0.5f, 0.5f, -0.5f,
//                -0.5f, 0.5f, -0.5f,
//                -0.5f, -0.5f, 0.5f,
//                0.5f, -0.5f, 0.5f,
//                0.5f, 0.5f, 0.5f,
//                -0.5f, 0.5f, 0.5f
        };


        ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuf.asFloatBuffer();
        vertexBuffer.put(vertices);
    }

    private void setCubeDataList() throws  Exception{
        JSONArray jsonArray = new JSONArray(cubeStringData);
        //System.out.println("jsonArray.length:" + jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            int[] cubeStringData = new int[9];
            JSONObject jObject = jsonArray.getJSONObject(i);
            cubeStringData[0] = jObject.getInt("w");
            cubeStringData[1] = jObject.getInt("h");
            cubeStringData[2] = jObject.getInt("d");
            JSONObject coordJObject = jObject.getJSONObject("coordinates");
            cubeStringData[3] = (coordJObject.getInt("x1"));
            cubeStringData[4] = (coordJObject.getInt("y1"));
            cubeStringData[5] = (coordJObject.getInt("z1"));
            cubeStringData[6] = (coordJObject.getInt("x2"));
            cubeStringData[7] = (coordJObject.getInt("y2"));
            cubeStringData[8] = (coordJObject.getInt("z2"));
            cubeDataList.add(cubeStringData);
        }
    }





    private void bindCubeService(){
        Intent intentService =  new Intent();
        intentService.setClassName(this,"com.example.denjamin.spinningcube.InnerCubeService");
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder iBinder) {
                iCubeInterface = ICubeInterface.Stub.asInterface(iBinder);
                try{
                    cubeStringData = iCubeInterface.getCubeVertexInfo();
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
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        bindCubeService();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        GLSurfaceView view = new GLSurfaceView(this);
        try {
            setCubeDataList();
//            setVerticles(cubeIndex);
        }catch (Exception e){
            e.printStackTrace();
        }
//        view.setRenderer(new CubeRenderer(new InnerCube(vertexBuffer)));
//        setContentView(view);
    }

    @Override
    public void onResume() {
        GLSurfaceView view = new GLSurfaceView(this);
        try {
//            setCubeDataList();
            setVerticles(cubeIndex);
        }catch (Exception e){
            e.printStackTrace();
        }
        view.setRenderer(new CubeRenderer(new InnerCube(vertexBuffer)));
        setContentView(view);


        super.onResume();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.open_gldemo, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.next:
//                cubeIndex ++;
//                return true;
//            case R.id.prev:
//                cubeIndex--;
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            cubeIndex ++;
            GLSurfaceView view = new GLSurfaceView(this);
            try {
//            setCubeDataList();
                setVerticles(cubeIndex);
            }catch (Exception e){
                e.printStackTrace();
            }
            view.setRenderer(new CubeRenderer(new InnerCube(vertexBuffer)));
            setContentView(view);
        }
        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            cubeIndex --;
            GLSurfaceView view = new GLSurfaceView(this);
            try {
//            setCubeDataList();
                setVerticles(cubeIndex);
            }catch (Exception e){
                e.printStackTrace();
            }
            view.setRenderer(new CubeRenderer(new InnerCube(vertexBuffer)));
            setContentView(view);
        }

        return super.onKeyDown(keyCode, event);
    }

}
