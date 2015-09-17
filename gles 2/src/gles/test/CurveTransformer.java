package gles.test;

import android.graphics.Bitmap;

public class CurveTransformer {

    public Bitmap applyShader(Bitmap bmp, Bitmap bmp1, int red, int green, int blue, float alpha, String shader) {
        BitmapRenderer renderer = new BitmapRenderer(bmp, bmp1, red/256.0f, green/256.0f, blue/256.0f, alpha, shader);
	PixelBuffer pb = new PixelBuffer(bmp.getWidth(), bmp.getHeight());
	pb.setRenderer(renderer);
	return pb.getBitmap();
    }

    public Bitmap applyCurves(Bitmap bmp, int[] curvesR, int[] curvesG, int[] curvesB) {
	return null;
    }

    public Bitmap applyNormal(Bitmap bmp, int red, int green, int blue, float alpha) {
	return applyShader(bmp, null, red, green, blue, alpha, normalShader);
    }

    public Bitmap applyScreen(Bitmap bmp, int red, int green, int blue, float alpha) {
	return applyShader(bmp, null, red, green, blue, alpha, screenShader);
    }

    public Bitmap applyMultiply(Bitmap bmp, int red, int green, int blue, float alpha) {
	return applyShader(bmp, null, red, green, blue, alpha, multiplyShader);
    }

    public Bitmap applyHardlight(Bitmap bmp, int red, int green, int blue, float alpha) {
	return applyShader(bmp, null, red, green, blue, alpha, hardlightShader);
    }

    public Bitmap applyColorDodge(Bitmap bmp, int red, int green, int blue, float alpha) {
	return applyShader(bmp, null, red, green, blue, alpha, dodgeShader);
    }

    public Bitmap applyColorOverlay(Bitmap bmp, int red, int green, int blue, float alpha) {
	return applyShader(bmp, null, red, green, blue, alpha, overlayShader);
    }

    public Bitmap applySoftLight(Bitmap bmp, int red, int green, int blue, float alpha) {
	return applyShader(bmp, null, red, green, blue, alpha, softlightShader);
    }

    public Bitmap applySaturation(Bitmap bitmap, int r, int g, int b, float alpha) {
	return applyShader(bitmap, null, r, g, b, alpha, saturationShader);
    }

    public Bitmap applyHue(Bitmap bitmap, int r, int g, int b, float alpha) {
	return applyShader(bitmap, null, r, g, b, alpha, hueShader);
    }

    public Bitmap applyColor(Bitmap bitmap, int r, int g, int b, float alpha) {
	return applyShader(bitmap, null, r, g, b, alpha, colorShader);
    }

    public Bitmap applyTexture(Bitmap bitmap, Bitmap texture, int method, float alpha) {
	return applyFrame(bitmap, texture, method, alpha);
    }

    public Bitmap applyFrame(Bitmap bitmap, Bitmap frame, int method, float alpha) {
	switch(method) {
	case BlendMode.OVERLAY: return applyShader(bitmap, frame, 0, 0, 0, alpha, overlayShader);
	case BlendMode.MULTIPLY: return applyShader(bitmap, frame, 0, 0, 0, alpha, multiplyShader);
	case BlendMode.SCREEN: return applyShader(bitmap, frame, 0, 0, 0, alpha, screenShader);
	case BlendMode.HARDLIGHT: return applyShader(bitmap, frame, 0, 0, 0, alpha, hardlightShader);
	case BlendMode.SOFTLIGHT: return applyShader(bitmap, frame, 0, 0, 0, alpha, softlightShader);
	case BlendMode.COLORDODGE: return applyShader(bitmap, frame, 0, 0, 0, alpha, dodgeShader);
	case BlendMode.NORMAL: return applyShader(bitmap, frame, 0, 0, 0, alpha, normalShader);

	case BlendMode.COLOR: return applyShader(bitmap, frame, 0, 0, 0, alpha, colorShader);
	case BlendMode.HUE: return applyShader(bitmap, frame, 0, 0, 0, alpha, hueShader);
	case BlendMode.SATURATION: return applyShader(bitmap, frame, 0, 0, 0, alpha, saturationShader);
	case BlendMode.LIGHTEN: return applyShader(bitmap, frame, 0, 0, 0, alpha, lightenShader);
	}
	return null;
    }

    public static class BlendMode {
	public static final int OVERLAY = 0;
	public static final int MULTIPLY = 1;
	public static final int SCREEN = 2;
	public static final int HARDLIGHT = 3;
	public static final int SOFTLIGHT = 4;
	public static final int COLORDODGE = 5;
	public static final int NORMAL = 6;

