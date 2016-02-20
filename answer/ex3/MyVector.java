package pr2Calc;

/**
 * Matrix�p������Α��̃��\�b�h���g����Ǝv����
 */
public class MyVector extends Matrix {
    //true:row�s�@false:column��
    protected boolean mode;

    /**
     * @param input �s�񂭂�����
     * @param mode  true:row�s�@false:column��
     */
    public MyVector(double[] input, boolean mode) {
        super(MyVector._convertArrayToMatrix(input, mode));
        this.mode = mode;
    }


    /**
     * �z���Matrix�̌`�ɕ����Bconstructor����Ă΂��
     *
     * @param input ���̓f�[�^
     * @param mode  true:row�s�@false:column��
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
     * override����vector�ɑΉ������\��������
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
     * ��̃x�N�g���̓��ς�߂�l�Ƃ��郁�\�b�h
     *
     * @param matrix �x�N�g���̓������s��
     * @return �|���Z����
     */
    protected double _getInnerProduct(MyVector matrix) {
        double result = 0.0;
        if (this._getNumOfRow() == 1 && matrix._getNumOfRow() == 1) {
            for (int i = 0; i < this._getNumOfColumn(); i++)
                result += this._getComponentOf(0, i) * matrix._getComponentOf(0, i);
            return result;
        } else if (this._getNumOfRow() == 1 && matrix._getNumOfColumn() == 1) {
            //_multiplyMatrix()�ɓ�����Ɠ���Matrix�̊|���Z�����Ă����
            return _multiplyMatrix(matrix)._getComponentOf(0, 0);
        }
        System.out.println("�s�x�N�g�����mor�s�x�N�g���Ɨ�x�N�g���ȊO�̓��ς��v�Z���悤�Ƃ��Ă��܂�");
        System.exit(0);
        return 0.0;
    }


    /**
     * 3-3�̉ۑ�p
     */
    protected void _printVector() {
        this._printMatrix();
    }


}