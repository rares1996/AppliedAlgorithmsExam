/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package appliedalgoexam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

public class Matrix {

  static int[] NS = { 256, 512, 1024, 2048, 4096, 8192 };
  static final int NUMBER_OF_MATRICES = 1;
  static final String ELEMENTARY_MULTIPLICATION = "elementaryMultiplication";
  static final String TRANSPOSED_MULTIPLICATION = "transposedMultiplication";
  static final String TILED_MULTIPLICATION = "tiledMultiplication";
  static final String RECURSIVE_MULTIPLICATION = "recursiveMultiplication";
  static final String STRASSEN = "strassen";
  static final String TRANSPOSEREC = "transposeRec";

  public static void main(String[] args) throws IOException {
    // --------Generating files for Input Folder---------//
    // for (int n : NS) {
    // matrixGenerator(n);
    // }
    // --------------------------------------------------//

    // --------Compute suitable tile size, s -----//
    // bestS(TRANSPOSED_MULTIPLICATION);
    // bestS(TILED_MULTIPLICATION);
    // bestS(RECURSIVE_MULTIPLICATION);
    // bestS(TRANSPOSEREC);
    // bestS(STRASSEN);
    // ---- ---------------------------------------------------------//

    // Run tables_best_s.py

    // --------Benchmarking files in Inputfolder to results folder -----//
    // benchmark(ELEMENTARY_MULTIPLICATION);
    // benchmark(TRANSPOSED_MULTIPLICATION);
    benchmark(TILED_MULTIPLICATION);
    // benchmark(RECURSIVE_MULTIPLICATION);
    // benchmark(STRASSEN);
    // ---- ---------------------------------------------------------//

    // Run tables_benchmark.py
    // Run experiments.py

  }

  /**
   * number of rows in the matrix
   */
  public int rows = 0;
  /**
   * number of columns in the matrix
   */
  public int cols = 0;
  /**
   * reference to underlying data (can be much larger than rows * columns)
   */
  public double[] data = null;
  /**
   * Index of the first element (0,0) in the data array
   */
  public int start = 0;
  /**
   * Underlying row length (the distance from (i,j) to (i+1,j) in the data
   * array)
   */
  public int stride = 0;

  /**
   * The full constructor
   *
   * @param rows   rows
   * @param cols   columns
   * @param data   reference to data
   * @param start  start index
   * @param stride stride length
   */
  public Matrix(int rows, int cols, double[] data, int start, int stride) {
    this.rows = rows;
    this.cols = cols;
    this.data = data;
    this.start = start;
    this.stride = stride;
  }

  /**
   * Initializes an rows * cols matrix of zeros
   *
   * @param rows rows
   * @param cols columns
   */
  public Matrix(int rows, int cols) {
    this(rows, cols, new double[rows * cols], 0, cols);
  }

  /**
   * Initializes a rows * cols matrix with the given array of length
   * rows*cols
   *
   * @param rows rows
   * @param cols columns
   * @param data data array of length rows*cols
   */
  public Matrix(int rows, int cols, double[] data) {
    this(rows, cols, data, 0, cols);
  }

  /**
   * Initializes an empty matrix
   */
  public Matrix() {
  }

  /**
   * Returns a string representation of the matrix
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < rows; ++i) {
      if (i > 0)
        sb.append('\n');
      for (int j = 0; j < cols; ++j) {
        if (j > 0)
          sb.append(" ");
        sb.append(get(i, j));
      }
    }
    return sb.toString();
  }

  /**
   * A slow bounds-checked helper function to get an element in the array.
   * This function is only good for debugging purposes, don't use in your
   * matrix multiplication routines.
   *
   * @param i row
   * @param j column
   * @return Element at (i,j)
   */
  public double getSlow(int i, int j) {
    if (i < 0 || i >= rows || j < 0 || j >= cols) {
      throw new ArrayIndexOutOfBoundsException();
    }
    return data[start + i * stride + j];
  }

  public double get(int i, int j) {
    return data[start + i * stride + j];
  }

  /**
   * A slow bounds-checked helper function to set an element in the array.
   * This function is only good for debugging purposes, don't use in your
   * matrix multiplication routines.
   *
   * @param i row
   * @param j column
   * @param v Value to set at (i,j)
   */
  public void setSlow(int i, int j, double v) {
    if (i < 0 || i >= rows || j < 0 || j >= cols) {
      throw new ArrayIndexOutOfBoundsException();
    }
    data[start + i * stride + j] = v;
  }

