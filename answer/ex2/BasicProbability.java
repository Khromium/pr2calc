package pr2Calc;

import java.io.*;
import java.util.Scanner;

public class BasicProbability {
    protected int[][] rv_;
    protected int[] numOfStates_; // 入りうる値の数の配列
    protected int numOfVariables_; // 横の長さ
    protected int numOfData_; // データの配列の数　たての長さ
    protected boolean fileExists;

    /**
     * 生成したオブジェクト内の変数 (フィールド変数) を初期化、必要な値を代入するため のコンストラクタ
     *
     * @param inputData 入力データ
     */
    public BasicProbability(int[][] inputData) {

        // 配列から、変数の取り得る値が記述されている行を除いた行数=データ数
        numOfData_ = inputData.length - 1;
        numOfVariables_ = inputData[0].length;
        rv_ = new int[numOfData_][numOfVariables_];
        numOfStates_ = new int[numOfVariables_];

        for (int h = 0; h < numOfVariables_; h++) {
            // 各変数の取り得る値をnumOfStates_ に格納する
            numOfStates_[h] = inputData[0][h];
        }

        for (int h = 0; h < numOfData_; h++) {
            for (int i = 0; i < numOfVariables_; i++)
                rv_[h][i] = inputData[h + 1][i];
        }
    }


    /**
     * ファイル名を指定すると読み込んで確率を計算してくれるconstructor
     *
     * @param fileName
     */
    public BasicProbability(String fileName) {
        try {
            fileExists = true;
            this._getSampleData(fileName);
        } catch (IOException e) {
            System.out.println("ファイルからの入力に失敗しました。");
            fileExists = false;
        }
    }

    /**
     * constructorから呼び出され、ファイルを読み取ってくれるメソッド
     *
     * @param fileName 　ファイル名
     * @return 読み取れたかどうか
     * @throws IOException エラーが有った場合
     */
    private boolean _getSampleData(String fileName) throws IOException {
        int i;
        BufferedReader fin = new BufferedReader(new FileReader(fileName));
        String data;
        String[] value;

        // data に、ファイルから文字列を一行分読み込む
        data = fin.readLine();
        value = data.split("\\s");    // 得られた文字列データをspace(= \\s)で区切り配列へ格納
        // 最初のデータはデータ数と変数の数のふた組なので、配列要素数が異なる場合はfalseを返す
        if (value.length != 2)
            return false;
        else {
                        /* データ数と変数の数をフィールド変数numOfData_, numOfVariables_に格納 */
                        /* 次の行のデータを読み込む */
            numOfData_ = Integer.parseInt(value[0]);
            numOfVariables_ = Integer.parseInt(value[1]);
            data = fin.readLine();
            value = data.split("\\s");
            if (value.length != numOfVariables_)
                return false;
            else {
                rv_ = new int[numOfData_][numOfVariables_];
                numOfStates_ = new int[numOfVariables_];
                // 各変数の取り得る値の数をフィールド変数numOfStates_[]に格納
                for (int j = 0; j < numOfVariables_; j++) {
                    numOfStates_[j] = Integer.parseInt(value[j]);
                }
            }
        }

        i = 0;  // 加えるサンプルデータをカウントする変数を初期化
        while (true) {
            // data に、ファイルから文字列を一行分読み込む
            data = fin.readLine();
            if (data == null)
                break;
            value = data.split("\\s");
            // i番目のデータをrv_[i][]に格納する
            for (int j = 0; j < numOfVariables_; j++) {
                rv_[i][j] = Integer.parseInt(value[j]);
            }
            i++;
        }

        return true;
    }

    /**
     * @param rvNum   値の場所
     * @param rvValue 値
     * @return 確率
     */
    public double getProbability(int rvNum, int rvValue) {
        // rvNum : 計算対象となる確率変数の番号, rvValue : rvNumで指定された確率変数の値
        // rvNum = 0, rvValue = 1 ならば P(X0 = 1)を計算することに対応する
        // →つまりXnの番号と　Xnの値
        // 機能を実現するよう記述を追加せよ
        int result = 0;
        for (int i = 0; i < numOfData_; i++) {
            if (rv_[i][rvNum] == rvValue)
                result++;
        }
        return (double) result / numOfData_;
    }

