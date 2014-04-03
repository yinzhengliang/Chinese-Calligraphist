from Hausdorff import *
from collections import defaultdict
import sys
import math


def Hausdorff( shape_info_dict, total_strokes_dict ):
    recognized_cha = ""
    hausdorff_val = 0.0

    stroke_num = shape_info_dict[ "stroke_number" ]
    candidate_list = total_strokes_dict[ str(stroke_num) ]

    A = shape_info_dict[ "stroke_points" ]
    

    result_dict = defaultdict()
    angle_dict = {}
    for B in candidate_list:
        recognized_cha = B[ "name" ]
        character_point_list = B[ "points" ]

        H_value = H_distance( A, character_point_list )
        angle1 = B[ "indicative_angle" ]
        angle2 = shape_info_dict[ "indicative_angle" ]
        angle = math.fabs(angle1 - angle2)
        if recognized_cha not in result_dict:
            result_dict[ recognized_cha ] = H_value
            angle_dict[ recognized_cha ] = angle
        else:
            if H_value < result_dict[ recognized_cha ]:
                result_dict[ recognized_cha ] = H_value
                angle_dict[ recognized_cha ] = angle
        
    sorted_list = sorted( result_dict, key = result_dict.get)
    #sorted_list = sorted_list[0:3]

    return sorted_list[0]
    #print "======================="
    #print sorted_list
    #tmp_dict = {}
    #for cha in sorted_list:
    #    tmp_dict[cha] = angle_dict[cha]

    #return sorted( tmp_dict, key = tmp_dict.get)
    

