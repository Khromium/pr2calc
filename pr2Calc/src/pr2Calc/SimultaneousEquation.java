package pr2Calc;

/**
 * Created by khrom on 15/11/16.
 */
public class SimultaneousEquation extends Matrix {
    private double[] answers_;

    public SimultaneousEquation(double[][] input) {
        super(input);
    }


    protected void _normalize(int targetRow) {
        final double normal = _getComponentOf(targetRow, targetRow);
        for (int i = targetRow; i < _getNumOfColumn(); i++) {
            m_[targetRow][i] /= normal;
        }
    }

    protected void _subtractRowFrom(int targetRow, int toRow) {
        double divide = _getComponentOf(toRow, targetRow) / _getComponentOf(targetRow, targetRow);
        for (int i = 0; i < _getNumOfColumn(); i++) {
            m_[toRow][i] -= divide * _getComponentOf(targetRow, i);
        }
    }

    /**
     * ガウスジョルダンの消去法を使って計算するメソッド
     */
    public void _solveByGaussJordan() {
        answers_ = new double[_getNumOfRow()];
        _printMatrix();
        for (int i = 0; i < _getNumOfRow(); i++) {
            System.out.println((i + 1) + "行" + (i + 1) + "列目が1となるように割り、他の行の" + (i + 1) + "列目が0となるように引く");
            _normalize(i);
            for (int j = 0; j < _getNumOfRow(); j++) {
                if (i != j) _subtractRowFrom(i, j);
            }
            _printMatrix();
        }

        for (int i = 0; i < _getNumOfRow(); i++) {
            answers_[i] = _getComponentOf(i, _getNumOfColumn() - 1);
        }
        System.out.println("Answer:");
        for (int i = 0; i < _getNumOfRow(); i++) {
            if (i != 0) System.out.print(", ");
            System.out.print("x" + (i + 1) + "=" + String.format("%1$4.1f", answers_[i]));

        }

    }

    public static void main(String[] args) {
        double[][] func = {
                {2, 1, 3, 4, 2},
                {3, 2, 5, 2, 12},
                {3, 4, 1, -1, 4},
                {-1, -3, 1, 3, -1}};
        SimultaneousEquation simultaneousEquation = new SimultaneousEquation(func);
        simultaneousEquation._solveByGaussJordan();
    }
}
