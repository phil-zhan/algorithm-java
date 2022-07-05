package org.study.system.class48;

public class Code35_338_CountBits {
    public int[] countBits(int n) {
        int[] ans = new int[n+1];

        int count;
        for (int i = 0; i <= n; i++) {
            count = 0;
            for(int j = 0 ; j < 32 ; j++){
                if((i & (1<<j)) != 0){
                    count++;
                }
            }
            ans[i] = count;
        }

        return ans;
    }
}
