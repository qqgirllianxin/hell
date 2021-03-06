package ps.hell.math.base.persons.fft.transform.dst;//package ps.landerbuluse.ml.math.fft.transform.dst;
//
//import edu.emory.mathcs.utils.IOUtils;
//import java.io.PrintStream;
//
//public class AccuracyCheckDoubleDST {
//	private static int[] sizes1D = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
//			16, 32, 64, 100, 120, 128, 256, 310, 512, 1024, 1056, 2048, 8192,
//			10158, 16384, 32768, 65536, 131072 };
//	private static int[] sizes2D = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
//			16, 32, 64, 100, 120, 128, 256, 310, 511, 512, 1024 };
//	private static int[] sizes3D = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
//			16, 32, 64, 100, 128 };
//	private static double eps = Math.pow(2.0D, -52.0D);
//
//	public static void checkAccuracyDST_1D() {
//		System.out.println("Checking accuracy of 1D DST...");
//		for (int i = 0; i < sizes1D.length; ++i) {
//			DoubleDST_1D localDoubleDST_1D = new DoubleDST_1D(sizes1D[i]);
//			double d = 0.0D;
//			double[] arrayOfDouble1 = new double[sizes1D[i]];
//			IOUtils.fillMatrix_1D(sizes1D[i], arrayOfDouble1);
//			double[] arrayOfDouble2 = new double[sizes1D[i]];
//			IOUtils.fillMatrix_1D(sizes1D[i], arrayOfDouble2);
//			localDoubleDST_1D.forward(arrayOfDouble1, true);
//			localDoubleDST_1D.inverse(arrayOfDouble1, true);
//			d = computeRMSE(arrayOfDouble1, arrayOfDouble2);
//			if (d > eps)
//				System.err
//						.println("\tsize = " + sizes1D[i] + ";\terror = " + d);
//			else
//				System.out
//						.println("\tsize = " + sizes1D[i] + ";\terror = " + d);
//			arrayOfDouble1 = null;
//			arrayOfDouble2 = null;
//			localDoubleDST_1D = null;
//			System.gc();
//		}
//	}
//
//	public static void checkAccuracyDST_2D() {
//		System.out.println("Checking accuracy of 2D DST (double[] input)...");
//		DoubleDST_2D localDoubleDST_2D;
//		double d;
//		Object localObject1;
//		Object localObject2;
//		for (int i = 0; i < sizes2D.length; ++i) {
//			localDoubleDST_2D = new DoubleDST_2D(sizes2D[i], sizes2D[i]);
//			d = 0.0D;
//			localObject1 = new double[sizes2D[i] * sizes2D[i]];
//			IOUtils.fillMatrix_2D(sizes2D[i], sizes2D[i], localObject1);
//			localObject2 = new double[sizes2D[i] * sizes2D[i]];
//			IOUtils.fillMatrix_2D(sizes2D[i], sizes2D[i], localObject2);
//			localDoubleDST_2D.forward(localObject1, true);
//			localDoubleDST_2D.inverse(localObject1, true);
//			d = computeRMSE(localObject1, localObject2);
//			if (d > eps)
//				System.err.println("\tsize = " + sizes2D[i] + " x "
//						+ sizes2D[i] + ";\terror = " + d);
//			else
//				System.out.println("\tsize = " + sizes2D[i] + " x "
//						+ sizes2D[i] + ";\terror = " + d);
//			localObject1 = null;
//			localObject2 = null;
//			localDoubleDST_2D = null;
//			System.gc();
//		}
//		System.out.println("Checking accuracy of 2D DST (double[][] input)...");
//		for (i = 0; i < sizes2D.length; ++i) {
//			localDoubleDST_2D = new DoubleDST_2D(sizes2D[i], sizes2D[i]);
//			d = 0.0D;
//			localObject1 = new double[sizes2D[i]][sizes2D[i]];
//			IOUtils.fillMatrix_2D(sizes2D[i], sizes2D[i], localObject1);
//			localObject2 = new double[sizes2D[i]][sizes2D[i]];
//			IOUtils.fillMatrix_2D(sizes2D[i], sizes2D[i], localObject2);
//			localDoubleDST_2D.forward(localObject1, true);
//			localDoubleDST_2D.inverse(localObject1, true);
//			d = computeRMSE(localObject1, localObject2);
//			if (d > eps)
//				System.err.println("\tsize = " + sizes2D[i] + " x "
//						+ sizes2D[i] + ";\terror = " + d);
//			else
//				System.out.println("\tsize = " + sizes2D[i] + " x "
//						+ sizes2D[i] + ";\terror = " + d);
//			localObject1 = (double[][]) null;
//			localObject2 = (double[][]) null;
//			localDoubleDST_2D = null;
//			System.gc();
//		}
//	}
//
//	public static void checkAccuracyDST_3D() {
//		System.out.println("Checking accuracy of 3D DST (double[] input)...");
//		DoubleDST_3D localDoubleDST_3D;
//		double d;
//		Object localObject1;
//		Object localObject2;
//		for (int i = 0; i < sizes3D.length; ++i) {
//			localDoubleDST_3D = new DoubleDST_3D(sizes3D[i], sizes3D[i],
//					sizes3D[i]);
//			d = 0.0D;
//			localObject1 = new double[sizes3D[i] * sizes3D[i] * sizes3D[i]];
//			IOUtils.fillMatrix_3D(sizes3D[i], sizes3D[i], sizes3D[i],
//					localObject1);
//			localObject2 = new double[sizes3D[i] * sizes3D[i] * sizes3D[i]];
//			IOUtils.fillMatrix_3D(sizes3D[i], sizes3D[i], sizes3D[i],
//					localObject2);
//			localDoubleDST_3D.forward(localObject1, true);
//			localDoubleDST_3D.inverse(localObject1, true);
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
//			localDoubleDST_3D = null;
//			System.gc();
//		}
//		System.out
//				.println("Checking accuracy of 3D DST (double[][][] input)...");
//		for (i = 0; i < sizes3D.length; ++i) {
//			localDoubleDST_3D = new DoubleDST_3D(sizes3D[i], sizes3D[i],
//					sizes3D[i]);
//			d = 0.0D;
//			localObject1 = new double[sizes3D[i]][sizes3D[i]][sizes3D[i]];
//			IOUtils.fillMatrix_3D(sizes3D[i], sizes3D[i], sizes3D[i],
//					localObject1);
//			localObject2 = new double[sizes3D[i]][sizes3D[i]][sizes3D[i]];
//			IOUtils.fillMatrix_3D(sizes3D[i], sizes3D[i], sizes3D[i],
//					localObject2);
//			localDoubleDST_3D.forward(localObject1, true);
//			localDoubleDST_3D.inverse(localObject1, true);
//			d = computeRMSE(localObject1, localObject2);
//			if (d > eps)
//				System.err
//						.println("\tsize = " + sizes3D[i] + " x " + sizes3D[i]
//								+ " x " + sizes3D[i] + ";\t\terror = " + d);
//			else
//				System.out
//						.println("\tsize = " + sizes3D[i] + " x " + sizes3D[i]
//								+ " x " + sizes3D[i] + ";\t\terror = " + d);
//			localObject1 = (double[][][]) null;
//			localObject2 = (double[][][]) null;
//			localDoubleDST_3D = null;
//			System.gc();
//		}
//	}
//
//	private static double computeRMSE(double[] paramArrayOfDouble1,
//			double[] paramArrayOfDouble2) {
//		if (paramArrayOfDouble1.length != paramArrayOfDouble2.length)
//			throw new IllegalArgumentException("Arrays are not the same size.");
//		double d1 = 0.0D;
//		for (int i = 0; i < paramArrayOfDouble1.length; ++i) {
//			double d2 = paramArrayOfDouble1[i] - paramArrayOfDouble2[i];
//			d1 += d2 * d2;
//		}
//		return Math.sqrt(d1 / paramArrayOfDouble1.length);
//	}
//
//	private static double computeRMSE(double[][] paramArrayOfDouble1,
//			double[][] paramArrayOfDouble2) {
//		if ((paramArrayOfDouble1.length != paramArrayOfDouble2.length)
//				|| (paramArrayOfDouble1[0].length != paramArrayOfDouble2[0].length))
//			throw new IllegalArgumentException("Arrays are not the same size.");
//		double d1 = 0.0D;
//		for (int i = 0; i < paramArrayOfDouble1.length; ++i)
//			for (int j = 0; j < paramArrayOfDouble1[0].length; ++j) {
//				double d2 = paramArrayOfDouble1[i][j]
//						- paramArrayOfDouble2[i][j];
//				d1 += d2 * d2;
//			}
//		return Math.sqrt(d1 / paramArrayOfDouble1.length
//				* paramArrayOfDouble1[0].length);
//	}
//
//	private static double computeRMSE(double[][][] paramArrayOfDouble1,
//			double[][][] paramArrayOfDouble2) {
//		if ((paramArrayOfDouble1.length != paramArrayOfDouble2.length)
//				|| (paramArrayOfDouble1[0].length != paramArrayOfDouble2[0].length)
//				|| (paramArrayOfDouble1[0][0].length != paramArrayOfDouble2[0][0].length))
//			throw new IllegalArgumentException("Arrays are not the same size.");
//		double d1 = 0.0D;
//		for (int i = 0; i < paramArrayOfDouble1.length; ++i)
//			for (int j = 0; j < paramArrayOfDouble1[0].length; ++j)
//				for (int k = 0; k < paramArrayOfDouble1[0][0].length; ++k) {
//					double d2 = paramArrayOfDouble1[i][j][k]
//							- paramArrayOfDouble2[i][j][k];
//					d1 += d2 * d2;
//				}
//		return Math.sqrt(d1 / paramArrayOfDouble1.length
//				* paramArrayOfDouble1[0].length
//				* paramArrayOfDouble1[0][0].length);
//	}
//
//	public static void main(String[] paramArrayOfString) {
//		checkAccuracyDST_1D();
//		checkAccuracyDST_2D();
//		checkAccuracyDST_3D();
//		System.exit(0);
//	}
//}