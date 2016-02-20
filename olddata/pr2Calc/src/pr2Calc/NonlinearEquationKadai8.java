package pr2Calc;


import java.applet.Applet;
import java.awt.*;
import java.util.function.Function;

public class NonlinearEquationKadai8 extends Applet{

    public static final double EPSILON = 0.001;//終了条件
    public static final int MAXIMUM_IT = 100;//繰り返し回数の最大値
    private static final double NEGATIVE_MAX = 0.0;
    private static final double POSITIVE_MAX = 5.0;
    private double initialValue_;//初期反復解x_0を格納
    private double alfa_ = 5.0;//固定
    private double answer_;//解を格納
    private int iteration_;//現在の反復回数を格納
    private double past_[]= new double[MAXIMUM_IT+1];;


    public void init(){
        initialValue_=4.0;
    }

    // コンストラクタ(最低一つ用意せよ）
//    public NonlinearEquationKadai8(double initialValue/*, double alfa*/) {
////        // 処理を実装せよ
//        initialValue_ = initialValue;
////        alfa_ = alfa;
//
//    }



    private boolean _solveNLEByLinearIteration() {
        double value;      // x_k に対応
        int count = 1;//最低一回は行う
        value = initialValue_;
        System.out.println("value = " + String.format("%1$4.15f ", Math.sqrt(10 + alfa_ + value)) + " , pastValue = " + value);
        past_[0]=value;
        while (Math.abs(Math.sqrt(10 + alfa_ + value) - value) >= EPSILON) {
            if (count > MAXIMUM_IT) return false;
            value = Math.sqrt(10 + alfa_ + value);
            System.out.print("value = " + String.format("%1$4.15f ", Math.sqrt(10 + alfa_ + value)));
            System.out.println(" , pastValue = " + String.format("%1$4.15f ", value));
            past_[count]=value;
            count++;
        }
        answer_ = Math.sqrt(10 + alfa_ + value);
        iteration_ = count;
        System.out.println("X = " + answer_ + " at interation " + iteration_ + ".");
        return true;
    }

    public void paint(Graphics g){
        int xPoints[], yPoints[],
                min = 0, max = 360, a = 10;

        _solveNLEByLinearIteration();

        int size = Math.abs(max-min);
        xPoints = new int[size];
        yPoints = new int[size];
        try{
            g.setColor(Color.black);
            g.drawLine(max, size/2, min, size/2);
            g.drawLine(size/2, max, size/2, min);
            g.drawString("0", size/2-10, size/2-5);
            g.drawString("x", size, size/2-5);
            g.drawString("y", size/2+5, 10);

            g.setColor(Color.blue);
            for(int i = 0; i < size; i++){
                xPoints[i] = min+i;
                yPoints[i] = size-xPoints[i];
            }
            g.drawPolyline(xPoints, yPoints, size);

            g.setColor(Color.green);
            int start = size/2-a*(int)(10.0+alfa_);
            int tmp = 0;
            for(int i = 0; i < size; i++){
                xPoints[i] = start+i;
                yPoints[i] = size/2-(int)(3.5*Math.sqrt(i));
            }
            g.drawPolyline(xPoints, yPoints, size);

            g.setColor(Color.red);
            int x = size/2+(int)(a*past_[0]),
                    y =size/2-(int)(3.5*Math.sqrt(x-size/2+a*(10.0+alfa_))),
                    y_p = size/2;
            for(int i = 0; i < iteration_; i++){
                g.drawLine(x, y_p, x, y);
                g.drawLine(x, y, size-y, y);
                x = size-y;
                y_p = y;
                y = size/2-(int)(3.5*Math.sqrt(x-size/2+a*(10.0+alfa_)));
            }
        }
        catch(SecurityException e){
            g.drawString("Signing didn't work. The SecurityException is: " + e.getMessage(), 10, 70);
        }
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

    private boolean _solveNLEByRegulaFalsi() {
        double value = 0.0;
        double xMid = 0.0, pastMid = 0.0;
        double x0 = NEGATIVE_MAX, x1 = POSITIVE_MAX;
        iteration_ = 0;

        Function<Double, Double> func = (x) -> (x + alfa_ == 0) ? 1.0 : (Math.sin(x + alfa_) / (x + alfa_));
        while (true) {
            if (++iteration_ == MAXIMUM_IT) break;  //上限に達した場合
            xMid = (x0 * func.apply(x1) - x1 * func.apply(x0)) / (func.apply(x1) - func.apply(x0));
            value = func.apply(xMid);//適用
            System.out.println("xNext = " + xMid + ", f(xNext) = " + value + ", xPastNext = " + pastMid);

            if (Math.abs(xMid - pastMid) < EPSILON || value == 0) {
                answer_ = xMid;
                System.out.printf("x = " + answer_ + " at iteration " + (iteration_ - 1) + ".");
                return true;
            }
            pastMid = xMid;
            if ((value < 0)) { //符号違いだったら
                x1 = xMid;
            } else {
                x0 = xMid;
            }
        }
        return false;
    }


    private boolean _solveNLEByNewton() {
        double dx = 0.00000000001;//微小な値を設定する。
        Function<Double, Double> func = x -> Math.exp(x) - alfa_ * x;
        Function<Double, Double> funcp = x -> (func.apply(x + dx) - func.apply(x)) / dx; //微分した値
        double next, line, value;
        iteration_ = 0;
        next = initialValue_;
        while (true) {
            if (++iteration_ == MAXIMUM_IT) break;  //上限に達した場合

            line = funcp.apply(next);
            value = next - func.apply(next) / line;
            System.out.println("xNext = " + value + ", f(xNext) = " + func.apply(value));

            //収束条件
            if (Math.abs(value - next) < EPSILON) {
                answer_ = value;
                System.out.println("x = " + answer_ + " at iteration " + (iteration_ - 1) + ".");
                return true;
            } else {
                next = value;
            }
        }
        return false;
    }

//
//    public static void main(String[] args) {
//        NonlinearEquationKadai8 eqn = new NonlinearEquationKadai8(1.19);//課題で述べられていた初期値
////        eqn.initialValue_ = 4.0;
////        eqn._alfa = 5.0;
////        if (!eqn._solveNLEByLinearIteration()) {
////            System.out.println("解が見つからなかった");
////        }
////        NonlinearEquation eqn = new NonlinearEquation(3, 0);
////        if (!eqn._solveNLEByRegulaFalsi()) {
////            System.out.println("解が見つからなかった");
////        }
//        if (!eqn._solveNLEByNewton()) {
//            System.out.println("解が見つかりませんでした");
//        }
//
//    }
}