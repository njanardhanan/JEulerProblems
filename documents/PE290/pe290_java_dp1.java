public class E290d137 {
    public static void main(String[] args) {
        int m = 21, m1 = 17, s = 137, t = 10, n = 9, p1 = 200, p = 2*p1, z = (s + 1) * p;
        long ans = 1, start = System.currentTimeMillis();
        long[][] x = new long[m][z];
        int jmax = 0;
        for (int i = 0; i <t;i++){
            int k = i*s, d = k%t - i, c = k/t, j = p * (c + 1) + d;
            x[0][j] = 1;
            if (j>jmax) jmax = j;
        }
        for (int r1 = 1, r = 0; r1 < m; r++ , r1++){
            int imax = (r1 > m1) ? 0 : n, jm = 0;
            for (int j = 0; j <= jmax; j++)if(x[r][j] >0){
                long x1 = x[r][j];
                int c = j/p - 1, d = j % p;
                if (d >= p1){d -= p; c++;}
                for (int i = 0; i <= imax; i++){
                    int k = c + s*i, c1 = k/t, d1 = k%t + d - i, j1 = p*(c1 + 1) + d1;
                    if (j1 > jm) jm = j1;
                    x[r1][j1] += x1;
                    if((j1==p)&&(j !=p)){ // new answers
                        ans += x1;
                        System.out.println("  r1 :"  + r1  + " new answer " +  ans + " after " + (System.currentTimeMillis() -start));
                    }
                }
            }
            jmax = jm;
        }
        //  r1 :20 new answer 20444710234716473 after 73
    }
}