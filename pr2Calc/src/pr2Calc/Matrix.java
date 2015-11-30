package pr2Calc;

import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;


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


    protected MyVector _rotate(double degree) {
        double radian = _toRadianValue(degree);
        double[][] sinos = new double[][]{{Math.cos(radian), -Math.sin(radian)}, {Math.sin(radian), Math.cos(radian)}};
        double[] result = new double[2];
        if (_getNumOfRow() == 1 && _getNumOfColumn() == 2) {
            this._transpose();
        } else if (_getNumOfColumn() > 1 || _getNumOfRow() > 2) {
            System.out.println("�^����x�N�g�����s���ł�");
            System.exit(1);
        }
        result[0] = sinos[0][0] * _getComponentOf(0, 0) + sinos[0][1] * _getComponentOf(1, 0);
        result[1] = sinos[1][0] * _getComponentOf(0, 0) + sinos[1][1] * _getComponentOf(1, 0);

        return new MyVector(result, false);
    }

    /**
     * deg�̊p�x�������radian�ɕϊ�����ĕԂ��Ă����
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
     * �s�� A �̓]�u�s��̊e�v�f��z��̌`���Ŗ߂����\�b�h
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
        //4�]�u
        System.out.println("�]�u�O�̍s��");
        new Matrix(vec2)._printMatrix();
        System.out.println("�]�u��̍s��");
        new Matrix(new Matrix(vec2)._transpose())._printMatrix();
        System.out.println("���W�_(-3.0, 3.0) �� 45����]������");
        new MyVector(new double[]{-3.0, 3.0}, false)._rotate(45)._printVector();
        System.out.println("���W�_(2.0, -3.464) �� 60����]������");
        new MyVector(new double[]{2.0, -3.464}, false)._rotate(60)._printVector();
    }

}