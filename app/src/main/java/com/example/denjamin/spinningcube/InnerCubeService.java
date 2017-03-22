package com.example.denjamin.spinningcube;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by I324402 on 3/20/2017.
 */

public class InnerCubeService extends Service {


    private boolean threadExecute = true;
    private boolean isConnected = false;
    private int currIndex;
    private int totalSteps = 0;
    String packingData = "Hi";




    ICubeInterface.Stub binder = new ICubeInterface.Stub(){

        public int getCurrentIndex(){
            return  currIndex;
        }
        public int getTotalSteps(){
            return totalSteps;
        }
        public void setCurrentIndex(int index){
            currIndex = index;
        }
        public String getCubeVertexInfo( ){
            return packingData;
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    @Override
    public void onCreate() {
        Thread t = new GetBoxLoadThread();
        t.start();
        super.onCreate();
    }

    public String getPackingData(String urlPath) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = -1;
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(30000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.connect();
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 210){
            isConnected = true;
        }
        else{
            isConnected = false;
        }
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        return new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
    }




    class GetBoxLoadThread extends Thread {
        @Override
        public void run() {
            String packingDataTemp = null;

            while (threadExecute) {
                try {
                    if (packingData == null || !packingData.equals(packingDataTemp)) {
                        packingData = getPackingData("http://10.207.199.72:8080/ServletJson0120/ServletJson0120/JsonTest");
                    }

//                    if (packingData != null) {
//                        if(!packingData.equals(packingDataTemp)) {
//                            cubeList.clear();
//                            //cubeAarryList.clear();
//                            //boxIdList.clear();
//                            InnerCubeBean innerBean = new InnerCubeBean();
//                            JSONArray jsonArray = new JSONArray(packingData);
//
//                            System.out.println("jsonArray.length:" + jsonArray.length());
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                int[] cubeData = new int[9];
//                                JSONObject jObject = jsonArray.getJSONObject(i);
//                                cubeData[0] = jObject.getInt("w");
//                                cubeData[1] = jObject.getInt("h");
//                                cubeData[2] = jObject.getInt("d");
//                                JSONObject coordJObject = jObject.getJSONObject("coordinates");
//                                cubeData[3] = (coordJObject.getInt("x1"));
//                                cubeData[4] = (coordJObject.getInt("y1"));
//                                cubeData[5] = (coordJObject.getInt("z1"));
//                                cubeData[6] = (coordJObject.getInt("x2"));
//                                cubeData[7] = (coordJObject.getInt("y2"));
//                                cubeData[8] = (coordJObject.getInt("z2"));
//                                cubeList.add(cubeData);
//                                totalSteps = i;
//                            }
//                        }
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