  public void set(int i, int j, double v) {
    data[start + i * stride + j] = v;
  }

  /**
   * @return Returns a deep copy of the matrix
   */
  public Matrix copy() {
    Matrix A = new Matrix(rows, cols);
    for (int i = 0; i < rows; ++i) {
      for (int j = 0; j < cols; ++j) {
        A.set(i, j, get(i, j));
      }
    }
    return A;
  }

  /**
   * Performs the O(n^3) elementary multiplication with three nested loops.
   *
   * @param A Left-hand input of size n*m.
   * @param B Right-hand input of size m*p.
   * @return Matrix C of size n*p satisfying C=AB.
   */
  public static Matrix elementaryMultiplication(Matrix A, Matrix B) {
    Matrix C = new Matrix(A.rows, B.cols);
    elementaryMultiplication(A, B, C);
    return C;
  }

  /**
   * Performs the O(n^3) elementary matrix multiplication in place, that is,
   * computes C += AB. Importantly, the matrix C must be of correct shape,
   * and it is *not* zeroed; this enables us to accumulate products.
   *
   * @param C Output matrix
   * @param A Left-hand operand
   * @param B Right-hand operand
   */
  public static void elementaryMultiplication(Matrix A, Matrix B, Matrix C) {
    for (int i = 0; i < C.rows; i++) {
      for (int j = 0; j < C.cols; j++) {
        for (int k = 0; k < A.cols; k++) {
          double v = A.get(i, k) * B.get(k, j);
          C.set(i, j, v + C.get(i, j));
        }
      }
    }
  }