	public static final int COLOR = 10;
	public static final int HUE = 11;
	public static final int SATURATION = 12;
	public static final int LIGHTEN = 13;
    }
    
    final String overlayShader = 
	"precision mediump float;" +
	"varying highp vec2 textureCoordinate1;" +
	"uniform sampler2D inputImageTexture1;" +
	
	"%s" +
 
	"void main()" +
	"{" +
	"  mediump vec4 base = texture2D(inputImageTexture1, textureCoordinate1);" +
	"  mediump vec4 overlay = %s" +
     
	"  mediump float ra;" +
	"  if (2.0 * base.r < base.a) {" +
	"    ra = 2.0 * overlay.r * base.r + overlay.r * (1.0 - base.a) + base.r * (1.0 - overlay.a);" +
	"  } else {" +
	"    ra = overlay.a * base.a - 2.0 * (base.a - base.r) * (overlay.a - overlay.r) + overlay.r * (1.0 - base.a) + base.r * (1.0 - overlay.a);" +
	"  }" +
     
	"  mediump float ga;" +
	"  if (2.0 * base.g < base.a) {" +
	"    ga = 2.0 * overlay.g * base.g + overlay.g * (1.0 - base.a) + base.g * (1.0 - overlay.a);" +
	"  } else {" +
	"    ga = overlay.a * base.a - 2.0 * (base.a - base.g) * (overlay.a - overlay.g) + overlay.g * (1.0 - base.a) + base.g * (1.0 - overlay.a);" +
	"  }" +
     
	"  mediump float ba;" +
	"  if (2.0 * base.b < base.a) {" +
	"    ba = 2.0 * overlay.b * base.b + overlay.b * (1.0 - base.a) + base.b * (1.0 - overlay.a);" +
	"  } else {" +
	"    ba = overlay.a * base.a - 2.0 * (base.a - base.b) * (overlay.a - overlay.b) + overlay.b * (1.0 - base.a) + base.b * (1.0 - overlay.a);" +
	"  }" +
     
	"  gl_FragColor = vec4(ra, ga, ba, 1.0);" +
	"}";

    final String multiplyShader = 
	"precision mediump float;" +
	"varying highp vec2 textureCoordinate1;" +
	"uniform sampler2D inputImageTexture1;" +
	
	"%s" +

	"void main()" +
	"{" +
	"  lowp vec4 base = texture2D(inputImageTexture1, textureCoordinate1);" +
	"  lowp vec4 overlayer = %s" +	
	"  gl_FragColor = overlayer * base + overlayer * (1.0 - base.a) + base * (1.0 - overlayer.a);" +
	"}";

    final String screenShader =
	"precision mediump float;" +
	"varying highp vec2 textureCoordinate1;" +
	"uniform sampler2D inputImageTexture1;" +
	
	"%s" +

	"void main()" +
	"{" +
	"  mediump vec4 textureColor = texture2D(inputImageTexture1, textureCoordinate1);" +
	"  mediump vec4 textureColor2 = %s" +
	"  mediump vec4 whiteColor = vec4(1.0);" +
	"  gl_FragColor = whiteColor - ((whiteColor - textureColor2) * (whiteColor - textureColor));" +
	"}";

    final String hardlightShader = 
	"precision mediump float;" +
	"varying highp vec2 textureCoordinate1;" +
	"uniform sampler2D inputImageTexture1;" +

	"%s" +
 
	"const highp vec3 W = vec3(0.2125, 0.7154, 0.0721);" +

	"void main()" +
	"{" +
	"  mediump vec4 base = texture2D(inputImageTexture1, textureCoordinate1);" +
	"  mediump vec4 overlay = %s" +

	"  highp float ra;" +
	"  if (2.0 * overlay.r < overlay.a) {" +
	"    ra = 2.0 * overlay.r * base.r + overlay.r * (1.0 - base.a) + base.r * (1.0 - overlay.a);" +
	"  } else {" +
	"    ra = overlay.a * base.a - 2.0 * (base.a - base.r) * (overlay.a - overlay.r) + overlay.r * (1.0 - base.a) + base.r * (1.0 - overlay.a);" +
	"  }" +
     
	"  highp float ga;" +
	"  if (2.0 * overlay.g < overlay.a) {" +
	"    ga = 2.0 * overlay.g * base.g + overlay.g * (1.0 - base.a) + base.g * (1.0 - overlay.a);" +
	"  } else {" +
	"    ga = overlay.a * base.a - 2.0 * (base.a - base.g) * (overlay.a - overlay.g) + overlay.g * (1.0 - base.a) + base.g * (1.0 - overlay.a);" +
	"  }" +
     
