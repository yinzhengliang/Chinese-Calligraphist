##############################################################################
#
# Name: Zhengliang Yin
# UIN : 521005224
# UNIV: Texas A&M University
#
# Course: CSCE-624 Sketch Recognition
#
# Homework: Calculate Rubine Features
#
##############################################################################

##############################################################################
#
# Caveat: Please Put this feature.py file under the same parent folder shared
#         by all the gestures folders
#         For example, ./AllLowerCaseLetters/x/xxx.xml
#                      ./AllLowerCaseLetters/y/yyy.xml
#                      ./THammondLowercaseLetters/a/aaa.xml
#                      ./THammondLowercaseLetters/b/bbb.xml
#                      ./THammondLowercaseLetters/a/aaa.xml
#                      ./feture.py
#
# To run, just ./feature.py in the command line, it would write the result for
# each gesture in a result.txt file, which contains the average value and
# standard deviation for every feature. It would also write into a csv file
# for each gesture with the 13 feature of the xml file stroke.
############################################################################

import os
import xml.sax
import xml.sax.handler
import pprint
import math
import re

tmpdict = {}

# sortbytime is used to sort the points in the xml file according to the time
# value, since I checked that building the list from xml may not guarantee the
# sequence of the points.

def sortbytime(word):
    return tmpdict[word]

# Points class here is just about the x, y, and time value

class Points:
  def __init__(self):
    self.x = 0
    self.y = 0
    self.time = 0

# XMLHandler is the class to set the context of parse for the xml file
# fetch the type, id, author value for sketch, and fetch id, x, y, and time
# for points in the xml file

class XMLHandler(xml.sax.handler.ContentHandler):
  def __init__(self):
    #self.inPoint = 0
    self.mapping = {}

  def startElement(self, name, attributes):
    if name == "sketch":
      self.points = {}
      self.type   = attributes["type"]
      self.id     = attributes["id"]
      self.author = attributes["author"]
    elif name == "point":
      self.points[attributes["id"]] = Points()
      self.points[attributes["id"]].x = eval(attributes["x"])
      self.points[attributes["id"]].y = eval(attributes["y"])
      self.points[attributes["id"]].time = eval(attributes["time"])
       
  def endElement(self, name):
    if name == "point":
      self.inPoint = 0
      self.mapping[self.id] = self.points

# Stoke class has 13 features and a list of points with (x, y, time) tuple
# infomation

class Stroke:
    def __init__(self):
        self.f1 = 0.0  # cosine of angle alpha (p0 - p2)
        self.f2 = 0.0  # sine of angle alpha (p0 - p2)
        self.f3 = 0.0  # length of bounding box diagonal 
        self.f4 = 0.0  # arctan of the bounding box diagonal angle
        self.f5 = 0.0  # length from the start point to the end
        self.f6 = 0.0  # cosine of the angle (p0 - pn)
        self.f7 = 0.0  # sine of the angle (p0 - pn)
        self.f8 = 0.0  # length of the stroke
        self.f9 = 0.0  # total angle traversed
        self.f10 = 0.0 # sum of absolute angle rotation
        self.f11 = 0.0 # sharpness, sum of the squared angle rotation
        self.f12 = 0.0 # maximum speed
        self.f13 = 0.0 # total time used

    # caculate the features according to the x[], y[], and t[], the count of
    # points is points
    
    def calculate(self, x, y, t, points):
        # sort to get [max|min]_[x|y]
        sortedx = sorted(x)
        sortedy = sorted(y)
        
        # delta[x|y|t] stored the change of the values
        deltax = []
        deltay = []
        deltat = []

        # theta is the change of the angles
        theta = []

        # square of delta, used to calculate some features
        deltax_sqr = []
        deltay_sqr = []
        deltat_sqr = []

        # calculate delta
        for i in range(points-1):
            deltax.append(x[i+1]-x[i])
            deltay.append(y[i+1]-y[i])
            deltat.append(t[i+1]-t[i])

        # calculate square of delta
        for i in range(points-1):
            deltax_sqr.append(deltax[i]*deltax[i])
            deltay_sqr.append(deltay[i]*deltay[i])
            deltat_sqr.append(deltat[i]*deltat[i])

        # calculate the theta, total rotation
        for i in range(points-2):
            if deltax[i]*deltax[i+1]+deltay[i]*deltay[i+1] == 0:
                angle_rotation = 1.0*(deltax[i+1]*deltay[i]-deltax[i]*deltay[i+1])/0.0000000000001
            else:
                angle_rotation = 1.0*(deltax[i+1]*deltay[i]-deltax[i]*deltay[i+1])/(deltax[i]*deltax[i+1]+deltay[i]*deltay[i+1])
            
            theta.append(math.atan(angle_rotation))
        
        # calculate f1
        xdist = x[2] - x[0]
        ydist = y[2] - y[0]
        hdist = math.sqrt(math.pow(xdist,2)+math.pow(ydist,2))
        if hdist == 0: hdist = 0.0000000000001
        self.f1 = 1.0*xdist/hdist

        # calculate f2
        self.f2 = 1.0*ydist/hdist

        # calculate f3
        xmax = sortedx[points - 1]
        ymax = sortedy[points - 1]
        xmin = sortedx[0]
        ymin = sortedy[0]
        bbdxdist = xmax-xmin
        bbdydist = ymax-ymin
        self.f3 = math.sqrt(math.pow(bbdxdist,2)+math.pow(bbdydist,2))

        # calculate f4
        if bbdxdist == 0: bbdxdist = 0.00000000001
        self.f4 = math.atan(1.0*bbdydist/bbdxdist)

        # calculate f5
        s_e_xdist = x[points-1]-x[0]
        s_e_ydist = y[points-1]-y[0]
        s_e_hdist = math.sqrt(math.pow(s_e_xdist,2)+math.pow(s_e_ydist,2))
        self.f5 = s_e_hdist

        # calculate f6
        if s_e_hdist == 0: s_e_hdist = 0.000000001
        self.f6 = 1.0*s_e_xdist/s_e_hdist

        # calculate f7
        self.f7 = 1.0*s_e_ydist/s_e_hdist

        # calculate f8
        self.f8 = 0.0
        for i in range(points-1):
            self.f8 += math.sqrt(deltax_sqr[i]+deltay_sqr[i])

        # calculate f9
        self.f9 = sum(theta)

        # calculate f10
        self.f10 = 0.0
        for i in range(len(theta)):
            self.f10 += math.fabs(theta[i])

        # calculate f11
        self.f11 = 0.0
        for i in range(len(theta)):
            self.f11 += math.fabs(theta[i]*theta[i])

        # calculate f12
        self.f12 = 0.0
        for i in range(points-1):
            if deltat_sqr[i] == 0: tmpval = 1.0*(deltax_sqr[i]+deltay_sqr[i])
            else: tmpval = 1.0*(deltax_sqr[i]+deltay_sqr[i])/deltat_sqr[i]
            if self.f12 < tmpval: self.f12 = tmpval

        # calculate f13
        self.f13 = t[points-1] - t[0]