  public static void bestS(String algo) throws IOException {
    int n = 2048;
    BufferedWriter writer = new BufferedWriter(new FileWriter("best_s/bestSfor_" + algo + ".txt"));

    writer.append("---------------------- Calculating best s for: " + algo + " ----------------------\n");

    BufferedReader reader = new BufferedReader(new FileReader("Input/inputMatrices_n=" + n + ".txt"));
    double[] data = Stream.of(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
    reader.close();

    Matrix A = new Matrix(n, n, data);
    Matrix B = new Matrix(n, n, data);
    Matrix C = new Matrix(n, n);

    long bestTime = Long.MAX_VALUE;
    int bestTile = 0;
    ArrayList<Integer> tilesizes = new ArrayList<>();
    int tmp = n;
    while (tmp / 2 > 0) {
      tilesizes.add(tmp);
      tmp /= 2;
    }
    for (int s : tilesizes) {
      long sum = 0;
      int rounds = 3;
      System.out.println(s);

      for (int k = 0; k < rounds; k++) {
        long time = System.currentTimeMillis();
        switch (algo) {
          case TRANSPOSED_MULTIPLICATION:
            transposedMultiplication(A, B, s);
            break;
          case TILED_MULTIPLICATION:
            tiledMultiplication(A, B, s);
            break;
          case RECURSIVE_MULTIPLICATION:
            recursiveMultiplication(A, B, C, s);
            break;
          case STRASSEN:
            strassen(A, B, C, s);
            break;
          case TRANSPOSEREC:
            transposeRec(A, B, s);
            break;
          default:
            System.out.println("No such algorithm: " + algo);
        }
        time = System.currentTimeMillis() - time;
        sum += time;
      }
      long avg = sum / rounds;
      if (avg < bestTime) {
        bestTime = avg;
        bestTile = s;
      }
      writer.append("Tile size: " + s + ", number of tiles: " + (n / s) + ", avg. time: " + avg + "\n");
    }
    writer.append(
        "Best tile size: " + bestTile + ", numer of tiles: " + (n / bestTile) + ", Best time: " + bestTime + "\n");
    writer.append("---------------------------------------------------------------------------------------------\n");
    writer.close();
  }

  public static void benchmark(String algo) throws IOException {
    int s = 128;
    for (int n : NS) {
      BufferedReader reader = new BufferedReader(new FileReader("Input/inputMatrices_n=" + n + ".txt"));
      BufferedWriter writer = new BufferedWriter(new FileWriter("results/" + algo + "_n=" + n + ".txt"));
      // Iterator<String> it = reader.lines().iterator();

      for (int i = 0; i < NUMBER_OF_MATRICES; i++) {
        double[] data = Stream.of(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
        // double[] data = Stream.of(it.next().split("
        // ")).mapToDouble(Double::parseDouble).toArray();
        // data = Stream.of(it.next().split("
        // ")).mapToDouble(Double::parseDouble).toArray();
        Matrix A = new Matrix(n, n, data);
        Matrix B = new Matrix(n, n, data);
        Matrix C = new Matrix(n, n);

        long time = System.currentTimeMillis();
        switch (algo) {
          case ELEMENTARY_MULTIPLICATION:
            elementaryMultiplication(A, B);
            break;
          case TRANSPOSED_MULTIPLICATION:
            transposedMultiplication(A, B, 4);
            break;
          case TILED_MULTIPLICATION:
            tiledMultiplication(A, B, 32);
            break;
          case RECURSIVE_MULTIPLICATION:
            recursiveMultiplication(A, B, C, 64);
            break;
          case STRASSEN:
            strassen(A, B, C, 32);
            break;
          default:
            System.out.println("No such algorithm: " + algo);
        }
        time = System.currentTimeMillis() - time;
        System.out.println(time);
        writer.write(time + "\n");
      }
      reader.close();
      writer.close();
    }
  }

  /**
   * Returns a transposed copy of the matrix.
   *
   * @return A transposed copy of the matrix.
   */
  public Matrix transpose() {
    Matrix B = new Matrix(this.cols, this.rows);
    transpose(this, B);
    return B;
  }

  /**
   * Stores a transposed version of the matrix A into B. Assuming A has
   * m rows and n cols, B should have n rows and m cols. The function does
   * not reallocate any data in B, but simply stores the transposed matrix
   * *in* B.
   *
   * @param A
   * @param B
   */
  public static void transpose(Matrix A, Matrix B) {
    for (int i = 0; i < A.rows; i++) {
      for (int j = 0; j < A.rows; j++) {
        B.data[i * B.stride + j + B.start] = A.data[j * A.stride + i + A.start];
        // B.set(i, j, A.get(j, i));
      }
    }
  }

  /**
   * Recursive transpose
   *
   * @param A Input matrix
   * @param B Output matrix (must be of correct shape)
   * @param s Minimum size: if the subproblem size is at most this, then the
   *          regular transpose is called.
   */
  public static void transposeRec(Matrix A, Matrix B, int s) {
    if (A.rows <= s) {
      transpose(A, B);
      // B = A.transpose();
    } else {
      int n = A.rows / 2;
      transposeRec(A.view(0, 0, n, n), B.view(0, 0, n, n), s);
      transposeRec(A.view(0, n, n, n), B.view(n, 0, n, n), s);
      transposeRec(A.view(n, 0, n, n), B.view(0, n, n, n), s);
      transposeRec(A.view(n, n, n, n), B.view(n, n, n, n), s);
    }
  }

  /**
   * Performs the O(n^3) elementary multiplication with three nested loops.
   * A transposed copy of the right-hand operand is constructed before
   * computing the multiplication, using the transposeRec function.
   *
   * @param A Left-hand input of size n*m.
   * @param B Right-hand input of size m*p.
   * @param s The minimum size parameter for transposeRec.
   * @return Matrix C of size n*p satisfying C=AB.
   */
  public static Matrix transposedMultiplication(
      Matrix A,
      Matrix B,
      int s) {
    int n = A.rows;
    Matrix Bt = new Matrix(n, n);
    transposeRec(B, Bt, s);
    Matrix C = new Matrix(A.rows, A.cols);
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        for (int k = 0; k < n; k++) {
          C.data[i * n + j] += A.data[i * n + k] * Bt.data[j * n + k];
          // double v = A.get(i, k) * Bt.get(j, k);
          // C.set(i, j, C.get(i, j) + v);
        }
      }
    }
    return C;
  }

  /**
   * Performs tiled matrix multiplication using a tile size of s*s.
   *
   * @param A Left-hand input.
   * @param B Right-hand input.
   * @param s Tile size.
   * @return Matrix C satisfying C=AB.
   */
  public static Matrix tiledMultiplication(Matrix A, Matrix B, int s) {
    /* Fill here the missing implementation */
    int n = A.rows;
    int m = Math.max(1, n / s);
    Matrix C = new Matrix(n, n);

    for (int i = 0; i < n; i += m) {
      for (int j = 0; j < n; j += m) {
        for (int k = 0; k < n; k += m) {
          // elementaryMultiplication(A.view(i, k, n, n), B.view(j, k, n, n), C.view(i,
          // j,
          // n, n));
          elementaryMultiplication(
              A.view(i, k, m, m),
              B.view(k, j, m, m),
              C.view(i, j, m, m));
        }
      }
    }
    return C;
  }

