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

    protected void _solveByGaussWithCompleteSelection() {
        answers_ = new double[_getNumOfRow()];
        _printMatrix();
        System.out.println();

        int[] oIndex = new int[_getNumOfColumn() - 1];
        for (int i = 0; i < _getNumOfColumn() - 1; i++) oIndex[i] = i;


        //前進
        for (int i = 0; i < _getNumOfRow() - 1; i++) {
            int[] maxRow = _selectPivotFromRowColumn(i, i);
            _exchangeRowsAndColumns(i, i, maxRow[0], maxRow[1]);

            int tmpd = oIndex[i];
            oIndex[i] = oIndex[maxRow[1]];
            oIndex[maxRow[1]] = tmpd;

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
                anst -= _getComponentOf(i, j) * answers_[oIndex[j]];
            }
            answers_[oIndex[i]] = anst / _getComponentOf(i, i);
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

    protected void _exchangeColumns(int col1, int col2) {
        for (int i = 0; i < _getNumOfRow(); i++) {
            double tmp = _getComponentOf(i, col1);
            _setComponentOf(i, col1, _getComponentOf(i, col2));
            _setComponentOf(i, col2, tmp);
        }
    }

    protected void _exchangeRowsAndColumns(int row1, int col1, int row2, int col2) {
        _exchangeRows(row1, row2);
        _exchangeColumns(col1, col2);
    }


    protected int _selectPivotFromRow(int rowfrom, int column) {
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

    protected int _selectPivotFromRow(final int column) {
        return _selectPivotFromRow(column, 0);
    }

    protected int[] _selectPivotFromRowColumn(int rowFrom, int colFrom) {
        int[] maxIndex = new int[2];
        maxIndex[0] = rowFrom;
        maxIndex[1] = colFrom;
        //初期値
        double maxValue = Math.abs(_getComponentOf(rowFrom, colFrom));
        for (int i = rowFrom; i < _getNumOfRow(); i++) {
            for (int j = colFrom; j < _getNumOfColumn() - 1; j++) {
                double tar = Math.abs(_getComponentOf(i, j));
                if (maxValue < tar) {
                    maxValue = tar;
                    maxIndex[0] = i;
                    maxIndex[1] = j;
                }
            }
        }
        return maxIndex;
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
        System.out.println("部分選択法");
        simultaneousEquation._solveByGaussWithPartialSelection();
        System.out.println("\n完全選択法");
        simultaneousEquation = new SimultaneousEquation(func);
        simultaneousEquation._solveByGaussWithCompleteSelection();
    }


}
