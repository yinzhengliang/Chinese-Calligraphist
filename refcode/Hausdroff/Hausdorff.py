import math

def distance( point1, point2):
    ret = 0.0
    ret = (point1[0]-point2[0])*(point1[0]-point2[0]) + (point1[1]-point2[1])*(point1[1]-point2[1])
    ret = math.sqrt(ret)
    return ret
    
def DpS( sourcePoint, targetStroke):
    ret = 100000.0 # define 100000.0 as positive infinite
    for targetPoint in targetStroke:
        currentDistance = distance(sourcePoint, targetPoint)
        if ret > currentDistance: ret = currentDistance
    return ret

def H_distance(A, B):
    DA = 0.0
    DB = 0.0
    for point in A:
        currentDistance = DpS(point, B)
        if DA < currentDistance: DA = currentDistance

    for point in B:
        currentDistance = DpS(point, A)
        if DB < currentDistance: DB = currentDistance

    ret = 0

    if DA > DB: ret = DA
    else: ret = DB

    return ret


