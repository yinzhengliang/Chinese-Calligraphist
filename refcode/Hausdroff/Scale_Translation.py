import sys
#Bounding box size is 10*10
def Translate_To_Origin( scale_newPoints ):
    x = 0
    y = 0
    point_num = 0
    newPoints = []
    '''
    for point in scale_newPoints:
        x += point[0]
        y += point[1]
        point_num += 1

    centroid = [ 1.0 * x / point_num, 1.0 * y / point_num ]
    '''
    x_min = sys.maxint
    y_min = sys.maxint
    for point in scale_newPoints:
        if point[0] < x_min:
            x_min = point[0]
        if point[1] < y_min:
            y_min = point[1]    

    for point in scale_newPoints:
        #qx = point[0] - centroid[0]
        #qy = point[1] - centroid[1]
        qx = point[0] - x_min
        qy = point[1] - y_min
        newPoints.append( [qx, qy] )
    return newPoints


def Scale_To_Square( point_list, SIZE ):
    newPoints = []
    B = Bounding_Box( point_list )
    for point in point_list:
        qx = point[0] * (1.0 * SIZE / B['B_width'] )
        qy = point[1] * (1.0 * SIZE / B['B_height'] )
        newPoints.append( [qx,qy] )
    return newPoints


def Bounding_Box( point_list ):
    x_min = point_list[0][0]
    x_max = point_list[0][0]
    y_min = point_list[0][1]
    y_max = point_list[0][1]
    
    for point in point_list:
        if point[0] > x_max:
            x_max = point[0]
        if point[0] < x_min:
            x_min = point[0]
        if point[1] > y_max:
            y_max = point[1]
        if point[1] < y_min:
            y_min = point[1]
    B_width = x_max - x_min
    B_height = y_max - y_min
    
    return { 'B_width':B_width, 'B_height':B_height }

