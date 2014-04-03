import json
import math

def Rotate_To_Zero( template_dict ):
    x = 0
    y = 0
    point_num = 0
    
    point_list = template_dict[ "stroke_points" ]

    for point in point_list:
        x += point[0]
        y += point[1]
        point_num += 1

    centroid = [ 1.0 * x / point_num, 1.0 * y / point_num ]
    indicative_angle = math.atan2( centroid[1] - point_list[0][1], centroid[0] - point_list[0][0] )
    newPoints = Rotate_By( centroid, point_list, -indicative_angle )

    return newPoints, indicative_angle

def Rotate_By( centroid, point_list, angle ):
    new_point_list = []
    for point in point_list:
        qx = (point[0] - centroid[0]) * math.cos(angle) - (point[1] - centroid[1]) * math.sin(angle) + centroid[0]
        qy = (point[0] - centroid[0]) * math.sin(angle) + (point[1] - centroid[1]) * math.cos(angle) + centroid[1]
        new_point_list.append([qx,qy])
    return new_point_list

