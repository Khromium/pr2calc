package pr2Calc;

/**
 * Matrix継承すれば他のメソッドも使えると思った
 */
public class MyVector extends Matrix {
    //true:row行　false:column列
    protected boolean mode;

    /**
     * @param input 行列ください
     * @param mode  true:row行　false:column列
     */
    public MyVector(double[] input, boolean mode) {
        super(MyVector._convertArrayToMatrix(input, mode));
        this.mode = mode;
    }


    /**
     * 配列をMatrixの形に復元。constructorから呼ばれる
     *
     * @param input 入力データ
     * @param mode  true:row行　false:column列
     * @return Matrix
     */
    private static double[][] _convertArrayToMatrix(double[] input, boolean mode) {
        double[][] m;
        if (mode) {
            m = new double[1][input.length];
            for (int i = 0; i < input.length; i++) {
                m[0][i] = input[i];
            }
            return m;
        } else {
            m = new double[input.length][1];
            for (int i = 0; i < input.length; i++) {
                m[i][0] = input[i];
            }
            return m;
        }

    }


    /**
     * overrideしてvectorに対応した表示をする
     */
    @Override
    protected void _printMatrix() {

        if (mode) {
            System.out.print("(");
            for (int i = 0; i < _getNumOfRow(); i++) {
                System.out.print(String.format(" %1$4.1f ", _getComponentOf(0, i)));
            }
            System.out.print(")");
        } else {
            for (int i = 0; i < _getNumOfColumn(); i++) {
                System.out.print("(");
                System.out.print(String.format(" %1$4.1f ", _getComponentOf(i, 0)));
                System.out.print(")");
            }
        }

    }

    /**
     * 二つのベクトルの内積を戻り値とするメソッド
     *
     * @param matrix ベクトルの入った行列
     * @return 掛け算結果
     */
    protected double _getInnerProduct(MyVector matrix) {
        double result = 0.0;
        if (this._getNumOfRow() == 1 && matrix._getNumOfRow() == 1) {
            for (int i = 0; i < this._getNumOfColumn(); i++)
                result += this._getComponentOf(0, i) * matrix._getComponentOf(0, i);
            return result;
        } else if (this._getNumOfRow() == 1 && matrix._getNumOfColumn() == 1) {
            //_multiplyMatrix()に投げると同じMatrixの掛け算をしてくれる
            return _multiplyMatrix(matrix)._getComponentOf(0, 0);
        }
        System.out.println("行ベクトル同士or行ベクトルと列ベクトル以外の内積を計算しようとしています");
        System.exit(0);
        return 0.0;
    }


    /**
     * 3-3の課題用
     */
    protected void _printVector() {
        this._printMatrix();
    }


}