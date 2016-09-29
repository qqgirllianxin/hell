package ps.hell.math.base.persons.fft.transform.dct;//package ps.landerbuluse.ml.math.fft.transform.dct;
//
//import edu.emory.mathcs.utils.IOUtils;
//import java.io.PrintStream;
//
//public class AccuracyCheckFloatDCT {
//	private static int[] sizes1D = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
//			16, 32, 64, 100, 120, 128, 256, 310, 512, 1024, 1056, 2048, 8192,
//			10158, 16384, 32768, 65536, 131072 };
//	private static int[] sizes2D = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
//			16, 32, 64, 100, 120, 128, 256, 310, 511, 512, 1024 };
//	private static int[] sizes3D = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
//			16, 32, 64, 100, 128 };
//	private static double eps = Math.pow(2.0D, -23.0D);
//
//	public static void checkAccuracyDCT_1D() {
//		System.out.println("Checking accuracy of 1D DCT...");
//		for (int i = 0; i < sizes1D.length; ++i) {
//			FloatDCT_1D localFloatDCT_1D = new FloatDCT_1D(sizes1D[i]);
//			double d = 0.0D;
//			float[] arrayOfFloat1 = new float[sizes1D[i]];
//			IOUtils.fillMatrix_1D(sizes1D[i], arrayOfFloat1);
//			float[] arrayOfFloat2 = new float[sizes1D[i]];
//			IOUtils.fillMatrix_1D(sizes1D[i], arrayOfFloat2);
//			localFloatDCT_1D.forward(arrayOfFloat1, true);
//			localFloatDCT_1D.inverse(arrayOfFloat1, true);
//			d = computeRMSE(arrayOfFloat1, arrayOfFloat2);
//			if (d > eps)
//				System.err
//						.println("\tsize = " + sizes1D[i] + ";\terror = " + d);
//			else
//				System.out
//						.println("\tsize = " + sizes1D[i] + ";\terror = " + d);
//			arrayOfFloat1 = null;
//			arrayOfFloat2 = null;
//			localFloatDCT_1D = null;
//			System.gc();
//		}
//	}
//
//	public static void checkAccuracyDCT_2D() {
//		System.out.println("Checking accuracy of 2D DCT (float[] input)...");
//		FloatDCT_2D localFloatDCT_2D;
//		double d;
//		Object localObject1;
//		Object localObject2;
//		for (int i = 0; i < sizes2D.length; ++i) {
//			localFloatDCT_2D = new FloatDCT_2D(sizes2D[i], sizes2D[i]);
//			d = 0.0D;
//			localObject1 = new float[sizes2D[i] * sizes2D[i]];
//			IOUtils.fillMatrix_2D(sizes2D[i], sizes2D[i], localObject1);
//			localObject2 = new float[sizes2D[i] * sizes2D[i]];
//			IOUtils.fillMatrix_2D(sizes2D[i], sizes2D[i], localObject2);
//			localFloatDCT_2D.forward(localObject1, true);
//			localFloatDCT_2D.inverse(localObject1, true);
//			d = computeRMSE(localObject1, localObject2);
//			if (d > eps)
//				System.err.println("\tsize = " + sizes2D[i] + " x "
//						+ sizes2D[i] + ";\terror = " + d);
//			else
//				System.out.println("\tsize = " + sizes2D[i] + " x "
//						+ sizes2D[i] + ";\terror = " + d);
//			localObject1 = null;
//			localObject2 = null;
//			localFloatDCT_2D = null;
//			System.gc();
//		}
//		System.out.println("Checking accuracy of 2D DCT (float[][] input)...");
//		for (i = 0; i < sizes2D.length; ++i) {
//			localFloatDCT_2D = new FloatDCT_2D(sizes2D[i], sizes2D[i]);
//			d = 0.0D;
//			localObject1 = new float[sizes2D[i]][sizes2D[i]];
//			IOUtils.fillMatrix_2D(sizes2D[i], sizes2D[i], localObject1);
//			localObject2 = new float[sizes2D[i]][sizes2D[i]];
//			IOUtils.fillMatrix_2D(sizes2D[i], sizes2D[i], localObject2);
//			localFloatDCT_2D.forward(localObject1, true);
//			localFloatDCT_2D.inverse(localObject1, true);
//			d = computeRMSE(localObject1, localObject2);
//			if (d > eps)
//				System.err.println("\tsize = " + sizes2D[i] + " x "
//						+ sizes2D[i] + ";\terror = " + d);
//			else
//				System.out.println("\tsize = " + sizes2D[i] + " x "
//						+ sizes2D[i] + ";\terror = " + d);
//			localObject1 = (float[][]) null;
//			localObject2 = (float[][]) null;
//			localFloatDCT_2D = null;
//			System.gc();
//		}
//	}
//
//	public static void checkAccuracyDCT_3D() {
//		System.out.println("Checking accuracy of 3D DCT (float[] input)...");
//		FloatDCT_3D localFloatDCT_3D;
//		double d;
//		Object localObject1;
//		Object localObject2;
//		for (int i = 0; i < sizes3D.length; ++i) {
//			localFloatDCT_3D = new FloatDCT_3D(sizes3D[i], sizes3D[i],
//					sizes3D[i]);
//			d = 0.0D;
//			localObject1 = new float[sizes3D[i] * sizes3D[i] * sizes3D[i]];
//			IOUtils.fillMatrix_3D(sizes3D[i], sizes3D[i], sizes3D[i],
//					localObject1);
//			localObject2 = new float[sizes3D[i] * sizes3D[i] * sizes3D[i]];
//			IOUtils.fillMatrix_3D(sizes3D[i], sizes3D[i], sizes3D[i],
//					localObject2);
//			localFloatDCT_3D.forward(localObject1, true);
//			localFloatDCT_3D.inverse(localObject1, true);
//			d = computeRMSE(localObject1, localObject2);
//			if (d > eps)
//				System.err
//						.println("\tsize = " + sizes3D[i] + " x " + sizes3D[i]
//								+ " x " + sizes3D[i] + ";\t\terror = " + d);
//			else
//				System.out
//						.println("\tsize = " + sizes3D[i] + " x " + sizes3D[i]
//								+ " x " + sizes3D[i] + ";\t\terror = " + d);
//			localObject1 = null;
//			localObject2 = null;
//			localFloatDCT_3D = null;
//			System.gc();
//		}
//		System.out
//				.println("Checking accuracy of 3D DCT (float[][][] input)...");
//		for (i = 0; i < sizes3D.length; ++i) {
//			localFloatDCT_3D = new FloatDCT_3D(sizes3D[i], sizes3D[i],
//					sizes3D[i]);
//			d = 0.0D;
//			localObject1 = new float[sizes3D[i]][sizes3D[i]][sizes3D[i]];
//			IOUtils.fillMatrix_3D(sizes3D[i], sizes3D[i], sizes3D[i],
//					localObject1);
//			localObject2 = new float[sizes3D[i]][sizes3D[i]][sizes3D[i]];
//			IOUtils.fillMatrix_3D(sizes3D[i], sizes3D[i], sizes3D[i],
//					localObject2);
//			localFloatDCT_3D.forward(localObject1, true);
//			localFloatDCT_3D.inverse(localObject1, true);
//			d = computeRMSE(localObject1, localObject2);
//			if (d > eps)
//				System.err
//						.println("\tsize = " + sizes3D[i] + " x " + sizes3D[i]
//								+ " x " + sizes3D[i] + ";\t\terror = " + d);
//			else
//				System.out
//						.println("\tsize = " + sizes3D[i] + " x " + sizes3D[i]
//								+ " x " + sizes3D[i] + ";\t\terror = " + d);
//			localObject1 = (float[][][]) null;
//			localObject2 = (float[][][]) null;
//			localFloatDCT_3D = null;
//			System.gc();
//		}
//	}
//
//	private static double computeRMSE(float[] paramArrayOfFloat1,
//			float[] paramArrayOfFloat2) {
//		if (paramArrayOfFloat1.length != paramArrayOfFloat2.length)
//			throw new IllegalArgumentException("Arrays are not the same size");
//		double d1 = 0.0D;
//		for (int i = 0; i < paramArrayOfFloat1.length; ++i) {
//			double d2 = paramArrayOfFloat1[i] - paramArrayOfFloat2[i];
//			d1 += d2 * d2;
//		}
//		return Math.sqrt(d1 / paramArrayOfFloat1.length);
//	}
//
//	private static double computeRMSE(float[][] paramArrayOfFloat1,
//			float[][] paramArrayOfFloat2) {
//		if ((paramArrayOfFloat1.length != paramArrayOfFloat2.length)
//				|| (paramArrayOfFloat1[0].length != paramArrayOfFloat2[0].length))
//			throw new IllegalArgumentException("Arrays are not the same size");
//		double d1 = 0.0D;
//		for (int i = 0; i < paramArrayOfFloat1.length; ++i)
//			for (int j = 0; j < paramArrayOfFloat1[0].length; ++j) {
//				double d2 = paramArrayOfFloat1[i][j] - paramArrayOfFloat2[i][j];
//				d1 += d2 * d2;
//			}
//		return Math.sqrt(d1 / paramArrayOfFloat1.length
//				* paramArrayOfFloat1[0].length);
//	}
//
//	private static double computeRMSE(float[][][] paramArrayOfFloat1,
//			float[][][] paramArrayOfFloat2) {
//		if ((paramArrayOfFloat1.length != paramArrayOfFloat2.length)
//				|| (paramArrayOfFloat1[0].length != paramArrayOfFloat2[0].length)
//				|| (paramArrayOfFloat1[0][0].length != paramArrayOfFloat2[0][0].length))
//			throw new IllegalArgumentException("Arrays are not the same size");
//		double d1 = 0.0D;
//		for (int i = 0; i < paramArrayOfFloat1.length; ++i)
//			for (int j = 0; j < paramArrayOfFloat1[0].length; ++j)
//				for (int k = 0; k < paramArrayOfFloat1[0][0].length; ++k) {
//					double d2 = paramArrayOfFloat1[i][j][k]
//							- paramArrayOfFloat2[i][j][k];
//					d1 += d2 * d2;
//				}
//		return Math.sqrt(d1 / paramArrayOfFloat1.length
//				* paramArrayOfFloat1[0].length
//				* paramArrayOfFloat1[0][0].length);
//	}
//
//	public static void main(String[] paramArrayOfString) {
//		checkAccuracyDCT_1D();
//		checkAccuracyDCT_2D();
//		checkAccuracyDCT_3D();
//		System.exit(0);
//	}
//}