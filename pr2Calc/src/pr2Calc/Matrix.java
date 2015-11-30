package pr2Calc;

import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;


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


    protected MyVector _rotate(double degree) {
        double radian = _toRadianValue(degree);
        double[][] sinos = new double[][]{{Math.cos(radian), -Math.sin(radian)}, {Math.sin(radian), Math.cos(radian)}};
        double[] result = new double[2];
        if (_getNumOfRow() == 1 && _getNumOfColumn() == 2) {
            this._transpose();
        } else if (_getNumOfColumn() > 1 || _getNumOfRow() > 2) {
            System.out.println("与えるベクトルが不正です");
            System.exit(1);
        }
        result[0] = sinos[0][0] * _getComponentOf(0, 0) + sinos[0][1] * _getComponentOf(1, 0);
        result[1] = sinos[1][0] * _getComponentOf(0, 0) + sinos[1][1] * _getComponentOf(1, 0);

        return new MyVector(result, false);
    }

    /**
     * degの角度を入れるとradianに変換されて返ってくるよ
     *
     * @param theta degree
     * @return radian
     */
    protected double _toRadianValue(double theta) {
        if (theta > 360 || theta < -360) {
            return (theta % 360) / 180 * Math.PI;
        }
        return theta / 180 * Math.PI;
    }

    /**
     * 行列 A の転置行列の各要素を配列の形式で戻すメソッド
     *
     * @return
     */
    protected double[][] _transpose() {
        double[][] trans = new double[_getNumOfColumn()][_getNumOfRow()];
        for (int i = 0; i < _getNumOfRow(); i++) {
            for (int j = 0; j < _getNumOfColumn(); j++) {
                trans[j][i] = _getComponentOf(i, j);
            }
        }
        return trans;
    }

    public static void main(String[] args) {

        double[][] vec2 = {{1.0, 2.0, 3.0}, {3.0, 2.0, -1.0}, {4.0, 2.0, 6.0}};
        //4転置
        System.out.println("転置前の行列");
        new Matrix(vec2)._printMatrix();
        System.out.println("転置後の行列");
        new Matrix(new Matrix(vec2)._transpose())._printMatrix();
        System.out.println("座標点(-3.0, 3.0) を 45°回転させる");
        new MyVector(new double[]{-3.0, 3.0}, false)._rotate(45)._printVector();
        System.out.println("座標点(2.0, -3.464) を 60°回転させる");
        new MyVector(new double[]{2.0, -3.464}, false)._rotate(60)._printVector();
    }

}