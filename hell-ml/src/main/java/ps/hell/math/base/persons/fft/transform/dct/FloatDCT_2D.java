package ps.hell.math.base.persons.fft.transform.dct;//package ps.landerbuluse.ml.math.fft.transform.dct;
//
//import edu.emory.mathcs.utils.ConcurrencyUtils;
//import java.util.concurrent.Future;
//
//public class FloatDCT_2D {
//	private int rows;
//	private int columns;
//	private float[] t;
//	private FloatDCT_1D dctColumns;
//	private FloatDCT_1D dctRows;
//	private int nt;
//	private int oldNthreads;
//	private boolean isPowerOfTwo = false;
//	private boolean useThreads = false;
//
//	public FloatDCT_2D(int paramInt1, int paramInt2) {
//		if ((paramInt1 <= 1) || (paramInt2 <= 1))
//			throw new IllegalArgumentException(
//					"rows and columns must be greater than 1");
//		this.rows = paramInt1;
//		this.columns = paramInt2;
//		if (paramInt1 * paramInt2 >= ConcurrencyUtils.getThreadsBeginN_2D())
//			this.useThreads = true;
//		if ((ConcurrencyUtils.isPowerOf2(paramInt1))
//				&& (ConcurrencyUtils.isPowerOf2(paramInt2))) {
//			this.isPowerOfTwo = true;
//			this.oldNthreads = ConcurrencyUtils.getNumberOfThreads();
//			this.nt = (4 * this.oldNthreads * paramInt1);
//			if (paramInt2 == 2 * this.oldNthreads)
//				this.nt >>= 1;
//			else if (paramInt2 < 2 * this.oldNthreads)
//				this.nt >>= 2;
//			this.t = new float[this.nt];
//		}
//		this.dctColumns = new FloatDCT_1D(paramInt2);
//		if (paramInt2 == paramInt1)
//			this.dctRows = this.dctColumns;
//		else
//			this.dctRows = new FloatDCT_1D(paramInt1);
//	}
//
//	public void forward(float[] paramArrayOfFloat, boolean paramBoolean) {
//		int i = ConcurrencyUtils.getNumberOfThreads();
//		if (this.isPowerOfTwo) {
//			if (i != this.oldNthreads) {
//				this.nt = (4 * i * this.rows);
//				if (this.columns == 2 * i)
//					this.nt >>= 1;
//				else if (this.columns < 2 * i)
//					this.nt >>= 2;
//				this.t = new float[this.nt];
//				this.oldNthreads = i;
//			}
//			if ((i > 1) && (this.useThreads)) {
//				ddxt2d_subth(-1, paramArrayOfFloat, paramBoolean);
//				ddxt2d0_subth(-1, paramArrayOfFloat, paramBoolean);
//			} else {
//				ddxt2d_sub(-1, paramArrayOfFloat, paramBoolean);
//				for (int j = 0; j < this.rows; ++j)
//					this.dctColumns.forward(paramArrayOfFloat,
//							j * this.columns, paramBoolean);
//			}
//		} else {
//			int l;
//			int i1;
//			if ((i > 1) && (this.useThreads) && (this.rows >= i)
//					&& (this.columns >= i)) {
//				Future[] arrayOfFuture = new Future[i];
//				l = this.rows / i;
//				int i2;
//				int i3;
//				for (i1 = 0; i1 < i; ++i1) {
//					i2 = i1 * l;
//					i3 = (i1 == i - 1) ? this.rows : i2 + l;
//					arrayOfFuture[i1] = ConcurrencyUtils.submit(new Runnable(
//							i2, i3, paramArrayOfFloat, paramBoolean) {
//						public void run() {
//							for (int i = this.val$firstRow; i < this.val$lastRow; ++i)
//								FloatDCT_2D.this.dctColumns.forward(this.val$a,
//										i * FloatDCT_2D.this.columns,
//										this.val$scale);
//						}
//					});
//				}
//				ConcurrencyUtils.waitForCompletion(arrayOfFuture);
//				l = this.columns / i;
//				for (i1 = 0; i1 < i; ++i1) {
//					i2 = i1 * l;
//					i3 = (i1 == i - 1) ? this.columns : i2 + l;
//					arrayOfFuture[i1] = ConcurrencyUtils.submit(new Runnable(
//							i2, i3, paramArrayOfFloat, paramBoolean) {
//						public void run() {
//							float[] arrayOfFloat = new float[FloatDCT_2D.this.rows];
//							for (int i = this.val$firstColumn; i < this.val$lastColumn; ++i) {
//								for (int j = 0; j < FloatDCT_2D.this.rows; ++j)
//									arrayOfFloat[j] = this.val$a[(j
//											* FloatDCT_2D
//													.access$000(FloatDCT_2D.this) + i)];
//								FloatDCT_2D.this.dctRows.forward(arrayOfFloat,
//										this.val$scale);
//								for (j = 0; j < FloatDCT_2D.this.rows; ++j)
//									this.val$a[(j * FloatDCT_2D.this.columns + i)] = arrayOfFloat[j];
//							}
//						}
//					});
//				}
//				ConcurrencyUtils.waitForCompletion(arrayOfFuture);
//			} else {
//				for (int k = 0; k < this.rows; ++k)
//					this.dctColumns.forward(paramArrayOfFloat,
//							k * this.columns, paramBoolean);
//				float[] arrayOfFloat = new float[this.rows];
//				for (l = 0; l < this.columns; ++l) {
//					for (i1 = 0; i1 < this.rows; ++i1)
//						arrayOfFloat[i1] = paramArrayOfFloat[(i1 * this.columns + l)];
//					this.dctRows.forward(arrayOfFloat, paramBoolean);
//					for (i1 = 0; i1 < this.rows; ++i1)
//						paramArrayOfFloat[(i1 * this.columns + l)] = arrayOfFloat[i1];
//				}
//			}
//		}
//	}
//
//	public void forward(float[][] paramArrayOfFloat, boolean paramBoolean) {
//		int i = ConcurrencyUtils.getNumberOfThreads();
//		if (this.isPowerOfTwo) {
//			if (i != this.oldNthreads) {
//				this.nt = (4 * i * this.rows);
//				if (this.columns == 2 * i)
//					this.nt >>= 1;
//				else if (this.columns < 2 * i)
//					this.nt >>= 2;
//				this.t = new float[this.nt];
//				this.oldNthreads = i;
//			}
//			if ((i > 1) && (this.useThreads)) {
//				ddxt2d_subth(-1, paramArrayOfFloat, paramBoolean);
//				ddxt2d0_subth(-1, paramArrayOfFloat, paramBoolean);
//			} else {
//				ddxt2d_sub(-1, paramArrayOfFloat, paramBoolean);
//				for (int j = 0; j < this.rows; ++j)
//					this.dctColumns.forward(paramArrayOfFloat[j], paramBoolean);
//			}
//		} else {
//			int l;
//			int i1;
//			if ((i > 1) && (this.useThreads) && (this.rows >= i)
//					&& (this.columns >= i)) {
//				Future[] arrayOfFuture = new Future[i];
//				l = this.rows / i;
//				int i2;
//				int i3;
//				for (i1 = 0; i1 < i; ++i1) {
//					i2 = i1 * l;
//					i3 = (i1 == i - 1) ? this.rows : i2 + l;
//					arrayOfFuture[i1] = ConcurrencyUtils.submit(new Runnable(
//							i2, i3, paramArrayOfFloat, paramBoolean) {
//						public void run() {
//							for (int i = this.val$firstRow; i < this.val$lastRow; ++i)
//								FloatDCT_2D.this.dctColumns.forward(
//										this.val$a[i], this.val$scale);
//						}
//					});
//				}
//				ConcurrencyUtils.waitForCompletion(arrayOfFuture);
//				l = this.columns / i;
//				for (i1 = 0; i1 < i; ++i1) {
//					i2 = i1 * l;
//					i3 = (i1 == i - 1) ? this.columns : i2 + l;
//					arrayOfFuture[i1] = ConcurrencyUtils.submit(new Runnable(
//							i2, i3, paramArrayOfFloat, paramBoolean) {
//						public void run() {
//							float[] arrayOfFloat = new float[FloatDCT_2D.this.rows];
//							for (int i = this.val$firstColumn; i < this.val$lastColumn; ++i) {
//								for (int j = 0; j < FloatDCT_2D.this.rows; ++j)
//									arrayOfFloat[j] = this.val$a[j][i];
//								FloatDCT_2D.this.dctRows.forward(arrayOfFloat,
//										this.val$scale);
//								for (j = 0; j < FloatDCT_2D.this.rows; ++j)
//									this.val$a[j][i] = arrayOfFloat[j];
//							}
//						}
//					});
//				}
//				ConcurrencyUtils.waitForCompletion(arrayOfFuture);
//			} else {
//				for (int k = 0; k < this.rows; ++k)
//					this.dctColumns.forward(paramArrayOfFloat[k], paramBoolean);
//				float[] arrayOfFloat = new float[this.rows];
//				for (l = 0; l < this.columns; ++l) {
//					for (i1 = 0; i1 < this.rows; ++i1)
//						arrayOfFloat[i1] = paramArrayOfFloat[i1][l];
//					this.dctRows.forward(arrayOfFloat, paramBoolean);
//					for (i1 = 0; i1 < this.rows; ++i1)
//						paramArrayOfFloat[i1][l] = arrayOfFloat[i1];
//				}
//			}
//		}
//	}
//
//	public void inverse(float[] paramArrayOfFloat, boolean paramBoolean) {
//		int i = ConcurrencyUtils.getNumberOfThreads();
//		if (this.isPowerOfTwo) {
//			if (i != this.oldNthreads) {
//				this.nt = (4 * i * this.rows);
//				if (this.columns == 2 * i)
//					this.nt >>= 1;
//				else if (this.columns < 2 * i)
//					this.nt >>= 2;
//				this.t = new float[this.nt];
//				this.oldNthreads = i;
//			}
//			if ((i > 1) && (this.useThreads)) {
//				ddxt2d_subth(1, paramArrayOfFloat, paramBoolean);
//				ddxt2d0_subth(1, paramArrayOfFloat, paramBoolean);
//			} else {
//				ddxt2d_sub(1, paramArrayOfFloat, paramBoolean);
//				for (int j = 0; j < this.rows; ++j)
//					this.dctColumns.inverse(paramArrayOfFloat,
//							j * this.columns, paramBoolean);
//			}
//		} else {
//			int l;
//			int i1;
//			if ((i > 1) && (this.useThreads) && (this.rows >= i)
//					&& (this.columns >= i)) {
//				Future[] arrayOfFuture = new Future[i];
//				l = this.rows / i;
//				int i2;
//				int i3;
//				for (i1 = 0; i1 < i; ++i1) {
//					i2 = i1 * l;
//					i3 = (i1 == i - 1) ? this.rows : i2 + l;
//					arrayOfFuture[i1] = ConcurrencyUtils.submit(new Runnable(
//							i2, i3, paramArrayOfFloat, paramBoolean) {
//						public void run() {
//							for (int i = this.val$firstRow; i < this.val$lastRow; ++i)
//								FloatDCT_2D.this.dctColumns.inverse(this.val$a,
//										i * FloatDCT_2D.this.columns,
//										this.val$scale);
//						}
//					});
//				}
//				ConcurrencyUtils.waitForCompletion(arrayOfFuture);
//				l = this.columns / i;
//				for (i1 = 0; i1 < i; ++i1) {
//					i2 = i1 * l;
//					i3 = (i1 == i - 1) ? this.columns : i2 + l;
//					arrayOfFuture[i1] = ConcurrencyUtils.submit(new Runnable(
//							i2, i3, paramArrayOfFloat, paramBoolean) {
//						public void run() {
//							float[] arrayOfFloat = new float[FloatDCT_2D.this.rows];
//							for (int i = this.val$firstColumn; i < this.val$lastColumn; ++i) {
//								for (int j = 0; j < FloatDCT_2D.this.rows; ++j)
//									arrayOfFloat[j] = this.val$a[(j
//											* FloatDCT_2D
//													.access$000(FloatDCT_2D.this) + i)];
//								FloatDCT_2D.this.dctRows.inverse(arrayOfFloat,
//										this.val$scale);
//								for (j = 0; j < FloatDCT_2D.this.rows; ++j)
//									this.val$a[(j * FloatDCT_2D.this.columns + i)] = arrayOfFloat[j];
//							}
//						}
//					});
//				}
//				ConcurrencyUtils.waitForCompletion(arrayOfFuture);
//			} else {
//				for (int k = 0; k < this.rows; ++k)
//					this.dctColumns.inverse(paramArrayOfFloat,
//							k * this.columns, paramBoolean);
//				float[] arrayOfFloat = new float[this.rows];
//				for (l = 0; l < this.columns; ++l) {
//					for (i1 = 0; i1 < this.rows; ++i1)
//						arrayOfFloat[i1] = paramArrayOfFloat[(i1 * this.columns + l)];
//					this.dctRows.inverse(arrayOfFloat, paramBoolean);
//					for (i1 = 0; i1 < this.rows; ++i1)
//						paramArrayOfFloat[(i1 * this.columns + l)] = arrayOfFloat[i1];
//				}
//			}
//		}
//	}
//
//	public void inverse(float[][] paramArrayOfFloat, boolean paramBoolean) {
//		int i = ConcurrencyUtils.getNumberOfThreads();
//		if (this.isPowerOfTwo) {
//			if (i != this.oldNthreads) {
//				this.nt = (4 * i * this.rows);
//				if (this.columns == 2 * i)
//					this.nt >>= 1;
//				else if (this.columns < 2 * i)
//					this.nt >>= 2;
//				this.t = new float[this.nt];
//				this.oldNthreads = i;
//			}
//			if ((i > 1) && (this.useThreads)) {
//				ddxt2d_subth(1, paramArrayOfFloat, paramBoolean);
//				ddxt2d0_subth(1, paramArrayOfFloat, paramBoolean);
//			} else {
//				ddxt2d_sub(1, paramArrayOfFloat, paramBoolean);
//				for (int j = 0; j < this.rows; ++j)
//					this.dctColumns.inverse(paramArrayOfFloat[j], paramBoolean);
//			}
//		} else {
//			int l;
//			int i1;
//			if ((i > 1) && (this.useThreads) && (this.rows >= i)
//					&& (this.columns >= i)) {
//				Future[] arrayOfFuture = new Future[i];
//				l = this.rows / i;
//				int i2;
//				int i3;
//				for (i1 = 0; i1 < i; ++i1) {
//					i2 = i1 * l;
//					i3 = (i1 == i - 1) ? this.rows : i2 + l;
//					arrayOfFuture[i1] = ConcurrencyUtils.submit(new Runnable(
//							i2, i3, paramArrayOfFloat, paramBoolean) {
//						public void run() {
//							for (int i = this.val$firstRow; i < this.val$lastRow; ++i)
//								FloatDCT_2D.this.dctColumns.inverse(
//										this.val$a[i], this.val$scale);
//						}
//					});
//				}
//				ConcurrencyUtils.waitForCompletion(arrayOfFuture);
//				l = this.columns / i;
//				for (i1 = 0; i1 < i; ++i1) {
//					i2 = i1 * l;
//					i3 = (i1 == i - 1) ? this.columns : i2 + l;
//					arrayOfFuture[i1] = ConcurrencyUtils.submit(new Runnable(
//							i2, i3, paramArrayOfFloat, paramBoolean) {
//						public void run() {
//							float[] arrayOfFloat = new float[FloatDCT_2D.this.rows];
//							for (int i = this.val$firstColumn; i < this.val$lastColumn; ++i) {
//								for (int j = 0; j < FloatDCT_2D.this.rows; ++j)
//									arrayOfFloat[j] = this.val$a[j][i];
//								FloatDCT_2D.this.dctRows.inverse(arrayOfFloat,
//										this.val$scale);
//								for (j = 0; j < FloatDCT_2D.this.rows; ++j)
//									this.val$a[j][i] = arrayOfFloat[j];
//							}
//						}
//					});
//				}
//				ConcurrencyUtils.waitForCompletion(arrayOfFuture);
//			} else {
//				for (int k = 0; k < this.rows; ++k)
//					this.dctColumns.inverse(paramArrayOfFloat[k], paramBoolean);
//				float[] arrayOfFloat = new float[this.rows];
//				for (l = 0; l < this.columns; ++l) {
//					for (i1 = 0; i1 < this.rows; ++i1)
//						arrayOfFloat[i1] = paramArrayOfFloat[i1][l];
//					this.dctRows.inverse(arrayOfFloat, paramBoolean);
//					for (i1 = 0; i1 < this.rows; ++i1)
//						paramArrayOfFloat[i1][l] = arrayOfFloat[i1];
//				}
//			}
//		}
//	}
//
//	private void ddxt2d_subth(int paramInt, float[] paramArrayOfFloat,
//			boolean paramBoolean) {
//		int i = ConcurrencyUtils.getNumberOfThreads();
//		int j = 4 * this.rows;
//		if (this.columns == 2 * i) {
//			j >>= 1;
//		} else if (this.columns < 2 * i) {
//			i = this.columns;
//			j >>= 2;
//		}
//		int k = i;
//		Future[] arrayOfFuture = new Future[i];
//		for (int l = 0; l < i; ++l) {
//			int i1 = l;
//			int i2 = j * l;
//			arrayOfFuture[l] = ConcurrencyUtils.submit(new Runnable(k,
//					paramInt, i1, i2, paramArrayOfFloat, paramBoolean) {
//				public void run() {
//					int k;
//					int i;
//					int j;
//					if (FloatDCT_2D.this.columns > 2 * this.val$nthreads) {
//						int l;
//						if (this.val$isgn == -1) {
//							k = 4 * this.val$n0;
//							while (k < FloatDCT_2D.this.columns) {
//								for (l = 0; l < FloatDCT_2D.this.rows; ++l) {
//									i = l * FloatDCT_2D.this.columns + k;
//									j = this.val$startt + FloatDCT_2D.this.rows
//											+ l;
//									FloatDCT_2D.this.t[(this.val$startt + l)] = this.val$a[i];
//									FloatDCT_2D.this.t[j] = this.val$a[(i + 1)];
//									FloatDCT_2D.this.t[(j + FloatDCT_2D.this.rows)] = this.val$a[(i + 2)];
//									FloatDCT_2D.this.t[(j + 2 * FloatDCT_2D.this.rows)] = this.val$a[(i + 3)];
//								}
//								FloatDCT_2D.this.dctRows.forward(
//										FloatDCT_2D.this.t, this.val$startt,
//										this.val$scale);
//								FloatDCT_2D.this.dctRows.forward(
//										FloatDCT_2D.this.t, this.val$startt
//												+ FloatDCT_2D.this.rows,
//										this.val$scale);
//								FloatDCT_2D.this.dctRows.forward(
//										FloatDCT_2D.this.t, this.val$startt + 2
//												* FloatDCT_2D.this.rows,
//										this.val$scale);
//								FloatDCT_2D.this.dctRows.forward(
//										FloatDCT_2D.this.t, this.val$startt + 3
//												* FloatDCT_2D.this.rows,
//										this.val$scale);
//								for (l = 0; l < FloatDCT_2D.this.rows; ++l) {
//									i = l * FloatDCT_2D.this.columns + k;
//									j = this.val$startt + FloatDCT_2D.this.rows
//											+ l;
//									this.val$a[i] = FloatDCT_2D
//											.access$400(FloatDCT_2D.this)[(this.val$startt + l)];
//									this.val$a[(i + 1)] = FloatDCT_2D
//											.access$400(FloatDCT_2D.this)[j];
//									this.val$a[(i + 2)] = FloatDCT_2D
//											.access$400(FloatDCT_2D.this)[(j + FloatDCT_2D
//											.access$200(FloatDCT_2D.this))];
//									this.val$a[(i + 3)] = FloatDCT_2D
//											.access$400(FloatDCT_2D.this)[(j + 2 * FloatDCT_2D
//											.access$200(FloatDCT_2D.this))];
//								}
//								k += 4 * this.val$nthreads;
//							}
//						} else {
//							k = 4 * this.val$n0;
//							while (k < FloatDCT_2D.this.columns) {
//								for (l = 0; l < FloatDCT_2D.this.rows; ++l) {
//									i = l * FloatDCT_2D.this.columns + k;
//									j = this.val$startt + FloatDCT_2D.this.rows
//											+ l;
//									FloatDCT_2D.this.t[(this.val$startt + l)] = this.val$a[i];
//									FloatDCT_2D.this.t[j] = this.val$a[(i + 1)];
//									FloatDCT_2D.this.t[(j + FloatDCT_2D.this.rows)] = this.val$a[(i + 2)];
//									FloatDCT_2D.this.t[(j + 2 * FloatDCT_2D.this.rows)] = this.val$a[(i + 3)];
//								}
//								FloatDCT_2D.this.dctRows.inverse(
//										FloatDCT_2D.this.t, this.val$startt,
//										this.val$scale);
//								FloatDCT_2D.this.dctRows.inverse(
//										FloatDCT_2D.this.t, this.val$startt
//												+ FloatDCT_2D.this.rows,
//										this.val$scale);
//								FloatDCT_2D.this.dctRows.inverse(
//										FloatDCT_2D.this.t, this.val$startt + 2
//												* FloatDCT_2D.this.rows,
//										this.val$scale);
//								FloatDCT_2D.this.dctRows.inverse(
//										FloatDCT_2D.this.t, this.val$startt + 3
//												* FloatDCT_2D.this.rows,
//										this.val$scale);
//								for (l = 0; l < FloatDCT_2D.this.rows; ++l) {
//									i = l * FloatDCT_2D.this.columns + k;
//									j = this.val$startt + FloatDCT_2D.this.rows
//											+ l;
//									this.val$a[i] = FloatDCT_2D
//											.access$400(FloatDCT_2D.this)[(this.val$startt + l)];
//									this.val$a[(i + 1)] = FloatDCT_2D
//											.access$400(FloatDCT_2D.this)[j];
//									this.val$a[(i + 2)] = FloatDCT_2D
//											.access$400(FloatDCT_2D.this)[(j + FloatDCT_2D
//											.access$200(FloatDCT_2D.this))];
//									this.val$a[(i + 3)] = FloatDCT_2D
//											.access$400(FloatDCT_2D.this)[(j + 2 * FloatDCT_2D
//											.access$200(FloatDCT_2D.this))];
//								}
//								k += 4 * this.val$nthreads;
//							}
//						}
//					} else if (FloatDCT_2D.this.columns == 2 * this.val$nthreads) {
//						for (k = 0; k < FloatDCT_2D.this.rows; ++k) {
//							i = k * FloatDCT_2D.this.columns + 2 * this.val$n0;
//							j = this.val$startt + k;
//							FloatDCT_2D.this.t[j] = this.val$a[i];
//							FloatDCT_2D.this.t[(j + FloatDCT_2D.this.rows)] = this.val$a[(i + 1)];
//						}
//						if (this.val$isgn == -1) {
//							FloatDCT_2D.this.dctRows.forward(
//									FloatDCT_2D.this.t, this.val$startt,
//									this.val$scale);
//							FloatDCT_2D.this.dctRows.forward(
//									FloatDCT_2D.this.t, this.val$startt
//											+ FloatDCT_2D.this.rows,
//									this.val$scale);
//						} else {
//							FloatDCT_2D.this.dctRows.inverse(
//									FloatDCT_2D.this.t, this.val$startt,
//									this.val$scale);
//							FloatDCT_2D.this.dctRows.inverse(
//									FloatDCT_2D.this.t, this.val$startt
//											+ FloatDCT_2D.this.rows,
//									this.val$scale);
//						}
//						for (k = 0; k < FloatDCT_2D.this.rows; ++k) {
//							i = k * FloatDCT_2D.this.columns + 2 * this.val$n0;
//							j = this.val$startt + k;
//							this.val$a[i] = FloatDCT_2D
//									.access$400(FloatDCT_2D.this)[j];
//							this.val$a[(i + 1)] = FloatDCT_2D
//									.access$400(FloatDCT_2D.this)[(j + FloatDCT_2D
//									.access$200(FloatDCT_2D.this))];
//						}
//					} else {
//						if (FloatDCT_2D.this.columns != this.val$nthreads)
//							return;
//						for (k = 0; k < FloatDCT_2D.this.rows; ++k)
//							FloatDCT_2D.this.t[(this.val$startt + k)] = this.val$a[(k
//									* FloatDCT_2D.access$000(FloatDCT_2D.this) + this.val$n0)];
//						if (this.val$isgn == -1)
//							FloatDCT_2D.this.dctRows.forward(
//									FloatDCT_2D.this.t, this.val$startt,
//									this.val$scale);
//						else
//							FloatDCT_2D.this.dctRows.inverse(
//									FloatDCT_2D.this.t, this.val$startt,
//									this.val$scale);
//						for (k = 0; k < FloatDCT_2D.this.rows; ++k)
//							this.val$a[(k * FloatDCT_2D.this.columns + this.val$n0)] = FloatDCT_2D
//									.access$400(FloatDCT_2D.this)[(this.val$startt + k)];
//					}
//				}
//			});
//		}
//		ConcurrencyUtils.waitForCompletion(arrayOfFuture);
//	}
//
//	private void ddxt2d_subth(int paramInt, float[][] paramArrayOfFloat,
//			boolean paramBoolean) {
//		int i = ConcurrencyUtils.getNumberOfThreads();
//		int j = 4 * this.rows;
//		if (this.columns == 2 * i) {
//			j >>= 1;
//		} else if (this.columns < 2 * i) {
//			i = this.columns;
//			j >>= 2;
//		}
//		int k = i;
//		Future[] arrayOfFuture = new Future[i];
//		for (int l = 0; l < i; ++l) {
//			int i1 = l;
//			int i2 = j * l;
//			arrayOfFuture[l] = ConcurrencyUtils.submit(new Runnable(k,
//					paramInt, i1, i2, paramArrayOfFloat, paramBoolean) {
//				public void run() {
//					int j;
//					int i;
//					if (FloatDCT_2D.this.columns > 2 * this.val$nthreads) {
//						int k;
//						if (this.val$isgn == -1) {
//							j = 4 * this.val$n0;
//							while (j < FloatDCT_2D.this.columns) {
//								for (k = 0; k < FloatDCT_2D.this.rows; ++k) {
//									i = this.val$startt + FloatDCT_2D.this.rows
//											+ k;
//									FloatDCT_2D.this.t[(this.val$startt + k)] = this.val$a[k][j];
//									FloatDCT_2D.this.t[i] = this.val$a[k][(j + 1)];
//									FloatDCT_2D.this.t[(i + FloatDCT_2D.this.rows)] = this.val$a[k][(j + 2)];
//									FloatDCT_2D.this.t[(i + 2 * FloatDCT_2D.this.rows)] = this.val$a[k][(j + 3)];
//								}
//								FloatDCT_2D.this.dctRows.forward(
//										FloatDCT_2D.this.t, this.val$startt,
//										this.val$scale);
//								FloatDCT_2D.this.dctRows.forward(
//										FloatDCT_2D.this.t, this.val$startt
//												+ FloatDCT_2D.this.rows,
//										this.val$scale);
//								FloatDCT_2D.this.dctRows.forward(
//										FloatDCT_2D.this.t, this.val$startt + 2
//												* FloatDCT_2D.this.rows,
//										this.val$scale);
//								FloatDCT_2D.this.dctRows.forward(
//										FloatDCT_2D.this.t, this.val$startt + 3
//												* FloatDCT_2D.this.rows,
//										this.val$scale);
//								for (k = 0; k < FloatDCT_2D.this.rows; ++k) {
//									i = this.val$startt + FloatDCT_2D.this.rows
//											+ k;
//									this.val$a[k][j] = FloatDCT_2D
//											.access$400(FloatDCT_2D.this)[(this.val$startt + k)];
//									this.val$a[k][(j + 1)] = FloatDCT_2D
//											.access$400(FloatDCT_2D.this)[i];
//									this.val$a[k][(j + 2)] = FloatDCT_2D
//											.access$400(FloatDCT_2D.this)[(i + FloatDCT_2D
//											.access$200(FloatDCT_2D.this))];
//									this.val$a[k][(j + 3)] = FloatDCT_2D
//											.access$400(FloatDCT_2D.this)[(i + 2 * FloatDCT_2D
//											.access$200(FloatDCT_2D.this))];
//								}
//								j += 4 * this.val$nthreads;
//							}
//						} else {
//							j = 4 * this.val$n0;
//							while (j < FloatDCT_2D.this.columns) {
//								for (k = 0; k < FloatDCT_2D.this.rows; ++k) {
//									i = this.val$startt + FloatDCT_2D.this.rows
//											+ k;
//									FloatDCT_2D.this.t[(this.val$startt + k)] = this.val$a[k][j];
//									FloatDCT_2D.this.t[i] = this.val$a[k][(j + 1)];
//									FloatDCT_2D.this.t[(i + FloatDCT_2D.this.rows)] = this.val$a[k][(j + 2)];
//									FloatDCT_2D.this.t[(i + 2 * FloatDCT_2D.this.rows)] = this.val$a[k][(j + 3)];
//								}
//								FloatDCT_2D.this.dctRows.inverse(
//										FloatDCT_2D.this.t, this.val$startt,
//										this.val$scale);
//								FloatDCT_2D.this.dctRows.inverse(
//										FloatDCT_2D.this.t, this.val$startt
//												+ FloatDCT_2D.this.rows,
//										this.val$scale);
//								FloatDCT_2D.this.dctRows.inverse(
//										FloatDCT_2D.this.t, this.val$startt + 2
//												* FloatDCT_2D.this.rows,
//										this.val$scale);
//								FloatDCT_2D.this.dctRows.inverse(
//										FloatDCT_2D.this.t, this.val$startt + 3
//												* FloatDCT_2D.this.rows,
//										this.val$scale);
//								for (k = 0; k < FloatDCT_2D.this.rows; ++k) {
//									i = this.val$startt + FloatDCT_2D.this.rows
//											+ k;
//									this.val$a[k][j] = FloatDCT_2D
//											.access$400(FloatDCT_2D.this)[(this.val$startt + k)];
//									this.val$a[k][(j + 1)] = FloatDCT_2D
//											.access$400(FloatDCT_2D.this)[i];
//									this.val$a[k][(j + 2)] = FloatDCT_2D
//											.access$400(FloatDCT_2D.this)[(i + FloatDCT_2D
//											.access$200(FloatDCT_2D.this))];
//									this.val$a[k][(j + 3)] = FloatDCT_2D
//											.access$400(FloatDCT_2D.this)[(i + 2 * FloatDCT_2D
//											.access$200(FloatDCT_2D.this))];
//								}
//								j += 4 * this.val$nthreads;
//							}
//						}
//					} else if (FloatDCT_2D.this.columns == 2 * this.val$nthreads) {
//						for (j = 0; j < FloatDCT_2D.this.rows; ++j) {
//							i = this.val$startt + j;
//							FloatDCT_2D.this.t[i] = this.val$a[j][(2 * this.val$n0)];
//							FloatDCT_2D.this.t[(i + FloatDCT_2D.this.rows)] = this.val$a[j][(2 * this.val$n0 + 1)];
//						}
//						if (this.val$isgn == -1) {
//							FloatDCT_2D.this.dctRows.forward(
//									FloatDCT_2D.this.t, this.val$startt,
//									this.val$scale);
//							FloatDCT_2D.this.dctRows.forward(
//									FloatDCT_2D.this.t, this.val$startt
//											+ FloatDCT_2D.this.rows,
//									this.val$scale);
//						} else {
//							FloatDCT_2D.this.dctRows.inverse(
//									FloatDCT_2D.this.t, this.val$startt,
//									this.val$scale);
//							FloatDCT_2D.this.dctRows.inverse(
//									FloatDCT_2D.this.t, this.val$startt
//											+ FloatDCT_2D.this.rows,
//									this.val$scale);
//						}
//						for (j = 0; j < FloatDCT_2D.this.rows; ++j) {
//							i = this.val$startt + j;
//							this.val$a[j][(2 * this.val$n0)] = FloatDCT_2D
//									.access$400(FloatDCT_2D.this)[i];
//							this.val$a[j][(2 * this.val$n0 + 1)] = FloatDCT_2D
//									.access$400(FloatDCT_2D.this)[(i + FloatDCT_2D
//									.access$200(FloatDCT_2D.this))];
//						}
//					} else {
//						if (FloatDCT_2D.this.columns != this.val$nthreads)
//							return;
//						for (j = 0; j < FloatDCT_2D.this.rows; ++j)
//							FloatDCT_2D.this.t[(this.val$startt + j)] = this.val$a[j][this.val$n0];
//						if (this.val$isgn == -1)
//							FloatDCT_2D.this.dctRows.forward(
//									FloatDCT_2D.this.t, this.val$startt,
//									this.val$scale);
//						else
//							FloatDCT_2D.this.dctRows.inverse(
//									FloatDCT_2D.this.t, this.val$startt,
//									this.val$scale);
//						for (j = 0; j < FloatDCT_2D.this.rows; ++j)
//							this.val$a[j][this.val$n0] = FloatDCT_2D
//									.access$400(FloatDCT_2D.this)[(this.val$startt + j)];
//					}
//				}
//			});
//		}
//		ConcurrencyUtils.waitForCompletion(arrayOfFuture);
//	}
//
//	private void ddxt2d0_subth(int paramInt, float[] paramArrayOfFloat,
//			boolean paramBoolean) {
//		int i = (ConcurrencyUtils.getNumberOfThreads() > this.rows) ? this.rows
//				: ConcurrencyUtils.getNumberOfThreads();
//		Future[] arrayOfFuture = new Future[i];
//		for (int j = 0; j < i; ++j) {
//			int k = j;
//			arrayOfFuture[j] = ConcurrencyUtils.submit(new Runnable(paramInt,
//					k, i, paramArrayOfFloat, paramBoolean) {
//				public void run() {
//					int i;
//					if (this.val$isgn == -1) {
//						i = this.val$n0;
//						while (i < FloatDCT_2D.this.rows) {
//							FloatDCT_2D.this.dctColumns.forward(this.val$a, i
//									* FloatDCT_2D.this.columns, this.val$scale);
//							i += this.val$nthreads;
//						}
//					} else {
//						i = this.val$n0;
//						while (i < FloatDCT_2D.this.rows) {
//							FloatDCT_2D.this.dctColumns.inverse(this.val$a, i
//									* FloatDCT_2D.this.columns, this.val$scale);
//							i += this.val$nthreads;
//						}
//					}
//				}
//			});
//		}
//		ConcurrencyUtils.waitForCompletion(arrayOfFuture);
//	}
//
//	private void ddxt2d0_subth(int paramInt, float[][] paramArrayOfFloat,
//			boolean paramBoolean) {
//		int i = (ConcurrencyUtils.getNumberOfThreads() > this.rows) ? this.rows
//				: ConcurrencyUtils.getNumberOfThreads();
//		Future[] arrayOfFuture = new Future[i];
//		for (int j = 0; j < i; ++j) {
//			int k = j;
//			arrayOfFuture[j] = ConcurrencyUtils.submit(new Runnable(paramInt,
//					k, i, paramArrayOfFloat, paramBoolean) {
//				public void run() {
//					int i;
//					if (this.val$isgn == -1) {
//						i = this.val$n0;
//						while (i < FloatDCT_2D.this.rows) {
//							FloatDCT_2D.this.dctColumns.forward(this.val$a[i],
//									this.val$scale);
//							i += this.val$nthreads;
//						}
//					} else {
//						i = this.val$n0;
//						while (i < FloatDCT_2D.this.rows) {
//							FloatDCT_2D.this.dctColumns.inverse(this.val$a[i],
//									this.val$scale);
//							i += this.val$nthreads;
//						}
//					}
//				}
//			});
//		}
//		ConcurrencyUtils.waitForCompletion(arrayOfFuture);
//	}
//
//	private void ddxt2d_sub(int paramInt, float[] paramArrayOfFloat,
//			boolean paramBoolean) {
//		int k;
//		int i;
//		if (this.columns > 2) {
//			int l;
//			int j;
//			if (paramInt == -1)
//				for (k = 0; k < this.columns; k += 4) {
//					for (l = 0; l < this.rows; ++l) {
//						i = l * this.columns + k;
//						j = this.rows + l;
//						this.t[l] = paramArrayOfFloat[i];
//						this.t[j] = paramArrayOfFloat[(i + 1)];
//						this.t[(j + this.rows)] = paramArrayOfFloat[(i + 2)];
//						this.t[(j + 2 * this.rows)] = paramArrayOfFloat[(i + 3)];
//					}
//					this.dctRows.forward(this.t, 0, paramBoolean);
//					this.dctRows.forward(this.t, this.rows, paramBoolean);
//					this.dctRows.forward(this.t, 2 * this.rows, paramBoolean);
//					this.dctRows.forward(this.t, 3 * this.rows, paramBoolean);
//					for (l = 0; l < this.rows; ++l) {
//						i = l * this.columns + k;
//						j = this.rows + l;
//						paramArrayOfFloat[i] = this.t[l];
//						paramArrayOfFloat[(i + 1)] = this.t[j];
//						paramArrayOfFloat[(i + 2)] = this.t[(j + this.rows)];
//						paramArrayOfFloat[(i + 3)] = this.t[(j + 2 * this.rows)];
//					}
//				}
//			else
//				for (k = 0; k < this.columns; k += 4) {
//					for (l = 0; l < this.rows; ++l) {
//						i = l * this.columns + k;
//						j = this.rows + l;
//						this.t[l] = paramArrayOfFloat[i];
//						this.t[j] = paramArrayOfFloat[(i + 1)];
//						this.t[(j + this.rows)] = paramArrayOfFloat[(i + 2)];
//						this.t[(j + 2 * this.rows)] = paramArrayOfFloat[(i + 3)];
//					}
//					this.dctRows.inverse(this.t, 0, paramBoolean);
//					this.dctRows.inverse(this.t, this.rows, paramBoolean);
//					this.dctRows.inverse(this.t, 2 * this.rows, paramBoolean);
//					this.dctRows.inverse(this.t, 3 * this.rows, paramBoolean);
//					for (l = 0; l < this.rows; ++l) {
//						i = l * this.columns + k;
//						j = this.rows + l;
//						paramArrayOfFloat[i] = this.t[l];
//						paramArrayOfFloat[(i + 1)] = this.t[j];
//						paramArrayOfFloat[(i + 2)] = this.t[(j + this.rows)];
//						paramArrayOfFloat[(i + 3)] = this.t[(j + 2 * this.rows)];
//					}
//				}
//		} else {
//			if (this.columns != 2)
//				return;
//			for (k = 0; k < this.rows; ++k) {
//				i = k * this.columns;
//				this.t[k] = paramArrayOfFloat[i];
//				this.t[(this.rows + k)] = paramArrayOfFloat[(i + 1)];
//			}
//			if (paramInt == -1) {
//				this.dctRows.forward(this.t, 0, paramBoolean);
//				this.dctRows.forward(this.t, this.rows, paramBoolean);
//			} else {
//				this.dctRows.inverse(this.t, 0, paramBoolean);
//				this.dctRows.inverse(this.t, this.rows, paramBoolean);
//			}
//			for (k = 0; k < this.rows; ++k) {
//				i = k * this.columns;
//				paramArrayOfFloat[i] = this.t[k];
//				paramArrayOfFloat[(i + 1)] = this.t[(this.rows + k)];
//			}
//		}
//	}
//
//	private void ddxt2d_sub(int paramInt, float[][] paramArrayOfFloat,
//			boolean paramBoolean) {
//		int j;
//		if (this.columns > 2) {
//			int k;
//			int i;
//			if (paramInt == -1)
//				for (j = 0; j < this.columns; j += 4) {
//					for (k = 0; k < this.rows; ++k) {
//						i = this.rows + k;
//						this.t[k] = paramArrayOfFloat[k][j];
//						this.t[i] = paramArrayOfFloat[k][(j + 1)];
//						this.t[(i + this.rows)] = paramArrayOfFloat[k][(j + 2)];
//						this.t[(i + 2 * this.rows)] = paramArrayOfFloat[k][(j + 3)];
//					}
//					this.dctRows.forward(this.t, 0, paramBoolean);
//					this.dctRows.forward(this.t, this.rows, paramBoolean);
//					this.dctRows.forward(this.t, 2 * this.rows, paramBoolean);
//					this.dctRows.forward(this.t, 3 * this.rows, paramBoolean);
//					for (k = 0; k < this.rows; ++k) {
//						i = this.rows + k;
//						paramArrayOfFloat[k][j] = this.t[k];
//						paramArrayOfFloat[k][(j + 1)] = this.t[i];
//						paramArrayOfFloat[k][(j + 2)] = this.t[(i + this.rows)];
//						paramArrayOfFloat[k][(j + 3)] = this.t[(i + 2 * this.rows)];
//					}
//				}
//			else
//				for (j = 0; j < this.columns; j += 4) {
//					for (k = 0; k < this.rows; ++k) {
//						i = this.rows + k;
//						this.t[k] = paramArrayOfFloat[k][j];
//						this.t[i] = paramArrayOfFloat[k][(j + 1)];
//						this.t[(i + this.rows)] = paramArrayOfFloat[k][(j + 2)];
//						this.t[(i + 2 * this.rows)] = paramArrayOfFloat[k][(j + 3)];
//					}
//					this.dctRows.inverse(this.t, 0, paramBoolean);
//					this.dctRows.inverse(this.t, this.rows, paramBoolean);
//					this.dctRows.inverse(this.t, 2 * this.rows, paramBoolean);
//					this.dctRows.inverse(this.t, 3 * this.rows, paramBoolean);
//					for (k = 0; k < this.rows; ++k) {
//						i = this.rows + k;
//						paramArrayOfFloat[k][j] = this.t[k];
//						paramArrayOfFloat[k][(j + 1)] = this.t[i];
//						paramArrayOfFloat[k][(j + 2)] = this.t[(i + this.rows)];
//						paramArrayOfFloat[k][(j + 3)] = this.t[(i + 2 * this.rows)];
//					}
//				}
//		} else {
//			if (this.columns != 2)
//				return;
//			for (j = 0; j < this.rows; ++j) {
//				this.t[j] = paramArrayOfFloat[j][0];
//				this.t[(this.rows + j)] = paramArrayOfFloat[j][1];
//			}
//			if (paramInt == -1) {
//				this.dctRows.forward(this.t, 0, paramBoolean);
//				this.dctRows.forward(this.t, this.rows, paramBoolean);
//			} else {
//				this.dctRows.inverse(this.t, 0, paramBoolean);
//				this.dctRows.inverse(this.t, this.rows, paramBoolean);
//			}
//			for (j = 0; j < this.rows; ++j) {
//				paramArrayOfFloat[j][0] = this.t[j];
//				paramArrayOfFloat[j][1] = this.t[(this.rows + j)];
//			}
//		}
//	}
//}