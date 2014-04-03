import Scale_Translation
import Rotating
import json
import math
import os

#Bounding Box Size: 100
SIZE = 100

#This is for training
def get_input( original_file_path ):
    dir_files = os.listdir( original_file_path )
    for filename in dir_files:
        if ".json" in filename:
            #parse json file
            f = open( original_file_path + filename, "r" )
            template_dict = json.loads(f.readline())
            f.close()

            stroke_points_dict = template_dict[ "stroke_points" ]
            
            #sampling
            newPoints = Resample( stroke_points_dict )
            
            template_dict["stroke_points"] = newPoints

            #Rotating
            newPoints, indicative_angle = Rotating.Rotate_To_Zero( template_dict )

            #Scale and Translation:
            scale_newPoints = Scale_Translation.Scale_To_Square( newPoints, SIZE )
            translate_newPoints = Scale_Translation.Translate_To_Origin( scale_newPoints )
            
            template_dict["stroke_points"] = translate_newPoints
            template_dict["indicative_angle"] = indicative_angle
            f = open( original_file_path + filename, "w" )
            f.write( json.dumps( template_dict ) + "\n" )
            f.close()
    

def Resample( stroke_point_dict ):
    path_length = 0.0
    points = []
    n = 0
    D = 0
    newPoints = []
    
    for stroke_num in range(1, len(stroke_point_dict) + 1):
        #n += len( stroke_point_dict[stroke_num] )
        point_list = stroke_point_dict[str(stroke_num)]
        i = 0

        points.append([])
        while i < len( point_list ) - 1:
            point1 = point_list[ i ]
            point2 = point_list[ i + 1 ]
            points[stroke_num - 1].append( point1 )
            #points.append( point2 )
            path_length += math.sqrt( math.pow( (point2[0] - point1[0]), 2 ) + math.pow( (point2[1] - point1[1]), 2 ) )
            i += 1
        points[stroke_num - 1].append(point_list[ i ])

    I = 1.0 * path_length / 64


    for stroke_num in range(len(points)):
        newPoints.append( points[stroke_num][0] )
        i = 1
        while i < len( points[stroke_num] ):
            d = math.sqrt( math.pow( (points[stroke_num][i][0] - points[stroke_num][i-1][0]), 2 ) + math.pow( (points[stroke_num][i][1] - points[stroke_num][i-1][1]), 2 ) )
            if (D + d) >= I:
                qx = points[stroke_num][i-1][0] + ( 1.0 * (I-D) / d ) * ( points[stroke_num][i][0] - points[stroke_num][i-1][0] )
                qy = points[stroke_num][i-1][1] + ( 1.0 * (I-D) / d ) * ( points[stroke_num][i][1] - points[stroke_num][i-1][1] )
                new_point = [ qx, qy ]
                newPoints.append( new_point )
                points[stroke_num].insert( i, new_point )
                D = 0
            else:
                D = D + d
            i += 1

    return newPoints



