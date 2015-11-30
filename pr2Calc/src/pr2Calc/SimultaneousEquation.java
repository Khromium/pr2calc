package pr2Calc;

/**
 * Created by khrom on 15/11/16.
 */
public class SimultaneousEquation extends Matrix {
    private double[] answers_;

    public SimultaneousEquation(double[][] input) {
        super(input);
    }

    public double[] getAnswers_() {
        return answers_;
    }

    protected void _normalize(int targetRow) {
        for (int i = targetRow; i < _getNumOfColumn(); i++) {
            m_[targetRow][i] = _getComponentOf(targetRow, i) / _getComponentOf(targetRow, targetRow);
        }
    }

    protected void _subtractRowFrom(int targetRow, int toRow) {
        for (int i = 0; i < _getNumOfColumn(); i++) {
            m_[toRow][i] = _getComponentOf(toRow, i) - _getComponentOf(targetRow, i) * _getComponentOf(toRow, i) / _getComponentOf(targetRow, i);
        }
    }


    public static void main(String[] args) {
        double[][] func = {{1, 2, 3, 4, 22}, {-3, 3, -2, 2, -14}, {6, -2, 4, -8, 8}, {3, -5, 1, 1, 23}};
        SimultaneousEquation simultaneousEquation = new SimultaneousEquation(func);
        simultaneousEquation._printMatrix();
        for (int i = 0; i < 4; i++) {
            System.out.println((i + 1) + "s1—ñ–Ú‚ª1‚Æ‚È‚é‚æ‚¤‚ÉŠ„‚èA‘¼‚Ìs‚Ì" + (i + 1) + "—ñ–Ú‚ª0‚Æ‚È‚é‚æ‚¤‚Éˆø‚­");
            simultaneousEquation._normalize(i);
            for (int j = i + 1; j < simultaneousEquation._getNumOfColumn() - 1; j++) {
                simultaneousEquation._subtractRowFrom(i, j);
            }
            simultaneousEquation._printMatrix();
        }

    }
}