	"  highp float ba;" +
	"  if (2.0 * overlay.b < overlay.a) {" +
	"    ba = 2.0 * overlay.b * base.b + overlay.b * (1.0 - base.a) + base.b * (1.0 - overlay.a);" +
	"  } else {" +
	"    ba = overlay.a * base.a - 2.0 * (base.a - base.b) * (overlay.a - overlay.b) + overlay.b * (1.0 - base.a) + base.b * (1.0 - overlay.a);" +
	"  }" +
     
	"  gl_FragColor = vec4(ra, ga, ba, 1.0);" +
	"}";

    final String softlightShader = 
	"precision mediump float;" +
	"varying highp vec2 textureCoordinate1;" +
	"uniform sampler2D inputImageTexture1;" +

	"%s" +
 
	"void main()" +
	"{" +
	"  mediump vec4 base = texture2D(inputImageTexture1, textureCoordinate1);" +
	"  mediump vec4 overlay = %s" +
	
	"  gl_FragColor = base * (overlay.a * (base / base.a) + (2.0 * overlay * (1.0 - (base / base.a)))) + overlay * (1.0 - base.a) + base * (1.0 - overlay.a);" +
	"}";


    final String dodgeShader = 
	"precision mediump float;" +
	"varying highp vec2 textureCoordinate1;" +
	"uniform sampler2D inputImageTexture1;" +

	"%s" +
 
	"void main()" +
	"{" +
	"  vec4 base = texture2D(inputImageTexture1, textureCoordinate1);" +
	"  vec4 overlay = %s" +	
	"  vec3 baseOverlayAlphaProduct = vec3(overlay.a * base.a);" +
	"  vec3 rightHandProduct = overlay.rgb * (1.0 - base.a) + base.rgb * (1.0 - overlay.a);" +
	"  vec3 firstBlendColor = baseOverlayAlphaProduct + rightHandProduct;" +
	"  vec3 overlayRGB = clamp((overlay.rgb / clamp(overlay.a, 0.01, 1.0)) * step(0.0, overlay.a), 0.0, 0.99);" +
	"  vec3 secondBlendColor = (base.rgb * overlay.a) / (1.0 - overlayRGB) + rightHandProduct;" +
	"  vec3 colorChoice = step((overlay.rgb * base.a + base.rgb * overlay.a), baseOverlayAlphaProduct);" +
	"  gl_FragColor = vec4(mix(firstBlendColor, secondBlendColor, colorChoice), 1.0);" +
	"}";

    final String normalShader =
	"precision mediump float;" +
	"varying highp vec2 textureCoordinate1;" +
	"uniform sampler2D inputImageTexture1;" +

	"%s" +
 
	"void main()" +
	"{" +
	"  lowp vec4 c2 = texture2D(inputImageTexture1, textureCoordinate1);" +
	"  lowp vec4 c1 = %s" +
	"  lowp vec4 outputColor;" +
	"  outputColor.r = c1.r + c2.r * c2.a * (1.0 - c1.a);" +
	"  outputColor.g = c1.g + c2.g * c2.a * (1.0 - c1.a);" +
	"  outputColor.b = c1.b + c2.b * c2.a * (1.0 - c1.a);" +
	"  outputColor.a = c1.a + c2.a * (1.0 - c1.a);" +
	"  gl_FragColor = outputColor;" +
	"}";

    final String colorShader = 
	"precision mediump float;" +
	"varying highp vec2 textureCoordinate1;" +
	"uniform sampler2D inputImageTexture1;" +

	"%s" +
 
	"highp float lum(lowp vec3 c) {" +
	"  return dot(c, vec3(0.3, 0.59, 0.11));" +
	"}" +
 
	"lowp vec3 clipcolor(lowp vec3 c) {" +
	"  highp float l = lum(c);" +
	"  lowp float n = min(min(c.r, c.g), c.b);" +
	"  lowp float x = max(max(c.r, c.g), c.b);" +
     
	"  if (n < 0.0) {" +
	"    c.r = l + ((c.r - l) * l) / (l - n);" +
	"    c.g = l + ((c.g - l) * l) / (l - n);" +
	"    c.b = l + ((c.b - l) * l) / (l - n);" +
	"  }" +
	"  if (x > 1.0) {" +
	"    c.r = l + ((c.r - l) * (1.0 - l)) / (x - l);" +
	"    c.g = l + ((c.g - l) * (1.0 - l)) / (x - l);" +
	"    c.b = l + ((c.b - l) * (1.0 - l)) / (x - l);" +
	"  }" +
     
