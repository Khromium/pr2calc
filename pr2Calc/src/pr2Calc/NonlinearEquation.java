package pr2Calc;



import java.util.function.Function;

public class NonlinearEquation {

    public static final double EPSILON = 0.001;//�I������
    public static final int MAXIMUM_IT = 100;//�J��Ԃ��񐔂̍ő�l
    private static final double NEGATIVE_MAX = 0.0;
    private static final double POSITIVE_MAX = 3.0;
    private double initialValue_;//����������x_0���i�[
    private double alfa_ = 3.0;//�Œ�
    private double answer_;//�����i�[
    private int iteration_;//���݂̔����񐔂��i�[


    // �R���X�g���N�^(�Œ��p�ӂ���j
    public NonlinearEquation(double initialValue, double alfa) {
        // ��������������
        initialValue_ = initialValue;
        alfa_ = alfa;

    }

    //fx=sin(x+alfa)/(x+a)
    //������������1.5
    private boolean _solveNLEByBlsectlonMethod() {
        double value;
        double xMid = 0.0, pastMid = 0.0;
        double x0 = NEGATIVE_MAX, x1 = POSITIVE_MAX;
        iteration_ = 0;

        Function<Double, Double> func = (x) -> (Math.sin(x + alfa_) / (x + alfa_)); //�����_��
        while (true) {
            if (++iteration_ == MAXIMUM_IT) break;  //����ɒB�����ꍇ
            xMid = (x1 + x0) / 2;
            value = func.apply(xMid);//�K�p
            System.out.println("xMid = " + xMid + ", f(xMid) = " + value + ", xPastMid = " + pastMid);

            if (Math.abs(xMid - pastMid) < EPSILON || value == 0) {
                answer_ = xMid;
                System.out.printf("x = " + answer_ + " at iteration " + (iteration_ - 1) + ".");
                return true;
            }
            pastMid = xMid;
            if ((value < 0)/* ^ (func.apply(x0) < 0)*/) { //�����Ⴂ��������
                x1 = xMid;
            } else {
                x0 = xMid;
            }
        }
        return false;

    }


    private boolean _solveNLEByLinearIteration() {
        double value;      // x_k �ɑΉ�
        int count = 1;//�Œ���͍s��
        value = initialValue_;
        System.out.println("value = " + String.format("%1$4.15f ", Math.sqrt(10 + alfa_ + value)) + " , pastValue = " + value);
        while (Math.abs(Math.sqrt(10 + alfa_ + value) - value) >= EPSILON) {
            if (count > MAXIMUM_IT) return false;
            value = Math.sqrt(10 + alfa_ + value);
            System.out.print("value = " + String.format("%1$4.15f ", Math.sqrt(10 + alfa_ + value)));
            System.out.println(" , pastValue = " + String.format("%1$4.15f ", value));
            count++;
        }
        answer_ = Math.sqrt(10 + alfa_ + value);
        iteration_ = count;
        System.out.println("X = " + answer_ + " at interation " + iteration_ + ".");
        return true;
    }


    public static void main(String[] args) {
//        NonlinearEquation eqn = new NonlinearEquation();
//        eqn.initialValue_ = 4.0;
//        eqn._alfa = 5.0;
//        if (!eqn._solveNLEByLinearIteration()) {
//            System.out.println("����������Ȃ�����");
//        }
        NonlinearEquation eqn = new NonlinearEquation(3, 0);
        if (!eqn._solveNLEByBlsectlonMethod()) {
            System.out.println("����������Ȃ�����");
        }
    }
}