    /**
     * @param rvNum    |より左の値の場所
     * @param rvValue  |より左の値
     * @param cRvNum   |より右の値の場所
     * @param cRvValue 　|より右の値
     * @return 確率　(変数と値の設定がおかしい時には-1.0を返すこととする)
     */
    public double getConditionalProbability(int rvNum, int rvValue, int cRvNum, int cRvValue) {
        double hit = 0.0, // 条件部分を満たす中で、rvNum = rvValue となるデータ数をカウント
                total = 0.0; // 条件部分(cRvNum = cRvValue)を満たすデータ総数をカウント

        if (rvNum >= 0 && rvNum < numOfVariables_ && cRvNum >= 0
                && cRvNum < numOfVariables_) {
            if (rvValue >= 0 && rvValue < numOfStates_[rvNum] && cRvValue >= 0
                    && cRvValue < numOfStates_[cRvNum]) {
                for (int h = 0; h < numOfData_; h++) {
                    // total と hit の値を更新
                    if (rv_[h][cRvNum] == cRvValue) {
                        ++total;
                        if (rv_[h][rvNum] == rvValue) {
                            ++hit;
                        }
                    }

                }
                if (total != 0.0) {
                    return hit / total;
                } else {
                    System.out.println("Denominator = 0."); // 分母が0であることを明示
                    return -1.0;
                }
            }
        }
        return -1.0;
    }


    /**
     * @param rvA 値の場所
     * @param rvR 　値
     * @return
     */
    public double getJointProbability(int[] rvA, int[] rvR) {
        //存在する大きさ比較
        final int numAvailable = Math.min(rvA.length, rvR.length);

        double hit = 0.0;
        boolean isHit;
        for (int i = 0; i < numOfData_; i++) {
            isHit = true;
            for (int j = 0; j < numAvailable; j++) {
                if (rvR[j] != rv_[i][rvA[j]]) {
                    isHit = false;
                    break;
                }
            }
            if (isHit) ++hit;
        }

        return hit / numOfData_;
    }

    /**
     * n個のr.v.のうち1個以上n-k個以下の変数Xbc の値がxbc であるという条件の下で、1個 以上 k 個以下の変数 Xar の値が xar
     * である条件付き確率 P(Xa0 ,...,Xar |Xb0 ,...,Xbc ) を返す (r: P(・|・) のうち、“|”
     * より前で値の定まっている r.v. の数、c: P(・|・) の “|” より 後で値の定まっている r.v. の数）
     *
     * @param rvA  |より左側のrvの場所
     * @param rvR  |より左側のrvの値
     * @param crvA |より右側のrvの場所
     * @param crvR |より右側のrvの値
     * @return 計算された確率
     */
    public double getConditionalProbability(int[] rvA, int[] rvR, int[] crvA, int[] crvR) {
        /**
         * Hint: 条件付き確率は課題資料に記述されている定義にあるとおり、 「同時確率／同時確率」の形式となっているので、上の
         * getJointProbability ができれば簡単に計算できる。
         * ただし、上の簡単な方のgetConditionalProbabilityと同様、 分母部分が0となった場合の処理は必要なので注意
         */
        final double condProbability;
        if ((condProbability = getJointProbability(crvA, crvR)) == 0.0) {
            System.out.println("Conditional Probability is 0.");
            return -1.0;
        }
        final int numAvailableR = Math.min(rvA.length, rvR.length);
        final int numAvailableC = Math.min(crvA.length, crvR.length);
        final int numJoint = numAvailableR + numAvailableC;

        int[] joinNums = new int[numJoint];
        int[] joinValues = new int[numJoint];
        System.arraycopy(rvA, 0, joinNums, 0, numAvailableR);
        System.arraycopy(rvR, 0, joinValues, 0, numAvailableR);
        System.arraycopy(crvA, 0, joinNums, numAvailableR, numAvailableC);
        System.arraycopy(crvR, 0, joinValues, numAvailableR, numAvailableC);
        // 合体リストの確率 P(A,B)
        final double joinProbability = getJointProbability(joinNums, joinValues);

        // P(A|B) = P(A,B) / P(B)
        return joinProbability / condProbability;

    }


