package pr2Calc;

public class BasicProbability {
    protected int[][] rv_;
    protected int[] numOfStates_; // ���肤��l�̐��̔z��
    protected int numOfVariables_; // ���̒���
    protected int numOfData_; // �f�[�^�̔z��̐��@���Ă̒���

    /**
     * ���������I�u�W�F�N�g���̕ϐ� (�t�B�[���h�ϐ�) ���������A�K�v�Ȓl�������邽�� �̃R���X�g���N�^
     *
     * @param inputData ���̓f�[�^
     */
    public BasicProbability(int[][] inputData) {

        // �z�񂩂�A�ϐ��̎�蓾��l���L�q����Ă���s���������s��=�f�[�^��
        numOfData_ = inputData.length - 1;
        numOfVariables_ = inputData[0].length;
        rv_ = new int[numOfData_][numOfVariables_];
        numOfStates_ = new int[numOfVariables_];

        for (int h = 0; h < numOfVariables_; h++) {
            // �e�ϐ��̎�蓾��l��numOfStates_ �Ɋi�[����
            numOfStates_[h] = inputData[0][h];
        }

        for (int h = 0; h < numOfData_; h++) {
            for (int i = 0; i < numOfVariables_; i++)
                rv_[h][i] = inputData[h + 1][i];
        }
    }

    /**
     * @param rvNum   �l�̏ꏊ
     * @param rvValue �l
     * @return �m��
     */
    public double getProbability(int rvNum, int rvValue) {
        // rvNum : �v�Z�ΏۂƂȂ�m���ϐ��̔ԍ�, rvValue : rvNum�Ŏw�肳�ꂽ�m���ϐ��̒l
        // rvNum = 0, rvValue = 1 �Ȃ�� P(X0 = 1)���v�Z���邱�ƂɑΉ�����
        // ���܂�Xn�̔ԍ��Ɓ@Xn�̒l
        // �@�\����������悤�L�q��ǉ�����
        int result = 0;
        for (int i = 0; i < numOfData_; i++) {
            if (rv_[i][rvNum] == rvValue)
                result++;
        }
        return (double) result / numOfData_;
    }

    /**
     * @param rvNum    |��荶�̒l�̏ꏊ
     * @param rvValue  |��荶�̒l
     * @param cRvNum   |���E�̒l�̏ꏊ
     * @param cRvValue �@|���E�̒l
     * @return �m���@(�ϐ��ƒl�̐ݒ肪�����������ɂ�-1.0��Ԃ����ƂƂ���)
     */
    public double getConditionalProbability(int rvNum, int rvValue, int cRvNum, int cRvValue) {
        double hit = 0.0, // ���������𖞂������ŁArvNum = rvValue �ƂȂ�f�[�^�����J�E���g
                total = 0.0; // ��������(cRvNum = cRvValue)�𖞂����f�[�^�������J�E���g

        if (rvNum >= 0 && rvNum < numOfVariables_ && cRvNum >= 0
                && cRvNum < numOfVariables_) {
            if (rvValue >= 0 && rvValue < numOfStates_[rvNum] && cRvValue >= 0
                    && cRvValue < numOfStates_[cRvNum]) {
                for (int h = 0; h < numOfData_; h++) {
                    // total �� hit �̒l���X�V
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
                    System.out.println("Denominator = 0."); // ���ꂪ0�ł��邱�Ƃ𖾎�
                    return -1.0;
                }
            }
        }
        return -1.0;
    }


    /**
     * @param rvA �l�̏ꏊ
     * @param rvR �@�l
     * @return
     */
    public double getJointProbability(int[] rvA, int[] rvR) {
        //���݂���傫����r
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
     * n��r.v.�̂���1�ȏ�n-k�ȉ��̕ϐ�Xbc �̒l��xbc �ł���Ƃ��������̉��ŁA1�� �ȏ� k �ȉ��̕ϐ� Xar �̒l�� xar
     * �ł�������t���m�� P(Xa0 ,...,Xar |Xb0 ,...,Xbc ) ��Ԃ� (r: P(�E|�E) �̂����A�g|�h
     * ���O�Œl�̒�܂��Ă��� r.v. �̐��Ac: P(�E|�E) �� �g|�h ��� ��Œl�̒�܂��Ă��� r.v. �̐��j
     *
     * @param rvA  |��荶����rv�̏ꏊ
     * @param rvR  |��荶����rv�̒l
     * @param crvA |���E����rv�̏ꏊ
     * @param crvR |��荶����rv�̒l
     * @return �v�Z���ꂽ�m��
     */
    public double getConditionalProbability(int[] rvA, int[] rvR, int[] crvA, int[] crvR) {
        /**
         * Hint: �����t���m���͉ۑ莑���ɋL�q����Ă����`�ɂ���Ƃ���A �u�����m���^�����m���v�̌`���ƂȂ��Ă���̂ŁA���
         * getJointProbability ���ł���ΊȒP�Ɍv�Z�ł���B
         * �������A��̊ȒP�ȕ���getConditionalProbability�Ɠ��l�A ���ꕔ����0�ƂȂ����ꍇ�̏����͕K�v�Ȃ̂Œ���
         */
        final double condProbability;
        if ((condProbability = getJointProbability(crvA, crvR)) == 0.0) {
            System.out.println("Conditional Probability is 0.");
            return -1.0;
        }
        final int numAvailableR = java.lang.Math.min(rvA.length, rvR.length);
        final int numAvailableC = java.lang.Math.min(crvA.length, crvR.length);
        final int numJoint = numAvailableR + numAvailableC;
        // ���̃��X�g�̍쐬
        int[] joinNums = new int[numJoint];
        int[] joinValues = new int[numJoint];
        System.arraycopy(rvA, 0, joinNums, 0, numAvailableR);
        System.arraycopy(rvR, 0, joinValues, 0, numAvailableR);
        System.arraycopy(crvA, 0, joinNums, numAvailableR, numAvailableC);
        System.arraycopy(crvR, 0, joinValues, numAvailableR, numAvailableC);
        // ���̃��X�g�̊m�� P(A,B)
        final double joinProbability = getJointProbability(joinNums, joinValues);

        // P(A|B) = P(A,B) / P(B)
        return joinProbability / condProbability;

    }

    /**
     * �쐬����BasicProbability�N���X�̃��\�b�h�����������삷�邩�� �m�F���邽�߂� main ���\�b�h�B�ꍇ�ɂ���Ă͕s�v�B
     * �ȉ��̃��\�b�h�̎��s���ʂ����������A��v�Z�i�v�Z�@�����g���Ă��ǂ��j �̌��ʂƔ�r���Ă݂�B�܂��A���̑��̃��\�b�h���g�p������A�ϐ���
     * �l��ς��Ď��s���Ă݂āA���Ȃ��v���O�������������m�F���Ă������ƁB
     *
     * @param args
     */
    public static void main(String[] args) {

        // �m���ϐ�X0 ? X4 �� 5 �̊m���ϐ��Ɋւ���f�[�^
        int[][] data = {
                {2, 2, 2, 3, 2},// �l�͈̔�

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