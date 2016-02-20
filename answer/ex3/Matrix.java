package pr2Calc;

import java.util.Scanner;

/**
 * 3-3�y��3-4���������܂���
 */
public class Matrix {
    //��ڂ��c�C��ڂ����C�G�N�Z���݂�����
    protected double[][] m_;

    public Matrix(double[][] input) {
        // �񎟌��z�� input �̓��e�ŁA�t�B�[���h�ϐ� m_[][]�̓��e������������
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
        // �w�肵���͈͂����݂��Ȃ��ꍇ
        if (row > _getNumOfRow() || column > _getNumOfColumn() || row < 0 || column < 0) {
            System.out.println("�w�肷��v�f�͑��݂��܂���.");
            System.exit(0);
        }
        return m_[row][column];
    }

    // �s��̓��e��\�����郁�\�b�h�i_print�ȍ~�ɓK���Ȗ��O������j
    // �K�v�ɉ����āA�����ň����̈قȂ郁�\�b�h�𕡐��쐬���Ă��ǂ�
//    protected void _print����(){
//        // �s����e�̕\����������������
//    }

    /**
     * �s���\�����܂��B
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

    // �s�񓯎m�A�������͍s��Ɨ�x�N�g���Ƃ̐ς��v�Z����
    //matrix�Ō��₷���Ȃ邩��
    protected Matrix _multiplyMatrix(Matrix target) {
        double[][] retValue = new double[this._getNumOfRow()][target._getNumOfColumn()];

        // �|������s��̗񐔂Ɗ|����s��̍s�����������Ȃ�
        if (this._getNumOfColumn() == target._getNumOfRow()) {
            // �ς̌v�Z��������������
            for (int i = 0; i < this._getNumOfRow(); i++) {
                for (int j = 0; j < target._getNumOfColumn(); j++) {
                    for (int k = 0; k < this._getNumOfColumn(); k++) {
                        retValue[i][j] += this._getComponentOf(i, k) * target._getComponentOf(k, j);
                    }
                }
            }

        } else {
            //�ۑ�̂܂܂���2�Ŏ~�܂�
            System.out.println("�v�f�����v�Z�ł���g�ݍ��킹�ƂȂ��Ă��܂���");
            System.out.println("�G���[�ł������s���܂����H y/n");
            if (new Scanner(System.in).next().indexOf("y") == -1) System.exit(0);
            retValue = new double[0][0];

        }
        return new Matrix(retValue);
    }

        /* ��̃��\�b�h�̑���Ɂi�������́A��̃��\�b�h�ɉ����āj�߂�l�ƈ�����
         * �o���� Matrix �N���X�Ƃ��� _multiplyMatrix()���쐬���Ă��ǂ�
         * �ǂ��炪�g���₷�����͊e���Ŕ��f���ė~����
         */

//    /**
//     * ��̃x�N�g���̓��ς�߂�l�Ƃ��郁�\�b�h
//     * @param matrix  �x�N�g���̓������s��
//     * @return  �|���Z����
//     */
//    protected double _getInnerProduct(Matrix matrix) {
//        double result = 0.0;
//        if (this._getNumOfRow() == 1 && matrix._getNumOfRow() == 1) {
//            for (int i = 0; i < this._getNumOfColumn(); i++)
//                result += this._getComponentOf(0, i) * matrix._getComponentOf(0, i);
//            return result;
//        } else if (this._getNumOfRow() == 1 && matrix._getNumOfColumn() == 1) {
//            //_multiplyMatrix()�ɓ�����Ɠ���Matrix�̊|���Z�����Ă����
//            return _multiplyMatrix(matrix)._getComponentOf(0, 0);
//        }
//        System.out.println("�s�x�N�g�����mor�s�x�N�g���Ɨ�x�N�g���ȊO�̓��ς��v�Z���悤�Ƃ��Ă��܂�");
//        System.exit(0);
//        return 0.0;
//    }


    public static void main(String[] args) {
/*
 * main ���\�b�h���ō���쐬�������όv�Z���\�b�h��s�񓯎m�A�x�N�g���ƍs��A
 * �s��ƃx�N�g���̐ς��v�Z���郁�\�b�h������ɓ����Ă��邩���m�F����B
 *
 */

// �s��E�x�N�g����`�E�����̈�� �i�����܂ň��@�ۑ�̗v���𖞂����悤�A
// �ȍ~�̕����͎��R�ɋL�q���C�����č\��Ȃ��j
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

        //�ۑ�3-1
        System.out.println("�ۑ�3-1");
        mat0 = new Matrix(A);
        mat0._printMatrix();
        System.out.println();

        //�ۑ�3-2
        /*
         * (1)x=(2,3,7), y=8(-1,-2, 2)�̓���
         *
         * (2)
         * ( 1  2  3)       ( 5  3  1)
         * ( 3  2 -1)   �~  ( 3 -3  2)
         * ( 4  2  6)
         *
         * (3)
         * ( 3)
         * (-2)    �~( 3  7 -5  2)
         *
         * (4)
         * ( 1  2  3)       ( 8  2)
         * ( 3  2 -1)   �~  (-3  2)
         * ( 4  2  6)       ( 1  6)
         *
         * (5)
         * ( 2 -3)     (-5 -3  1)
         * ( 4  2)  �~ (-3  3  2)
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

        System.out.println("�ۑ�3-2~3-4");
        System.out.print("(1)=");
        //3-4���s�����߂ɉ���
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