# Gesture class has 13 features' average value, their std dev, list of xml,
# list of strokes, 1 parser and 1 XMLHander

class Gesture:
    def __init__(self):
        # average value for all strokes
        self.f1 = 0.0  # cosine of angle alpha (p0 - p2)
        self.f2 = 0.0  # sine of angle alpha (p0 - p2)
        self.f3 = 0.0  # length of bounding box diagonal 
        self.f4 = 0.0  # arctan of the bounding box diagonal angle
        self.f5 = 0.0  # length from the start point to the end
        self.f6 = 0.0  # cosine of the angle (p0 - pn)
        self.f7 = 0.0  # sine of the angle (p0 - pn)
        self.f8 = 0.0  # length of the stroke
        self.f9 = 0.0  # total angle traversed
        self.f10 = 0.0 # sum of absolute angle rotation
        self.f11 = 0.0 # sharpness, sum of the squared angle rotation
        self.f12 = 0.0 # maximum speed
        self.f13 = 0.0 # total time used
        # standard deviation for all strokes
        self.sdf1 = 0.0  # cosine of angle alpha (p0 - p2)
        self.sdf2 = 0.0  # sine of angle alpha (p0 - p2)
        self.sdf3 = 0.0  # length of bounding box diagonal 
        self.sdf4 = 0.0  # arctan of the bounding box diagonal angle
        self.sdf5 = 0.0  # length from the start point to the end
        self.sdf6 = 0.0  # cosine of the angle (p0 - pn)
        self.sdf7 = 0.0  # sine of the angle (p0 - pn)
        self.sdf8 = 0.0  # length of the stroke
        self.sdf9 = 0.0  # total angle traversed
        self.sdf10 = 0.0 # sum of absolute angle rotation
        self.sdf11 = 0.0 # sharpness, sum of the squared angle rotation
        self.sdf12 = 0.0 # maximum speed
        self.sdf13 = 0.0 # total time used
        # number of strokes
        self.numofstrokes = 0.0
        # list of strokes
        self.strokes = []
        # list of xml files
        self.xml = []
        # parsing variables
        self.parser = xml.sax.make_parser()
        self.handler = XMLHandler()

    # Process is to calculate the values of the gesture

    def Process(self, xmllist):
        # read files, add to xml
        self.xml = xmllist

        self.numofstrokes = len(xmllist)

        # parse xml, add to strokes
        for xmlfile in self.xml:
            #parser = xml.sax.make_parser()
            #handler = XMLHandler()
            self.parser.setContentHandler(self.handler)

            self.parser.parse(xmlfile)
            

            #self.parser.close()
            # sort points according to time
            neworder = sorted(self.handler.points)

            tmpdict.clear()
            for terms in neworder:
                tmpdict[terms] = self.handler.points[terms].time

            # to get the ordered list of points x and y
            neworder.sort(key = sortbytime)
            m_stroke = Stroke()
            x_inorder = []
            y_inorder = []
            t_inorder = []
            listlen = len(neworder)

            for pt in neworder:
               x_inorder.append(self.handler.points[pt].x)
               y_inorder.append(self.handler.points[pt].y)
               t_inorder.append(self.handler.points[pt].time)

            # calculate features
            m_stroke.calculate(x_inorder, y_inorder, t_inorder, listlen)

            # add the stroke into the list
            self.strokes.append(m_stroke)

        # done for all the xml files
        # calculate for average value and standard deviation
        for i in range(self.numofstrokes):
            # sum
            self.f1 += self.strokes[i].f1
            self.f2 += self.strokes[i].f2
            self.f3 += self.strokes[i].f3
            self.f4 += self.strokes[i].f4
            self.f5 += self.strokes[i].f5
            self.f6 += self.strokes[i].f6
            self.f7 += self.strokes[i].f7
            self.f8 += self.strokes[i].f8
            self.f9 += self.strokes[i].f9
            self.f10 += self.strokes[i].f10
            self.f11 += self.strokes[i].f11
            self.f12 += self.strokes[i].f12
            self.f13 += self.strokes[i].f13

        # divide by num
        self.f1 = 1.0*self.f1/self.numofstrokes
        self.f2 = 1.0*self.f2/self.numofstrokes
        self.f3 = 1.0*self.f3/self.numofstrokes
        self.f4 = 1.0*self.f4/self.numofstrokes
        self.f5 = 1.0*self.f5/self.numofstrokes
        self.f6 = 1.0*self.f6/self.numofstrokes
        self.f7 = 1.0*self.f7/self.numofstrokes
        self.f8 = 1.0*self.f8/self.numofstrokes
        self.f9 = 1.0*self.f9/self.numofstrokes
        self.f10 = 1.0*self.f10/self.numofstrokes
        self.f11 = 1.0*self.f11/self.numofstrokes
        self.f12 = 1.0*self.f12/self.numofstrokes
        self.f13 = 1.0*self.f13/self.numofstrokes

        # sum of squares
        for i in range(self.numofstrokes):
            self.sdf1 += math.pow(self.strokes[i].f1-self.f1,2)
            self.sdf2 += math.pow(self.strokes[i].f2-self.f2,2)
            self.sdf3 += math.pow(self.strokes[i].f3-self.f3,2)
            self.sdf4 += math.pow(self.strokes[i].f4-self.f4,2)
            self.sdf5 += math.pow(self.strokes[i].f5-self.f5,2)
            self.sdf6 += math.pow(self.strokes[i].f6-self.f6,2)
            self.sdf7 += math.pow(self.strokes[i].f7-self.f7,2)
            self.sdf8 += math.pow(self.strokes[i].f8-self.f8,2)
            self.sdf9 += math.pow(self.strokes[i].f9-self.f9,2)
            self.sdf10 += math.pow(self.strokes[i].f10-self.f10,2)
            self.sdf11 += math.pow(self.strokes[i].f11-self.f11,2)
            self.sdf12 += math.pow(self.strokes[i].f12-self.f12,2)
            self.sdf13 += math.pow(self.strokes[i].f13-self.f13,2)

        # divide by num
        self.sdf1 = math.sqrt(1.0*self.sdf1/self.numofstrokes)
        self.sdf2 = math.sqrt(1.0*self.sdf2/self.numofstrokes)
        self.sdf3 = math.sqrt(1.0*self.sdf3/self.numofstrokes)
        self.sdf4 = math.sqrt(1.0*self.sdf4/self.numofstrokes)
        self.sdf5 = math.sqrt(1.0*self.sdf5/self.numofstrokes)
        self.sdf6 = math.sqrt(1.0*self.sdf6/self.numofstrokes)
        self.sdf7 = math.sqrt(1.0*self.sdf7/self.numofstrokes)
        self.sdf8 = math.sqrt(1.0*self.sdf8/self.numofstrokes)
        self.sdf9 = math.sqrt(1.0*self.sdf9/self.numofstrokes)
        self.sdf10 = math.sqrt(1.0*self.sdf10/self.numofstrokes)
        self.sdf11 = math.sqrt(1.0*self.sdf11/self.numofstrokes)
        self.sdf12 = math.sqrt(1.0*self.sdf12/self.numofstrokes)
        self.sdf13 = math.sqrt(1.0*self.sdf13/self.numofstrokes)


    # print the result to the screen
    
    def Print(self):
        a = [self.f1, self.f2, self.f3, self.f4, self.f5, self.f6, self.f7, self.f8, self.f9, self.f10, self.f11, self.f12, self.f13]
        b = [self.sdf1, self.sdf2, self.sdf3, self.sdf4, self.sdf5, self.sdf6, self.sdf7, self.sdf8, self.sdf9, self.sdf10, self.sdf11, self.sdf12, self.sdf13]
        for i in range(13):
            print "[Feature: %2d]  Ave: %.3f\t      StdDev: %.3f" %(i+1, a[i], b[i])

    # write the result to the csv file
    
    def Write(self,file):
        for i in range(len(self.strokes)):
            match = re.search(r"[\w]+\.xml",self.xml[i])
            file.write(match.group()+',')
            match = re.findall(r"./[\w]+/([\w]+)/",self.xml[i])
            file.write(match[0]+',')
            file.write(str(self.strokes[i].f1)+',')
            file.write(str(self.strokes[i].f2)+',')
            file.write(str(self.strokes[i].f3)+',')
            file.write(str(self.strokes[i].f4)+',')
            file.write(str(self.strokes[i].f5)+',')
            file.write(str(self.strokes[i].f6)+',')
            file.write(str(self.strokes[i].f7)+',')
            file.write(str(self.strokes[i].f8)+',')
            file.write(str(self.strokes[i].f9)+',')
            file.write(str(self.strokes[i].f10)+',')
            file.write(str(self.strokes[i].f11)+',')
            file.write(str(self.strokes[i].f12)+',')
            file.write(str(self.strokes[i].f13)+'\n')
            
        
