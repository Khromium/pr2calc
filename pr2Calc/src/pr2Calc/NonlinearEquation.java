package pr2Calc;



import java.util.function.Function;

public class NonlinearEquation {

    public static final double EPSILON = 0.001;//終了条件
    public static final int MAXIMUM_IT = 100;//繰り返し回数の最大値
    private static final double NEGATIVE_MAX = 0.0;
    private static final double POSITIVE_MAX = 3.0;
    private double initialValue_;//初期反復解x_0を格納
    private double alfa_ = 3.0;//固定
    private double answer_;//解を格納
    private int iteration_;//現在の反復回数を格納


    // コンストラクタ(最低一つ用意せよ）
    public NonlinearEquation(double initialValue, double alfa) {
        // 処理を実装せよ
        initialValue_ = initialValue;
        alfa_ = alfa;

    }

    //fx=sin(x+alfa)/(x+a)
    //初期反復解は1.5
    private boolean _solveNLEByBlsectlonMethod() {
        double value;
        double xMid = 0.0, pastMid = 0.0;
        double x0 = NEGATIVE_MAX, x1 = POSITIVE_MAX;
        iteration_ = 0;

        Function<Double, Double> func = (x) -> (Math.sin(x + alfa_) / (x + alfa_)); //ラムダ式
        while (true) {
            if (++iteration_ == MAXIMUM_IT) break;  //上限に達した場合
            xMid = (x1 + x0) / 2;
            value = func.apply(xMid);//適用
            System.out.println("xMid = " + xMid + ", f(xMid) = " + value + ", xPastMid = " + pastMid);

            if (Math.abs(xMid - pastMid) < EPSILON || value == 0) {
                answer_ = xMid;
                System.out.printf("x = " + answer_ + " at iteration " + (iteration_ - 1) + ".");
                return true;
            }
            pastMid = xMid;
            if ((value < 0)/* ^ (func.apply(x0) < 0)*/) { //符号違いだったら
                x1 = xMid;
            } else {
                x0 = xMid;
            }
        }
        return false;

    }


    private boolean _solveNLEByLinearIteration() {
        double value;      // x_k に対応
        int count = 1;//最低一回は行う
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
//            System.out.println("解が見つからなかった");
//        }
        NonlinearEquation eqn = new NonlinearEquation(3, 0);
        if (!eqn._solveNLEByBlsectlonMethod()) {
            System.out.println("解が見つからなかった");
        }
    }
}