    /**
     * ファイル名を指定すると
     * データ数、確率変数の数、各変数の取り得る値の数、実際に変数が取った値
     * を保存します
     *
     * @param saveFileName 保存するファイル名
     */
    private void _saveSampleData(String saveFileName) {
        try {
            File file = new File(saveFileName);
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(numOfData_ + " " + numOfVariables_);
            for (int j = 0; j < numOfVariables_; j++) {
                if (j + 1 != numOfVariables_) {
                    pw.print(numOfStates_[j] + " ");
                } else {
                    pw.print(numOfStates_[j]);
                }
            }

            for (int j = 0; j < rv_.length; j++) {
                pw.print("\n");
                for (int k = 0; k < rv_[0].length; k++) {
                    if (k + 1 != rv_[0].length) {
                        pw.print(rv_[j][k] + " ");
                    } else {
                        pw.print(rv_[j][k]);
                    }
                }
            }
            pw.close();
        } catch (IOException e) {
            System.exit(1);
        }

    }


    /**
     * 作成したBasicProbabilityクラスのメソッドが正しく動作するかを 確認するための main メソッド。場合によっては不要。
     * 以下のメソッドの実行結果が正しいか、手計算（計算機等を使っても良い） の結果と比較してみよ。また、その他のメソッドを使用したり、変数や
     * 値を変えて実行してみて、問題なくプログラムが動くか確認しておくこと。
     *
     * @param args
     */
    public static void main(String[] args) {

        // 確率変数X0 ? X4 の 5 つの確率変数に関するデータ
        int[][] data = {
                {2, 2, 2, 3, 2},// 値の範囲

                {1, 1, 0, 1, 0}, {1, 0, 1, 2, 1}, {0, 0, 1, 0, 0},
                {1, 0, 0, 0, 1}, {0, 0, 0, 2, 0}, {1, 1, 1, 1, 0},
                {1, 0, 0, 2, 1}, {0, 0, 1, 1, 1}, {1, 0, 0, 0, 0},
                {0, 1, 1, 1, 1}, {1, 1, 1, 0, 1}};

        //ex2-2
        BasicProbability pr = null;
        if (args.length == 0) {
            System.out.println("引数が不適当です。データ入力用のファイル名を再度指定して下さい。");
            String fn = null;
            fn = new Scanner(System.in).next();
            pr = new BasicProbability(fn);
        } else {
            pr = new BasicProbability(args[0]);
        }

        //ex2-1
        int[] rv = new int[5], val = new int[5];
        // 入力データの含まれているファイル名をsample.datとした場合
//        BasicProbability pr = new BasicProbability("sample.dat");
//        rv[0] = 0;
//        val[0] = 1;
//        rv[1] = 3;
//        val[1] = 2;
//        System.out.println("P(rv" + rv[0] + "=" + val[0] + ") = " + pr.getProbability(rv[0], val[0]));
//        System.out.println("P(rv" + rv[0] + "=" + val[0] + "|rv" + rv[1] + "=" + val[1] + ") = " +
//                pr.getConditionalProbability(rv[0], val[0], rv[1], val[1]));

        /*
        (1) P(X4 = 1)
        (2) P(X0 = 1, X1 = 0, X2 = 1)
        (3) P(X0 = 1, X2 = 1)
        (4) P(X2 = 0|X3 = 2)
        (5) P(X0 = 0, X2 = 1|X1 = 0, X4 = 0)
         */
//        BasicProbability pr = new BasicProbability(data);
        if (pr.fileExists) {
            //(1) P(X4 = 1)
            System.out.println("(1) P(X4 = 1) = " + pr.getProbability(4, 1));

            //(2) P(X0 = 1, X1 = 0, X2 = 1)
            System.out.println("(2) P(X0 = 1, X1 = 0, X2 = 1) = " + pr.getJointProbability(new int[]{0, 1, 2}, new int[]{1, 0, 1}));

            //(3) P(X0 = 1, X2 = 1)
            System.out.println("(3) P(X0 = 1, X2 = 1) = " + pr.getJointProbability(new int[]{0, 2}, new int[]{1, 1}));

            //(4) P(X2 = 0|X3 = 2)
            System.out.println("(4) P(X2 = 0|X3 = 2) = " + pr.getConditionalProbability(2, 0, 3, 2));

            //(5) P(X0 = 0, X2 = 1|X1 = 0, X4 = 0)
            System.out.println("(5) P(X0 = 0, X2 = 1|X1 = 0, X4 = 0) = " + pr.getConditionalProbability(new int[]{0, 2}, new int[]{0, 1}, new int[]{1, 4}, new int[]{0, 0}));

            System.out.println("出力ファイル名");
            String fn = null;
            fn = new Scanner(System.in).next();
            pr._saveSampleData(fn);
        }
    }

}