package gles.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.opengl.GLSurfaceView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ImageView;

public class GLES extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
	Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.image);
	Bitmap frame = BitmapFactory.decodeResource(getResources(), R.drawable.frame);

	CurveTransformer ct = new CurveTransformer();

	// Bitmap nImg = ct.applyColorOverlay(img, 255, 0, 0, 1.0f);
	// Bitmap nImg = ct.applyMultiply(img, 255, 0, 0, 1.0f);
	// Bitmap nImg = ct.applyScreen(img, 255, 0, 0, 1.0f);
	// Bitmap nImg = ct.applyHardlight(img, 255, 0, 0, 0.5f);
	// Bitmap nImg = ct.applySoftLight(img, 255, 0, 0, 1.0f);
	// Bitmap nImg = ct.applyColorDodge(img, 255, 0, 0, 1.0f);
	// Bitmap nImg = ct.applyNormal(img, 255, 0, 0, 0.5f);
	// Bitmap nImg = ct.applyColor(img, 255, 0, 0, 1.0f);
	// Bitmap nImg = ct.applyHue(img, 255, 0, 0, 1.0f);
	// Bitmap nImg = ct.applySaturation(img, 255, 0, 0, 1.0f);

	// Bitmap nImg = ct.applyTexture(img, frame, CurveTransformer.BlendMode.OVERLAY, 1.0f);
	// Bitmap nImg = ct.applyTexture(img, frame, CurveTransformer.BlendMode.MULTIPLY, 1.0f);
	// Bitmap nImg = ct.applyTexture(img, frame, CurveTransformer.BlendMode.SCREEN, 1.0f);
	// Bitmap nImg = ct.applyTexture(img, frame, CurveTransformer.BlendMode.HARDLIGHT, 1.0f);
	// Bitmap nImg = ct.applyTexture(img, frame, CurveTransformer.BlendMode.SOFTLIGHT, 1.0f);
	// Bitmap nImg = ct.applyTexture(img, frame, CurveTransformer.BlendMode.COLORDODGE, 1.0f);
	// Bitmap nImg = ct.applyTexture(img, frame, CurveTransformer.BlendMode.NORMAL, 1.0f);
	// Bitmap nImg = ct.applyTexture(img, frame, CurveTransformer.BlendMode.COLOR, 1.0f);
	// Bitmap nImg = ct.applyTexture(img, frame, CurveTransformer.BlendMode.HUE, 1.0f);
	// Bitmap nImg = ct.applyTexture(img, frame, CurveTransformer.BlendMode.SATURATION, 1.0f);
	 Bitmap nImg = ct.applyTexture(img, frame, CurveTransformer.BlendMode.LIGHTEN, 1.0f);

	ImageView imageView = new ImageView(this);
	LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	imageView.setLayoutParams(lp);
	imageView.setImageBitmap(nImg);
	setContentView(imageView);

	//	setContentView(preview(img));
    }

    private GLSurfaceView preview(Bitmap bmp) {
	GLSurfaceView view = new GLSurfaceView(this);
	BitmapRenderer renderer = new BitmapRenderer(bmp, null, 1.0f, 1.0f, 0.0f, 1.0f, testShader);
	view.setEGLContextClientVersion(2);
	view.setRenderer(renderer);
	return view;
    }

    final String testShader = 
	//	"precision mediump float;" +
	"varying highp vec2 textureCoordinate1;" +
	"uniform sampler2D inputImageTexture1;" +
 
	"void main()" +
	"{" +
	"  gl_FragColor = texture2D(inputImageTexture1, textureCoordinate1);" +
	"}";
}
