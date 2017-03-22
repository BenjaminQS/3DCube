// ICubeInterface.aidl
package com.example.denjamin.spinningcube;



// Declare any non-default types here with import statements

interface ICubeInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    //List<ArrayList> getCubeList();
    int getCurrentIndex();
    int getTotalSteps();
    void setCurrentIndex(int index);
    String getCubeVertexInfo();
}