# main function is to get the file list and send them to the gesture class

def main():
    # get the number of folders
    GestureMap = {}

    #index = 0
    #Glist = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z']

    filelist = os.listdir('./')
    print filelist

    # [Different People's]: here subpath is '232'
    for subpath in filelist: 
        if '.' not in subpath:
            # remove the files, not folders, find folders in the './232/'
            # lots of gestures in sublist. 1 represent 1 gesture
            # EX: in sublist, there may be '2940'....
            sublist = os.listdir('./'+subpath)
            f = open("./" + subpath + "/Result.txt","w")
            w_file = open('./'+subpath+'/Features.csv','w')
            w_file.write("Sketch Number,Shape Type,Feature1,Feature2,Feature3,Feature4,Feature5,Feature6,Feature7,Feature8,Feature9,Feature10,Feature11,Feature12,Feature13\n")
            index = 0
            
            for subsubpath in sublist:
                # subsubpath is the folders like '2940'
                # find strokes in each gesture folder
                if '.' not in subsubpath:
                    # m_files are the xml files
                    m_files = os.listdir('./'+subpath+'/'+subsubpath)
                    numoffiles = len(m_files)

                    gestureA = Gesture()
                    for i in range(numoffiles):
                        m_files[i] = './'+subpath+'/'+subsubpath + '/' + m_files[i]

                    nonxml = []
                    for toberemoved in m_files:
                        if '.xml' not in toberemoved: nonxml.append(toberemoved)

                    for rmvfile in nonxml:
                        m_files.remove(rmvfile)

                    print 'Gesture:  ', subsubpath
                    gestureA.Process(m_files)
                    gestureA.Print()
                    gestureA.Write(w_file)
                    GestureMap[subsubpath] = gestureA

                    f.write('Gesture:  '+subsubpath+'\n')
                    a = [gestureA.f1, gestureA.f2, gestureA.f3, gestureA.f4, gestureA.f5, gestureA.f6, gestureA.f7, gestureA.f8, gestureA.f9, gestureA.f10, gestureA.f11, gestureA.f12, gestureA.f13]
                    b = [gestureA.sdf1, gestureA.sdf2, gestureA.sdf3, gestureA.sdf4, gestureA.sdf5, gestureA.sdf6, gestureA.sdf7, gestureA.sdf8, gestureA.sdf9, gestureA.sdf10, gestureA.sdf11, gestureA.sdf12, gestureA.sdf13]

                    for i in range(13):
                        f.write("[Feature: %2d]  Ave: %.3f\t      StdDev: %.3f\n" %(i+1, a[i], b[i]))

                    #index = index + 1

            w_file.close()
            f.close()

                
                    
    f.close()
    
    # create a gesture for a folder, process it and print it

    ### just test './232/2940/'

    #m_files = os.listdir('./232/2940')
    #numoffiles = len(m_files)

    #gestureA = Gesture()
    #for i in range(numoffiles):
    #    m_files[i] = './232/2940/' + m_files[i]
    ###print m_files

    #gestureA.Process(m_files)
    #gestureA.Print()



if __name__ == '__main__':
  main()
    
