package com.example.denjamin.spinningcube;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class OuterCube {

	private FloatBuffer mVertexBuffer;
	private FloatBuffer mColorBuffer;
	private ByteBuffer  mIndexBuffer;

	private float vertices[] = {
//			-1.0f, -1.0f, -1.0f,
//			1.0f, -1.0f, -1.0f,
//			1.0f,  1.0f, -1.0f,
//			-1.0f, 1.0f, -1.0f,
//			-1.0f, -1.0f,  1.0f,
//			1.0f, -1.0f,  1.0f,
//			1.0f,  1.0f,  1.0f,
//			-1.0f,  1.0f,  1.0f

			-1.5f, -0.9375f, -0.65625f,
			1.5f, -0.9375f, -0.65625f,
			1.5f,  0.9375f, -0.65625f,
			-1.5f, 0.9375f, -0.65625f,
			-1.5f, -0.9375f,  0.65625f,
			1.5f, -0.9375f,  0.65625f,
			1.5f,  0.9375f,  0.65625f,
			-1.5f,  0.9375f,  0.65625f
	};
	private float colors[] = {
			0.0f,  1.0f,  0.0f,  0.0f,
			0.0f,  1.0f,  0.0f,  0.0f,
			0.0f,  1.0f,  0.0f,  0.0f,
			0.0f,  1.0f,  0.0f,  0.0f,
			0.0f,  1.0f,  0.0f,  0.0f,
			0.0f,  1.0f,  0.0f,  0.0f,
			0.0f,  1.0f,  0.0f,  0.0f,
			0.0f,  1.0f,  0.0f,  0.0f,
			0.0f,  1.0f,  0.0f,  0.0f,
			0.0f,  1.0f,  0.0f,  0.0f,
			0.0f,  1.0f,  0.0f,  0.0f,
			0.0f,  1.0f,  0.0f,  0.0f
	};

	private byte indices[] = {
//			0, 4, 5, 0, 5, 1,
//			1, 5, 6, 1, 6, 2,
//			2, 6, 7, 2, 7, 3,
//			3, 7, 4, 3, 4, 0,
//			4, 7, 6, 4, 6, 5,
//			3, 0, 1, 3, 1, 2
			0,1,
			1,2,
			2,3,
			3,0,
			1,5,
			2,6,
			3,7,
			0,4,
			4,5,
			5,6,
			6,7,
			7,4
	};

	public OuterCube() {
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		mVertexBuffer = byteBuf.asFloatBuffer();
		mVertexBuffer.put(vertices);
		mVertexBuffer.position(0);

		byteBuf = ByteBuffer.allocateDirect(colors.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		mColorBuffer = byteBuf.asFloatBuffer();
		mColorBuffer.put(colors);
		mColorBuffer.position(0);

		mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
		mIndexBuffer.put(indices);
		mIndexBuffer.position(0);
	}

	public void draw(GL10 gl) {
//		glEnable(GL_BLEND);
//		glBlendFunc(GL_SRC_ALPHA, GL_ONE);
		gl.glFrontFace(GL10.GL_CW);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDrawElements(GL10.GL_LINES, 24, GL10.GL_UNSIGNED_BYTE,
				mIndexBuffer);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}
