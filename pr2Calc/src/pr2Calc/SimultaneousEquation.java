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
        _printAnswer();
    }


    public void _solveByGauss() {
        answers_ = new double[_getNumOfRow()];
        _printMatrix();

        //前進
        for (int i = 0; i < _getNumOfRow() - 1; i++) {
            for (int j = i + 1; j < _getNumOfRow(); j++) {
                _subtractRowFrom(i, j);
            }
            _printMatrix();
            System.out.println();
        }

        //後退
        for (int i = _getNumOfRow() - 1; i >= 0; i--) {
            double anst = _getComponentOf(i, _getNumOfColumn() - 1);
            for (int j = i + 1; j < _getNumOfRow(); j++) {
                anst -= _getComponentOf(i, j) * answers_[j];
            }
            answers_[i] = anst / _getComponentOf(i, i);
        }
        _printAnswer();

    }

    //public:protectedのミス？
    protected void _solveByGaussWithPartialSelection() {
        answers_ = new double[_getNumOfRow()];
        _printMatrix();
        System.out.println();

        //前進
        for (int i = 0; i < _getNumOfRow() - 1; i++) {
            int maxRow = _selectPivotFromRow(i, i);
            _exchangeRows(i, maxRow);
            for (int j = i + 1; j < _getNumOfRow(); j++) {
                _subtractRowFrom(i, j);
            }
            _printMatrix();
            System.out.println();
        }

        //後退
        for (int i = _getNumOfRow() - 1; i >= 0; i--) {
            double anst = _getComponentOf(i, _getNumOfColumn() - 1);
            for (int j = i + 1; j < _getNumOfRow(); j++) {
                anst -= _getComponentOf(i, j) * answers_[j];
            }
            answers_[i] = anst / _getComponentOf(i, i);
        }
        _printAnswer();
    }

    protected void _exchangeRows(int row1, int row2) {
        for (int i = 0; i < _getNumOfColumn(); i++) {
            double tmp = _getComponentOf(row1, i);
            _setComponentOf(row1, i, _getComponentOf(row2, i));
            _setComponentOf(row2, i, tmp);
        }
    }

    protected int _selectPivotFromRow(int rowfrom, int column) /*throws invalidMatrixException*/ {
//        if (rowfrom < _getNumOfRow()) {
//            System.out.println("値が不正です");
//            System.exit(1);
////            new invalidMatrixException("値が不正です");
//        }
        int resultIndex = rowfrom;
        double maxvalue = Math.abs(_getComponentOf(rowfrom, column));
        for (int i = rowfrom + 1; i < _getNumOfRow(); i++) {
            double tar = Math.abs(_getComponentOf(i, column));
            if (maxvalue < tar) {
                maxvalue = tar;
                resultIndex = i;
            }
        }
        return resultIndex;
    }


    private void _printAnswer() {
        System.out.println("Answer:");
        for (int i = 0; i < _getNumOfRow(); i++) {
            if (i != 0) System.out.print(", ");
            System.out.print("x" + (i + 1) + "=" + String.format("%1$4.1f", answers_[i]));

        }
    }

    public static void main(String[] args) {
        double[][] func = {
                {1, 2, 3, 4, 22},
                {-3, 3, -2, 2, -14},
                {3, -5, 1, 1, 23},
                {6, -2, 4, -8, 8}};


        SimultaneousEquation simultaneousEquation = new SimultaneousEquation(func);
        simultaneousEquation._solveByGaussWithPartialSelection();
    }

//    class invalidMatrixException extends Exception {
//        public invalidMatrixException(String args) {
//            super(args);
//        }
//    }

}
