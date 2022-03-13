package com.example.matrix;

/**
 * @author Alexanrd Spaskin
 */
public class MultiplyMatrix extends Thread{
    private int[][] a;
    private int[][] b;
    private int[][] result;
    private int n;
    private int threadsCount;
    private int row;

    public MultiplyMatrix() {}

    public MultiplyMatrix(int[][] a, int[][] b, int[][] result, int row, int threadsCount) {
        this.a = a;
        this.b = b;
        this.result = result;
        this.n = b.length;
        this.threadsCount = threadsCount;
        this.row = row;
    }

    @Override
    public void run(){
        System.out.println("calculation on line " + row);
        for (int i = 0; i <= row; i++){
            for (int j = 0; j < result[i].length; j++){
                result[i][j] = calcValue(i, j);
            }
        }
    }

    private int calcValue(int row, int col) {
        int c = 0;
        for (int i = 0; i < n; i++) {
            c += a[row][i] * b[i][col];
        }
        return c;
    }

    public int[][] multiply(int[][] a, int[][] b){
        if (a == null || a.length == 0 || a[0] == null || a[0].length == 0) {
            throw new IllegalArgumentException("a");
        }
        if (b == null || b.length == 0 || b[0] == null || b[0].length == 0) {
            throw new IllegalArgumentException("b");
        }
        if (a[0].length != b.length) {
            throw new IllegalArgumentException("wrong matrix sizes");
        }

        int rowResult = a.length;
        int columnResult = b[0].length;
        result = new int[rowResult][columnResult];

        threadsCount = a.length;
        Thread[] threads = new Thread[threadsCount];

        for(int i = 0; i < threadsCount; i++){
            threads[i] = new MultiplyMatrix(a, b, result, i, threadsCount);
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[][] a = {
                {1, 2},
                {3, 4}
        };

        int[][] b = {
                {4, 3},
                {2, 1}
        };

        int[][] c = new MultiplyMatrix().multiply(a, b);

        for (int[] ints : c) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }

}
