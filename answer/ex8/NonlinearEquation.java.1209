package pr2Calc;

/**
 * Created by khrom on 15/12/09.
 */
public class NonlinearEquation {

    public static final double EPSILON = 0.001;//終了条件
    public static final int MAXIMUM_IT = 100;//繰り返し回数の最大値
    private double initialValue_;//初期反復解x_0を格納
    private double _alfa;
    private double answer_;//解を格納
    private int iteration_;//現在の反復回数を格納


    // コンストラクタ(最低一つ用意せよ）　なくてもいいらしい
//    public NonlinearEquation(引数は各自で決定) {
//        // 処理を実装せよ
//    }

    private boolean _solveNLEByLinearIteration() {
        double value;      // x_k に対応
        int count = 1;//最低一回は行う
        // 初期反復解をvalueに設定し
        value = initialValue_;
        System.out.println("value = " + String.format("%1$4.15f ", Math.sqrt(10 + _alfa + value)) + " , pastValue = " + value);
        while (Math.abs(Math.sqrt(10 + _alfa + value) - value) >= EPSILON) {
            if (count > MAXIMUM_IT) return false;
            value = Math.sqrt(10 + _alfa + value);
            System.out.print("value = " + String.format("%1$4.15f ", Math.sqrt(10 + _alfa + value)));
            System.out.println(" , pastValue = " + String.format("%1$4.15f ", value));
            count++;
        }
        System.out.println("X = " + Math.sqrt(10 + _alfa + value) + " at interation " + count + ".");
        return true;
        // |value - pastValue| が EPSILON 未満となる(近似解が見つかる)、もしくは
        // 繰り返し回数がMAXIMUM_IT 回に到達するまで繰り返し
        // 繰り返しで得られる反復解の途中経過を表示するようにすること
    }


    public static void main(String[] args) {
        NonlinearEquation eqn = new NonlinearEquation();
//        eqn.initialValue_ = 10.0; //HPの値を出すための設定値
//        eqn._alfa= 15 //HPの値を出すための設定値
        eqn.initialValue_ = 4.0;
        eqn._alfa = 5.0;
        if (!eqn._solveNLEByLinearIteration()) {
            System.out.println("解が見つからなかった");
        }
        // 初期反復解を設定
        // 近似解が得られたら近似解＆何回目の繰り返しで解が得られたかを表示
        // 得られなければ、解が見つからなかったことを表示
    }
}