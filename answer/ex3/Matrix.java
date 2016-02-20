package pr2Calc;

import java.util.Scanner;

/**
 * 3-3及び3-4も実装しました
 */
public class Matrix {
    //一つ目が縦，二つ目が横，エクセルみたいな
    protected double[][] m_;

    public Matrix(double[][] input) {
        // 二次元配列 input の内容で、フィールド変数 m_[][]の内容を初期化せよ
        m_ = new double[input.length][];
        for (int i = 0; i < input.length; i++) {
            m_[i] = new double[input[i].length];
            for (int j = 0; j < input[i].length; j++) {
                m_[i][j] = input[i][j];
            }
        }
    }

    protected int _getNumOfRow() {
        return m_.length;
    }

    protected int _getNumOfColumn() {
        return m_[0].length;
    }

    protected double _getComponentOf(int row, int column) {
        // 指定した範囲が存在しない場合
        if (row > _getNumOfRow() || column > _getNumOfColumn() || row < 0 || column < 0) {
            System.out.println("指定する要素は存在しません.");
            System.exit(0);
        }
        return m_[row][column];
    }

    // 行列の内容を表示するメソッド（_print以降に適当な名前をつけよ）
    // 必要に応じて、同名で引数の異なるメソッドを複数作成しても良い
//    protected void _print○○(){
//        // 行列内容の表示処理を実装せよ
//    }

    /**
     * 行列を表示します。
     */
    protected void _printMatrix() {
        for (int i = 0; i < _getNumOfRow(); i++) {
            System.out.print("(");
            for (int j = 0; j < _getNumOfColumn(); j++) {
                System.out.print(String.format(" %1$4.1f ", _getComponentOf(i, j)));
            }
            System.out.println(")");
        }
    }

    // 行列同士、もしくは行列と列ベクトルとの積を計算する
    //matrixで見やすくなるかな
    protected Matrix _multiplyMatrix(Matrix target) {
        double[][] retValue = new double[this._getNumOfRow()][target._getNumOfColumn()];

        // 掛けられる行列の列数と掛ける行列の行数が等しいなら
        if (this._getNumOfColumn() == target._getNumOfRow()) {
            // 積の計算処理を実装せよ
            for (int i = 0; i < this._getNumOfRow(); i++) {
                for (int j = 0; j < target._getNumOfColumn(); j++) {
                    for (int k = 0; k < this._getNumOfColumn(); k++) {
                        retValue[i][j] += this._getComponentOf(i, k) * target._getComponentOf(k, j);
                    }
                }
            }

        } else {
            //課題のままだと2で止まる
            System.out.println("要素数が計算できる組み合わせとなっていません");
            System.out.println("エラーですが続行しますか？ y/n");
            if (new Scanner(System.in).next().indexOf("y") == -1) System.exit(0);
            retValue = new double[0][0];

        }
        return new Matrix(retValue);
    }

        /* 上のメソッドの代わりに（もしくは、上のメソッドに加えて）戻り値と引数の
         * 双方を Matrix クラスとした _multiplyMatrix()を作成しても良い
         * どちらが使いやすいかは各自で判断して欲しい
         */

//    /**
//     * 二つのベクトルの内積を戻り値とするメソッド
//     * @param matrix  ベクトルの入った行列
//     * @return  掛け算結果
//     */
//    protected double _getInnerProduct(Matrix matrix) {
//        double result = 0.0;
//        if (this._getNumOfRow() == 1 && matrix._getNumOfRow() == 1) {
//            for (int i = 0; i < this._getNumOfColumn(); i++)
//                result += this._getComponentOf(0, i) * matrix._getComponentOf(0, i);
//            return result;
//        } else if (this._getNumOfRow() == 1 && matrix._getNumOfColumn() == 1) {
//            //_multiplyMatrix()に投げると同じMatrixの掛け算をしてくれる
//            return _multiplyMatrix(matrix)._getComponentOf(0, 0);
//        }
//        System.out.println("行ベクトル同士or行ベクトルと列ベクトル以外の内積を計算しようとしています");
//        System.exit(0);
//        return 0.0;
//    }


    public static void main(String[] args) {
/*
 * main メソッド中で今回作成した内積計算メソッドや行列同士、ベクトルと行列、
 * 行列とベクトルの積を計算するメソッドが正常に動いているかを確認せよ。
 *
 */

// 行列・ベクトル定義・処理の一例 （あくまで一例　課題の要求を満たすよう、
// 以降の部分は自由に記述を修正して構わない）
        Matrix mat0, mat2;
        double[][] A = {
                {1.0, 2.0, 3.0},
                {3.0, 2.0, -1.0},
                {4.0, 2.0, 6.0}},

                B = {
                        {3.0},
                        {-2.0}},
                C = {
                        {8.0, 2.0},
                        {-3.0, 2.0},
                        {1.0, 6.0}};

        //課題3-1
        System.out.println("課題3-1");
        mat0 = new Matrix(A);
        mat0._printMatrix();
        System.out.println();

        //課題3-2
        /*
         * (1)x=(2,3,7), y=8(-1,-2, 2)の内積
         *
         * (2)
         * ( 1  2  3)       ( 5  3  1)
         * ( 3  2 -1)   ×  ( 3 -3  2)
         * ( 4  2  6)
         *
         * (3)
         * ( 3)
         * (-2)    ×( 3  7 -5  2)
         *
         * (4)
         * ( 1  2  3)       ( 8  2)
         * ( 3  2 -1)   ×  (-3  2)
         * ( 4  2  6)       ( 1  6)
         *
         * (5)
         * ( 2 -3)     (-5 -3  1)
         * ( 4  2)  × (-3  3  2)
         *
         */
        double[][] vec1x = {{2.0, -3.0, 7.0}};
        double[][] vec1y = {{-1.0}, {-2.0}, {2.0}};
        double[][] vec2 = {{1.0, 2.0, 3.0}, {3.0, 2.0, -1.0}, {4.0, 2.0, 6.0}};
        double[][] vec3 = {{5.0, 3.0, 1.0}, {3.0, -3.0, 2.0}};
        double[][] vec4 = {{3.0}, {-2.0}};
        double[][] vec5 = {{3.0, 7.0, -5.0, 2.0}};
        double[][] vec6 = {{8.0, 2.0}, {-3.0, 2.0}, {1.0, 6.0}};
        double[][] vec7 = {{2.0, -3.0}, {4, 2}};
        double[][] vec8 = {{-5.0, -3.0, 1.0}, {-3.0, 3.0, 2.0}};

        System.out.println("課題3-2~3-4");
        System.out.print("(1)=");
        //3-4を行うために改変
//        new Matrix(vec1x)._multiplyMatrix(new Matrix(vec1y))._printMatrix();
        System.out.println(new MyVector(new double[]{2.0, -3.0, 7.0}, true)._getInnerProduct(new MyVector(new double[]{-1.0, -2.0, 2.0}, true)));
        System.out.println();

        System.out.println("(2)=");
        new Matrix(vec2)._multiplyMatrix(new Matrix(vec3))._printMatrix();
        System.out.println();
        System.out.println("(3)=");
        new Matrix(vec4)._multiplyMatrix(new Matrix(vec5))._printMatrix();
        System.out.println();
        System.out.println("(4)=");
        new Matrix(vec2)._multiplyMatrix(new Matrix(vec6))._printMatrix();
        System.out.println();
        System.out.println("(5)=");
        new Matrix(vec7)._multiplyMatrix(new Matrix(vec8))._printMatrix();
    }

}