#include<iostream>
using namespace std;

int sum[1000001];
long long dp[1600000];

int hash(int a , int b , int c)
{
    c += 200;
    return a * 80000 + b * 400 + c;
}

long long search(int loc , int last , int d)
{
    if(loc > 18)
        return 0;
    int H = hash(loc , last , d);
    if(dp[H] >= 0)
        return dp[H];
    long long ret = 0;
    for(int i = 0 ; i < 10 ; i++)
    {
        int cur = (last + 137 * i) % 10;
        if(i > 0 && sum[(last + 137 * i) / 10] + d + cur - i == 0)
            ret ++;
        ret += search(loc + 1 , (last + 137 * i) / 10 , d + cur - i);
    }
    dp[H] = ret;
    return ret;
}

int main()
{
    memset(dp , 0xff , sizeof(dp));
    int s = 0;
    sum[0] = 0;
    for(int i = 1 ; i <= 1000 ; i++)
        sum[i] = sum[i / 10] + i % 10;
    cout<<search(1 , 0 , 0) + 1<<endl;
    return 0;
}
