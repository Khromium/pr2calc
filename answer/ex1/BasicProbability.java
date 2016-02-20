package pr2Calc;

public class BasicProbability {
    protected int[][] rv_;
    protected int[] numOfStates_; // 入りうる値の数の配列
    protected int numOfVariables_; // 横の長さ
    protected int numOfData_; // データの配列の数　たての長さ

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
     * @param crvR |より左側のrvの値
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
        final int numAvailableR = java.lang.Math.min(rvA.length, rvR.length);
        final int numAvailableC = java.lang.Math.min(crvA.length, crvR.length);
        final int numJoint = numAvailableR + numAvailableC;
        // 合体リストの作成
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

        /*
        (1) P(X4 = 1)
        (2) P(X0 = 1, X1 = 0, X2 = 1)
        (3) P(X0 = 1, X2 = 1)
        (4) P(X2 = 0|X3 = 2)
        (5) P(X0 = 0, X2 = 1|X1 = 0, X4 = 0)
         */
        BasicProbability pr = new BasicProbability(data);

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

    }

}