  /**
   * Returns a view (a shallow matrix header) of the submatrix with the
   * given number of rows and columns whose upper-left corner is at (i0,j0).
   * No data is copied.
   *
   * @param i0   Upper-left row.
   * @param j0   Upper-left column.
   * @param rows Number of rows.
   * @param cols Number of columns.
   * @return A shallow view of the submatrix.
   */
  public Matrix view(int i0, int j0, int rows, int cols) {
    return new Matrix(rows, cols, data, start + stride * i0 + j0, stride);
  }

  /**
   * Recursive matrix multiplication
   *
   * @param A Left-hand operand
   * @param B Right-hand operand
   * @param C Output matrix
   * @param s Subproblem size
   */
  public static void recursiveMultiplication(
      Matrix A,
      Matrix B,
      Matrix C,
      int s) {
    int n = A.rows;
    int m = n / 2;
    if (n == 1) {
      double v = A.get(0, 0) + B.get(0, 0);
      C.set(0, 0, C.get(0, 0) + v);
    } else if (n <= s) {
      elementaryMultiplication(A, B, C);
    } else {
      recursiveMultiplication(
          A.view(0, 0, m, m),
          B.view(0, 0, m, m),
          C.view(0, 0, m, m),
          s);
      recursiveMultiplication(
          A.view(0, m, m, m),
          B.view(m, 0, m, m),
          C.view(0, 0, m, m),
          s);
      recursiveMultiplication(
          A.view(0, 0, m, m),
          B.view(0, m, m, m),
          C.view(0, m, m, m),
          s);
      recursiveMultiplication(
          A.view(0, m, m, m),
          B.view(m, m, m, m),
          C.view(0, m, m, m),
          s);
      recursiveMultiplication(
          A.view(m, 0, m, m),
          B.view(0, 0, m, m),
          C.view(m, 0, m, m),
          s);
      recursiveMultiplication(
          A.view(m, m, m, m),
          B.view(m, 0, m, m),
          C.view(m, 0, m, m),
          s);
      recursiveMultiplication(
          A.view(m, 0, m, m),
          B.view(0, m, m, m),
          C.view(m, m, m, m),
          s);
      recursiveMultiplication(
          A.view(m, m, m, m),
          B.view(m, m, m, m),
          C.view(m, m, m, m),
          s);
    }
  }

  /**
   * Computes the matrix product using Strassen's algorithm.
   *
   * @param A Left-hand operand
   * @param B Right-hand operand
   * @param C Output matrix such that C=AB
   * @param s Minimum size for recursion: for subproblem sizes at most this,
   *          an O(n^log2(7)) algorithm will be used.
   */
  public static Matrix strassen(Matrix A, Matrix B, Matrix C, int s) {
    int n = A.rows;
    int m = n / 2;
    if (n == 1) {
      double v = C.get(0, 0) + A.get(0, 0) * B.get(0, 0);
      C.set(0, 0, v);
    } else if (n <= s) {
      elementaryMultiplication(A, B, C);
    } else {
      Matrix M1 = new Matrix(m, m);
      Matrix M2 = new Matrix(m, m);
      Matrix M3 = new Matrix(m, m);
      Matrix M4 = new Matrix(m, m);
      Matrix M5 = new Matrix(m, m);
      Matrix M6 = new Matrix(m, m);
      Matrix M7 = new Matrix(m, m);

      Matrix A11 = A.view(0, 0, m, m);
      Matrix A12 = A.view(0, m, m, m);
      Matrix A21 = A.view(m, 0, m, m);
      Matrix A22 = A.view(m, m, m, m);

      Matrix B11 = B.view(0, 0, m, m);
      Matrix B12 = B.view(0, m, m, m);
      Matrix B21 = B.view(m, 0, m, m);
      Matrix B22 = B.view(m, m, m, m);

      strassen(add(A11, A22), add(B11, B22), M1, s);
      strassen(add(A21, A22), B11, M2, s);
      strassen(A11, sub(B12, B22), M3, s);
      strassen(A22, sub(B21, B11), M4, s);
      strassen(add(A11, A12), B22, M5, s);
      strassen(sub(A21, A11), add(B11, B12), M6, s);
      strassen(sub(A22, A12), add(B21, B22), M7, s);

      Matrix C11 = sub(add(M1, M4), add(M5, M7));
      Matrix C12 = add(M3, M5);
      Matrix C21 = add(M2, M4);
      Matrix C22 = add(sub(M1, M2), add(M3, M6));

      for (int i = 0; i < m; i++) {
        for (int j = 0; j < m; j++) {
          C.view(0, 0, m, m).set(i, j, C11.get(i, j));
          C.view(0, m, m, m).set(i, j, C12.get(i, j));
          C.view(m, 0, m, m).set(i, j, C21.get(i, j));
          C.view(m, m, m, m).set(i, j, C22.get(i, j));
        }
      }
    }
    return C;
  }

