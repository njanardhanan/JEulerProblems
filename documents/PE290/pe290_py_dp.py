from time import time
sttm=time()

def p290(n1=18,p1=137) :
    def sd1(n1) :
        sm1=0
        while n1 : sm1,n1=sm1+n1%10,n1/10
        return sm1

    n2,md1=18*n1+1,9*n1+1
    dg0=[[0 for i1 in xrange(n2)] for j1 in xrange(p1)]
    dg0[0][md1]=1
    lcr1=[0]*p1
    for p0 in xrange(p1) :
        for d1 in xrange(10) :
            n0=p0+p1*d1
            r0,n0=d1-(n0%10),n0/10
            if not lcr1[p0] : lcr1[p0]=[(r0,n0)]
            else : lcr1[p0].append((r0,n0))
    for d0 in xrange(n1) :
        dg1=[[0 for i1 in xrange(n2)] for j1 in xrange(p1)]
        for p0 in xrange(p1) :
            for sn1 in xrange(n2) :
                if dg0[p0][sn1] :
                    for (r0,n0) in lcr1[p0] : dg1[n0][sn1+r0]+=dg0[p0][sn1]
        dg0=dg1

    return sum(dg0[p0][md1+sd1(p0)] for p0 in xrange(p1))

cn1=p290()

print '\nThe count is ',cn1
print "\nTime taken ",time()-sttm," s"