	"  return c;" +
	"}" +

	"lowp vec3 setlum(lowp vec3 c, highp float l) {" +
	"  highp float d = l - lum(c);" +
	"  c = c + vec3(d);" +
	"  return clipcolor(c);" +
	"}" +
 
	"void main()" +
	"{" +
	"  highp vec4 baseColor = texture2D(inputImageTexture1, textureCoordinate1);" +
	"  highp vec4 overlayColor = %s" +

	"  gl_FragColor = vec4(baseColor.rgb * (1.0 - overlayColor.a) + setlum(overlayColor.rgb, lum(baseColor.rgb)) * overlayColor.a, baseColor.a);" +
	"}";

    final String saturationShader = 
	"precision mediump float;" +
	"varying highp vec2 textureCoordinate1;" +
	"uniform sampler2D inputImageTexture1;" +

	"%s" +
 
	"highp float lum(lowp vec3 c) {" +
	"  return dot(c, vec3(0.3, 0.59, 0.11));" +
	"}" +
 
	"lowp vec3 clipcolor(lowp vec3 c) {" +
	"  highp float l = lum(c);" +
	"  lowp float n = min(min(c.r, c.g), c.b);" +
	"  lowp float x = max(max(c.r, c.g), c.b);" +
     
	"  if (n < 0.0) {" +
	"    c.r = l + ((c.r - l) * l) / (l - n);" +
	"    c.g = l + ((c.g - l) * l) / (l - n);" +
	"    c.b = l + ((c.b - l) * l) / (l - n);" +
	"  }" +
	"  if (x > 1.0) {" +
	"    c.r = l + ((c.r - l) * (1.0 - l)) / (x - l);" +
	"    c.g = l + ((c.g - l) * (1.0 - l)) / (x - l);" +
	"    c.b = l + ((c.b - l) * (1.0 - l)) / (x - l);" +
	"  }" +
     
	"  return c;" +
	"}" +
 
	"lowp vec3 setlum(lowp vec3 c, highp float l) {" +
	"  highp float d = l - lum(c);" +
	"  c = c + vec3(d);" +
	"  return clipcolor(c);" +
	"}" +
 
	"highp float sat(lowp vec3 c) {" +
	"  lowp float n = min(min(c.r, c.g), c.b);" +
	"  lowp float x = max(max(c.r, c.g), c.b);" +
	"  return x - n;" +
	"}" +
 
	"lowp float mid(lowp float cmin, lowp float cmid, lowp float cmax, highp float s) {" +
	"  return ((cmid - cmin) * s) / (cmax - cmin);" +
	"}" +
 
	"lowp vec3 setsat(lowp vec3 c, highp float s) {" +
	"  if (c.r > c.g) {" +
	"    if (c.r > c.b) {" +
	"      if (c.g > c.b) {" +
	/* g is mid, b is min */
	"        c.g = mid(c.b, c.g, c.r, s);" +
	"        c.b = 0.0;" +
	"      } else {" +
	/* b is mid, g is min */
	"        c.b = mid(c.g, c.b, c.r, s);" +
	"        c.g = 0.0;" +
	"      }" +
	"      c.r = s;" +
	"    } else {" +
	/* b is max, r is mid, g is min */
	"      c.r = mid(c.g, c.r, c.b, s);" +
	"      c.b = s;" +
	"      c.r = 0.0;" +
	"    }" +
	"  } else if (c.r > c.b) {" +
	/* g is max, r is mid, b is min */
	"    c.r = mid(c.b, c.r, c.g, s);" +
	"    c.g = s;" +
	"    c.b = 0.0;" +
	"  } else if (c.g > c.b) {" +
	/* g is max, b is mid, r is min */
	"    c.b = mid(c.r, c.b, c.g, s);" +
	"    c.g = s;" +
	"    c.r = 0.0;" +
	"  } else if (c.b > c.g) {" +
	/* b is max, g is mid, r is min */
	"    c.g = mid(c.r, c.g, c.b, s);" +
	"    c.b = s;" +
	"    c.r = 0.0;" +
	"  } else {" +
	"    c = vec3(0.0);" +
	"  }" +
	"  return c;" +
	"}" +
 
	"void main()" +
	"{" +
	"  highp vec4 baseColor = texture2D(inputImageTexture1, textureCoordinate1);" +
	"  highp vec4 overlayColor = %s" +     
	"  gl_FragColor = vec4(baseColor.rgb * (1.0 - overlayColor.a) + setlum(setsat(baseColor.rgb, sat(overlayColor.rgb)), lum(baseColor.rgb)) * overlayColor.a, baseColor.a);" +
	"}";
    final String hueShader = 
	"precision mediump float;" +
	"varying highp vec2 textureCoordinate1;" +
	"uniform sampler2D inputImageTexture1;" +

