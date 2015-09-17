package gles.test;

import android.app.Activity;
import android.graphics.*;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GLES extends Activity {
    /**
     * Called when the activity is first created.
     */

    private class MTask extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... params) {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inMutable = true;
            Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.control_table, opts);
            //Bitmap frame = BitmapFactory.decodeResource(getResources(), R.drawable.frame);
            CurveTransformer ct = new CurveTransformer();
            /*
            "blend":"colordodge",
                "strength":100,
                "type":"color",
                "values":{
                    "r":75,
                    "g":60,
                    "b":40
            * */
            //return ct.applyColorDodge(img, 75, 60, 40, 1.0f);
            //return ct.applyScreen(img, 75, 60, 40, 1.0f);
            //return ct.applyMultiply(img, 75, 60, 40, 1.0f);
            //return ct.applyHardlight(img, 75, 60, 40, 1.0f);
            //return ct.applyColorOverlay(img, 75, 60, 40, 1.0f);
            //return ct.applySoftLight(img, 75, 60, 40, 1.0f);
            //return ct.applySaturation(img, 75, 60, 40, 1.0f);
            //return ct.applyHue(img, 75, 60, 40, 1.0f);
            //return ct.applyColor(img, 240, 0, 255, 0.1f);

            //return ct.applyColorOverlay(img, 240, 0, 255, 1f);
            //return ct.applyHardlight(img, 240, 0, 255, 0.1f);
            return ct.applyHardlight(img, 75, 60, 40, 0.1f);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ImageView imageView = new ImageView(GLES.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(lp);
            imageView.setImageBitmap(bitmap);
            setContentView(imageView);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new MTask().execute();

        /*BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inMutable = true;
        Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.image, opts);
        Bitmap frame = BitmapFactory.decodeResource(getResources(), R.drawable.frame);

        CurveTransformer ct = new CurveTransformer();*/

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
        //Saturator saturator = new Saturator(-100, -100, -100);

        /*Bitmap nImg = applyColorMatrixFilter(saturator.toMatrix(), img);
        nImg = ct.applyColorOverlay(nImg, 255, 100, 0, 0.25f);*/
        /*Bitmap nImg = ct.applyFrame(img, frame, CurveTransformer.BlendMode.OVERLAY, 1.0f);*/
        //nImg = ct.applyColorOverlay(nImg, 255, 100, 0, 0.25f);
        /*
        sepia
        {
                "strength":100,
                "type":"saturation",
                "values":{
                    "r":-100,
                    "g":-100,
                    "b":-100
                }
            },
            {
                "blend":"overlay",
                "strength":25,
                "type":"color",
                "values":{
                    "r":255,
                    "g":100,
                    "b":0
                }
            }
        * */
        //Bitmap nImg = ct.applyTexture(img, frame, CurveTransformer.BlendMode.LIGHTEN, 1.0f);

        /*ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(lp);
        imageView.setImageBitmap(nImg);
        setContentView(imageView);*/

        //	setContentView(preview(img));
    }


    private Bitmap applyColorMatrixFilter(float[] colorMatrixFilter, Bitmap bitmap) {
        ColorMatrix colorMatrix = new ColorMatrix();
        ColorMatrix post = new ColorMatrix(colorMatrixFilter);
        colorMatrix.postConcat(post);

        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        Paint paintWithColorFilter = new Paint();

        paintWithColorFilter.setColorFilter(colorFilter);
        return applyFilter(paintWithColorFilter, bitmap);
    }

    private Bitmap applyFilter(Paint paint, Bitmap bitmap) {
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bitmap;
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

    private class Saturator {
        //strength always 100%
        //private float strength = 1;
        private int red;
        private int green;
        private int blue;

        private Saturator(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        //r == rValue / 100 etc
        public float[] toMatrix() {

            float r = (float) red / 100;
            float g = (float) green / 100;
            float b = (float) blue / 100;

            r++;
            r = Math.min(r, 2);
            r = Math.max(r, 0);
            g++;
            g = Math.min(g, 2);
            g = Math.max(g, 0);
            b++;
            b = Math.min(b, 2);
            b = Math.max(b, 0);

            float[][] mat = new float[5][5];
            identMatrix(mat);
            saturateMatrix(mat, r, g, b);

            return toColorMatrix(mat);
        }

        private void saturateMatrix(float[][] mat, float satRed, float satGreen, float satBlue) {
            float a, b, c, d, e, f, g, h, i;
            float rwgt, gwgt, bwgt;

            rwgt = RLUM;
            gwgt = GLUM;
            bwgt = BLUM;

            a = (1 - satRed) * rwgt + satRed;
            b = (1 - satRed) * rwgt;
            c = (1 - satRed) * rwgt;

            d = (1 - satGreen) * gwgt;
            e = (1 - satGreen) * gwgt + satGreen;
            f = (1 - satGreen) * gwgt;

            g = (1 - satBlue) * bwgt;
            h = (1 - satBlue) * bwgt;
            i = (1 - satBlue) * bwgt + satBlue;

            float[][] mmat = new float[5][5];
            mmat[0] = new float[]{a, b, c, 0, 0};
            mmat[1] = new float[]{d, e, f, 0, 0};
            mmat[2] = new float[]{g, h, i, 0, 0};
            mmat[3] = new float[]{0, 0, 0, 1, 0};
            mmat[4] = new float[]{0, 0, 0, 0, 1};

            multMatrix(mmat, mat, mat);
        }

        public void identMatrix(float[][] matrix) {
            matrix[0][0] = 1;
            matrix[0][1] = 0;
            matrix[0][2] = 0;
            matrix[0][3] = 0;
            matrix[0][4] = 0;

            matrix[1][0] = 0;
            matrix[1][1] = 1;
            matrix[1][2] = 0;
            matrix[1][3] = 0;
            matrix[1][4] = 0;

            matrix[2][0] = 0;
            matrix[2][1] = 0;
            matrix[2][2] = 1;
            matrix[2][3] = 0;
            matrix[2][4] = 0;

            matrix[3][0] = 0;
            matrix[3][1] = 0;
            matrix[3][2] = 0;
            matrix[3][3] = 1;
            matrix[3][4] = 0;

            matrix[4][0] = 0;
            matrix[4][1] = 0;
            matrix[4][2] = 0;
            matrix[4][3] = 0;
            matrix[4][4] = 1;
        }

        public void multMatrix(float[][] a, float[][] b, float[][] c) {
            int x, y;
            float[][] temp = new float[5][5];
            for (y = 0; y < 5; y++)
                for (x = 0; x < 5; x++) {
                    temp[y][x] = b[y][0] * a[0][x]
                            + b[y][1] * a[1][x]
                            + b[y][2] * a[2][x]
                            + b[y][3] * a[3][x]
                            + b[y][4] * a[4][x];
                }
            for (y = 0; y < 5; y++)
                for (x = 0; x < 5; x++)
                    c[y][x] = temp[y][x];
        }

        public float[] toColorMatrix(float[][] mat) {
            return new float[]{
                    //red       green       blue   alpha          e
                    mat[0][0], mat[1][0], mat[2][0], 0, (mat[3][0] + mat[4][0]),
                    mat[0][1], mat[1][1], mat[2][1], 0, (mat[3][1] + mat[4][1]),
                    mat[0][2], mat[1][2], mat[2][2], 0, (mat[3][2] + mat[4][2]),
                    0, 0, 0, 1, 0
            };
        }

        public final float RLUM = 0.212671f;
        public final float GLUM = 0.715160f;
        public final float BLUM = 0.072169f;
    }
}
