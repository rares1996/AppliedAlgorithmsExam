package appliedalgoexam;

import java.util.Arrays;
import java.util.Scanner;

public class CodeJudge {

    public static void matrixElement() {
        Scanner scan = new Scanner(System.in);
        String[] line = scan.nextLine().split(" ");
        int n = Integer.parseInt(line[0]);
        int m = Integer.parseInt(line[1]);
        char letter = line[2].charAt(0);
        String[] elements = scan.nextLine().split(" ");
        while (scan.hasNext()) {
            line = scan.nextLine().split(" ");
            int i = Integer.parseInt(line[0]);
            int j = Integer.parseInt(line[1]);
            System.out.println(letter == 'C' ? elements[i * m + j] : elements[j * n + i]);
        }
        scan.close();
    }

    public static void elementaryMatrixMultiplication() {
        Scanner scan = new Scanner(System.in);
        String[] line = scan.nextLine().split(" ");
        int n = Integer.parseInt(line[0]);
        int m = Integer.parseInt(line[1]);
        int p = Integer.parseInt(line[2]);
        boolean rowMajorOrder = line[3].equals("C");
        line = scan.nextLine().split(" ");
        int[] A = new int[n * m];
        if (rowMajorOrder) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    A[i * m + j] = Integer.parseInt(line[i * m + j]);
                }
            }
        } else {
            for (int j = 0; j < m; j++) {
                for (int i = 0; i < n; i++) {
                    A[i * m + j] = Integer.parseInt(line[j * n + i]);
                }
            }
        }
        line = scan.nextLine().split(" ");
        int[] B = new int[m * p];
        if (rowMajorOrder) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < p; j++) {
                    B[i * p + j] = Integer.parseInt(line[i * p + j]);
                }
            }
        } else {
            for (int j = 0; j < p; j++) {
                for (int i = 0; i < m; i++) {
                    B[i * p + j] = Integer.parseInt(line[j * m + i]);
                }
            }
        }
        scan.close();
        int[] C = new int[n * p];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                for (int k = 0; k < m; k++) {
                    C[i * p + j] += A[i * m + k] * B[k * p + j];
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        if (rowMajorOrder) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < p; j++) {
                    sb.append(C[i * p + j] + " ");
                }
            }
        } else {
            for (int j = 0; j < p; j++) {
                for (int i = 0; i < n; i++) {
                    sb.append(C[i * p + j] + " ");
                }
            }
        }
        System.out.println(sb.toString());
    }

    public static void submatrix() {
        Scanner scan = new Scanner(System.in);
        int n = Integer.parseInt(scan.nextLine());
        String[] line = scan.nextLine().split(" ");
        int i = Integer.parseInt(line[0]);
        int j = Integer.parseInt(line[1]);
        line = scan.nextLine().split(" ");
        scan.close();
        long[] A = Arrays.stream(line).mapToLong(Long::parseLong).toArray(); 
        StringBuilder sb = new StringBuilder();
        for (int k = i*n/2; k < i*n/2 + n/2; k++) {
            for (int l = j*n/2; l < j*n/2 + n/2; l++) {
                sb.append(A[k*n + l] + " ");
            }
        }
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = Integer.parseInt(scan.nextLine());
        long[] A = Arrays.stream(scan.nextLine().split(" ")).mapToLong(Long::parseLong).toArray();
        long[] B = Arrays.stream(scan.nextLine().split(" ")).mapToLong(Long::parseLong).toArray();
        
    } 


    public static void emm() {
        Scanner scan = new Scanner(System.in);
        String[] line = scan.nextLine().split(" ");
        int n = Integer.parseInt(line[0]);
        int m = Integer.parseInt(line[1]);
        int p = Integer.parseInt(line[2]);
        boolean rowMajorOrder = line[3].equals("C");
        line = scan.nextLine().split(" ");
        Matrix A = new Matrix(n, m);
        if (rowMajorOrder) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    A.data[i * m + j] = Integer.parseInt(line[i * m + j]);
                }
            }
        } else {
            for (int j = 0; j < m; j++) {
                for (int i = 0; i < n; i++) {
                    A.data[i * m + j] = Integer.parseInt(line[j * n + i]);
                }
            }
        }
        line = scan.nextLine().split(" ");
        System.out.println("\n" + A.toString());
        Matrix B = new Matrix(m, p);
        if (rowMajorOrder) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < p; j++) {
                    B.data[i * p + j] = Integer.parseInt(line[i * p + j]);
                }
            }
        } else {
            for (int j = 0; j < p; j++) {
                for (int i = 0; i < m; i++) {
                    B.data[i * p + j] = Integer.parseInt(line[j * m + i]);
                }
            }
        }
        System.out.println("\n" + B.toString());
        scan.close();
        Matrix C = Matrix.elementaryMultiplication(A, B);
        System.out.println("\n" + C.toString());
    }

    public static void elementaryMatrixMultiplication2D() {
        Scanner scan = new Scanner(System.in);
        String[] line = scan.nextLine().split(" ");
        int n = Integer.parseInt(line[0]);
        int m = Integer.parseInt(line[1]);
        int p = Integer.parseInt(line[2]);
        boolean rowMajorOrder = line[3].equals("C");
        line = scan.nextLine().split(" ");
        int[][] A = new int[n][m];
        if (rowMajorOrder) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    A[i][j] = Integer.parseInt(line[i * m + j]);
                }
            }
        } else {
            for (int j = 0; j < m; j++) {
                for (int i = 0; i < n; i++) {
                    A[i][j] = Integer.parseInt(line[j * n + i]);
                }
            }
        }
        line = scan.nextLine().split(" ");
        int[][] B = new int[m][p];
        if (rowMajorOrder) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < p; j++) {
                    B[i][j] = Integer.parseInt(line[i * p + j]);
                }
            }
        } else {
            for (int j = 0; j < p; j++) {
                for (int i = 0; i < m; i++) {
                    B[i][j] = Integer.parseInt(line[j * m + i]);
                }
            }
        }
        scan.close();
        int[][] C = new int[n][p];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                for (int k = 0; k < m; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        if (rowMajorOrder) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < p; j++) {
                    sb.append(C[i][j] + " ");
                }
            }
        } else {
            for (int j = 0; j < p; j++) {
                for (int i = 0; i < n; i++) {
                    sb.append(C[i][j] + " ");
                }
            }
        }
        System.out.println(sb.toString());
    }
}