	"%s" +
 
	"highp float lum(lowp vec3 c) {" +
	"  return dot(c, vec3(0.3, 0.59, 0.11));" +
	"}" +
 
	"lowp vec3 clipcolor(lowp vec3 c) {" +
	"  highp float l = lum(c);" +
	"  lowp float n = min(min(c.r, c.g), c.b);" +
	"  lowp float x = max(max(c.r, c.g), c.b);" +
     
	"  if (n < 0.0) {" +
	"    c.r = l + ((c.r - l) * l) / (l - n);" +
	"    c.g = l + ((c.g - l) * l) / (l - n);" +
	"    c.b = l + ((c.b - l) * l) / (l - n);" +
	"  }" +
	"  if (x > 1.0) {" +
	"    c.r = l + ((c.r - l) * (1.0 - l)) / (x - l);" +
	"    c.g = l + ((c.g - l) * (1.0 - l)) / (x - l);" +
	"    c.b = l + ((c.b - l) * (1.0 - l)) / (x - l);" +
	"  }" +
     
	"  return c;" +
	"}" +
 
	"lowp vec3 setlum(lowp vec3 c, highp float l) {" +
	"  highp float d = l - lum(c);" +
	"  c = c + vec3(d);" +
	"  return clipcolor(c);" +
	"}" +
 
	"highp float sat(lowp vec3 c) {" +
	"  lowp float n = min(min(c.r, c.g), c.b);" +
	"  lowp float x = max(max(c.r, c.g), c.b);" +
	"  return x - n;" +
	"}" +
 
	"lowp float mid(lowp float cmin, lowp float cmid, lowp float cmax, highp float s) {" +
	"  return ((cmid - cmin) * s) / (cmax - cmin);" +
	"}" +
 
	"lowp vec3 setsat(lowp vec3 c, highp float s) {" +
	"  if (c.r > c.g) {" +
	"    if (c.r > c.b) {" +
	"      if (c.g > c.b) {" +
	/* g is mid, b is min */
	"        c.g = mid(c.b, c.g, c.r, s);" +
	"        c.b = 0.0;" +
	"      } else {" +
	/* b is mid, g is min */
	"        c.b = mid(c.g, c.b, c.r, s);" +
	"        c.g = 0.0;" +
	"      }" +
	"      c.r = s;" +
	"    } else {" +
	/* b is max, r is mid, g is min */
	"      c.r = mid(c.g, c.r, c.b, s);" +
	"      c.b = s;" +
	"      c.r = 0.0;" +
	"    }" +
	"  } else if (c.r > c.b) {" +
	/* g is max, r is mid, b is min */
	"    c.r = mid(c.b, c.r, c.g, s);" +
	"    c.g = s;" +
	"    c.b = 0.0;" +
	"  } else if (c.g > c.b) {" +
	/* g is max, b is mid, r is min */
	"    c.b = mid(c.r, c.b, c.g, s);" +
	"    c.g = s;" +
	"    c.r = 0.0;" +
	"  } else if (c.b > c.g) {" +
	/* b is max, g is mid, r is min */
	"    c.g = mid(c.r, c.g, c.b, s);" +
	"    c.b = s;" +
	"    c.r = 0.0;" +
	"  } else {" +
	"    c = vec3(0.0);" +
	"  }" +
	"  return c;" +
	"}" +
 
	"void main()" +
	"{" +
	"  highp vec4 baseColor = texture2D(inputImageTexture1, textureCoordinate1);" +
	"  highp vec4 overlayColor = %s" +
     
	"  gl_FragColor = vec4(baseColor.rgb * (1.0 - overlayColor.a) + setlum(setsat(overlayColor.rgb, sat(baseColor.rgb)), lum(baseColor.rgb)) * overlayColor.a, baseColor.a);" +
	"}";

    final String lightenShader = 
	"precision mediump float;" +
	"varying highp vec2 textureCoordinate1;" +
	"uniform sampler2D inputImageTexture1;" +

	"%s" +
 
	"void main()" +
	"{" +
	"  lowp vec4 textureColor = texture2D(inputImageTexture1, textureCoordinate1);" +
	"  lowp vec4 textureColor2 = %s" +
    
	"  gl_FragColor = max(textureColor, textureColor2);" +
	"}";
}