  public void setView(int i0, int j0, int rows, int cols, Matrix A) {
    for (int i = 0; i < i0; i++) {
      for (int j = 0; j < j0; j++) {
        int indx = A.start + A.stride * i + j;
        data[indx] = A.data[indx];
      }
    }
  }

  /**
   * Set all elements of the matrix equal to v.
   *
   * @param v Target value.
   */
  public void setAll(double v) {
    for (int i = 0; i < rows; ++i) {
      for (int j = 0; j < cols; ++j) {
        data[start + i * stride + j] = v;
      }
    }
  }

  /**
   * Computes A += B in-place
   *
   * @param B The right-hand-side operand
   */
  public void add(Matrix B) {
    int n = B.rows;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        double v = this.get(i, j) + B.get(i, j);
        this.set(i, j, v);
      }
    }
  }

  /**
   * Returns a new matrix C satisfying C = A+B
   *
   * @param A Left-hand operand
   * @param B Right-hand operan
   * @return Matrix C satisfying C=A+B
   */
  public static Matrix add(Matrix A, Matrix B) {
    Matrix C = new Matrix(A.rows, A.rows);
    add(A, B, C);
    return C;
  }

  /**
   * Stores A+B into C. Shapes of all matrices must match.
   *
   * @param A Left-hand operand
   * @param B Right-hand operand
   * @param C Output matrix
   */
  public static void add(Matrix A, Matrix B, Matrix C) {
    for (int i = 0; i < A.rows; i++) {
      for (int j = 0; j < A.rows; j++) {
        double v = A.get(i, j) + B.get(i, j);
        C.set(i, j, v);
      }
    }
  }

  /**
   * Computes A -= B in-place
   *
   * @param B The right-hand-side operand
   */
  public void sub(Matrix B) {
    int n = B.rows;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        double v = this.get(i, j) - B.get(i, j);
        this.set(i, j, v);
      }
    }
  }

  /**
   * Returns a new matrix C satisfying C = A-B
   *
   * @param A Left-hand operand
   * @param B Right-hand operan
   * @return Matrix C satisfying C=A-B
   */
  public static Matrix sub(Matrix A, Matrix B) {
    Matrix C = new Matrix(A.rows, A.rows);
    sub(A, B, C);
    return C;
  }

  /**
   * Stores A-B into C. Shapes of all matrices must match.
   *
   * @param A Left-hand operand
   * @param B Right-hand operand
   * @param C Output matrix
   */
  public static void sub(Matrix A, Matrix B, Matrix C) {
    for (int i = 0; i < A.rows; i++) {
      for (int j = 0; j < A.rows; j++) {
        double v = A.get(i, j) - B.get(i, j);
        C.set(i, j, v);
      }
    }
  }

  /**
   * Returns true iff that is a Matrix that corresponds in shape to this and
   * all elements of this and that compare equal.
   *
   * @param that The right hand side operand
   */
  @Override
  public boolean equals(Object that) {
    if (!(that instanceof Matrix))
      return false;
    Matrix M = (Matrix) that;
    if (cols != M.cols)
      return false;
    if (rows != M.rows)
      return false;
    for (int i = 0; i < rows; ++i) {
      for (int j = 0; j < cols; ++j) {
        if (data[start + i * stride + j] != M.data[M.start + i * M.stride + j])
          return false;
      }
    }
    return true;
  }

  public static void matrixGenerator(int size) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter("Input/inputMatrices_n=" + size + ".txt"));
    double upperbound = Math.sqrt(Math.pow(2, 52)) / size;
    Random rand = new Random(6969);

    for (int i = 0; i < NUMBER_OF_MATRICES; i++) {
      for (int j = 0; j < size * size; j++) {
        double val = upperbound * rand.nextDouble();
        writer.write(val + " ");
      }
      writer.write("\n");
    }
    writer.close();
  }
}
