# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'SketchBoard.ui'
#
# Created: Sun May 05 20:20:56 2013
#      by: PyQt4 UI code generator 4.9.6
#
# WARNING! All changes made in this file will be lost!

from PyQt4 import QtCore, QtGui
from PyQt4.QtCore import *
from PyQt4.QtGui import *

import json
import sys
import os
import random
from collections import defaultdict
import Recognition
import Scale_Translation
import Rotating
import Resampling

SIZE = 100

try:
    _fromUtf8 = QtCore.QString.fromUtf8
except AttributeError:
    def _fromUtf8(s):
        return s

try:
    _encoding = QtGui.QApplication.UnicodeUTF8
    def _translate(context, text, disambig):
        return QtGui.QApplication.translate(context, text, disambig, _encoding)
except AttributeError:
    def _translate(context, text, disambig):
        return QtGui.QApplication.translate(context, text, disambig)

def Xmin(strokes_point_list):
    x = 1000
    for stroke in strokes_point_list:
        for point in stroke:
            if x > point[0]: x = point[0]
    return x

class Canvas(QGraphicsView):
    def __init__(self, holder, parent):
        QtGui.QGraphicsView.__init__(self, parent)
        self.setScene(QtGui.QGraphicsScene(self))
        self.setSceneRect(QtCore.QRectF(self.viewport().rect()))
        self.draw = False
        self.point_list = []
        self.strokes_point_list = []   #a list of all the drawn strokes
        self.stroke_num = 0
        self.history_strokes = []
        self.holder = holder
        self.history_words = ""
        self.xlast = 0.0
        with open( "templates.json" ) as f:
            self.total_strokes_dist = json.loads( f.readline() )

    def mousePressEvent(self, event):
        if event.button() == Qt.LeftButton:
            if self.draw == False:
                self.draw = True
                self.point_list = []
                self.point_list.append([event.pos().x(),event.pos().y()])

            #elif self.draw == True:
            #    self.point_list.append([event.pos().x(),event.pos().y()])
            #    print 'Appending', str(self.point_list[-1])

        elif event.button() == Qt.MidButton:
            self.history_words += ' '
            
        elif event.button() == Qt.RightButton:
            if self.stroke_num != 0:
                self.history_strokes.append(self.strokes_point_list)
                shape_info_dict = defaultdict()
                shape_info_dict[ "stroke_number" ] = self.stroke_num
                shape_info_dict[ "stroke_points" ] = defaultdict()
                i = 0
                while i < self.stroke_num:
                    i += 1
                    shape_info_dict[  "stroke_points" ][str(i)] = self.strokes_point_list[i - 1]

                xnew = Xmin( self.strokes_point_list )
                if self.xlast > xnew:# x is smaller than before
                    self.history_words += '\r\n'
                    self.xlast = xnew + 2 # 2 is a small number that adjust to single character line
                else:
                    self.xlast = xnew
                newPoints = Resampling.Resample( shape_info_dict[ "stroke_points" ] )
                shape_info_dict[ "stroke_points" ] = newPoints
                newPoints, indicative_angle = Rotating.Rotate_To_Zero( shape_info_dict )
                scale_newPoints = Scale_Translation.Scale_To_Square( newPoints, SIZE )
                translate_newPoints = Scale_Translation.Translate_To_Origin( scale_newPoints )
                shape_info_dict["stroke_points"] = translate_newPoints
                shape_info_dict["indicative_angle"] = indicative_angle
                
                self.Comparison( shape_info_dict )
                
                self.stroke_num = 0
                self.point_list = []
                self.strokes_point_list = []
                self.holder.mousePressEvent( event )
                #print 'Finished drawing. List of all points:', str(self.point_list)

    def mouseReleaseEvent(self, event):
        self.draw = False
        if len( self.point_list ) != 0:
            self.stroke_num += 1
            self.strokes_point_list.append( self.point_list )
            self.point_list = []

    def mouseMoveEvent(self, event):
        if self.draw == True:
            self.point_list.append([event.pos().x(),event.pos().y()])
            #print 'Point at', str(self.point_list[-1])
            
            sp = QPoint(self.point_list[-2][0], self.point_list[-2][1])
            ep = QPoint(self.point_list[-1][0], self.point_list[-1][1])
            start = QtCore.QPointF(self.mapToScene(sp))
            end = QtCore.QPointF(self.mapToScene(ep))
            self.scene().addItem(QtGui.QGraphicsLineItem(QtCore.QLineF(start, end)))

    def Comparison( self, shape_info_dict ):
        self.character = Recognition.Hausdorff( shape_info_dict, self.total_strokes_dist )
        self.history_words += self.character

    def redraw(self):
        self.scene().clear()
        for each_char in self.history_strokes:
            for each_stroke in each_char:
                endpoint = each_stroke[0]
                for point in each_stroke[1 : len(each_stroke)]:
                    startpoint = endpoint
                    endpoint = point
                    sp = QPoint(startpoint[0], startpoint[1])
                    ep = QPoint(endpoint[0], endpoint[1])
                    start = QtCore.QPointF(self.mapToScene(sp))
                    end = QtCore.QPointF(self.mapToScene(ep))
                    self.scene().addItem(QtGui.QGraphicsLineItem(QtCore.QLineF(start, end)))
        self.xlast = Xmin(self.history_strokes[-1])
                
class Ui_MainWindow(object):
    def setupUi(self, MainWindow):
        self.text = ""
        self.draw = False
        MainWindow.setObjectName(_fromUtf8("MainWindow"))
        MainWindow.resize(807, 583)
        self.centralwidget = QtGui.QWidget(MainWindow)
        self.centralwidget.setObjectName(_fromUtf8("centralwidget"))
        self.widget = QtGui.QWidget(self.centralwidget)
        self.widget.setGeometry(QtCore.QRect(10, 10, 431, 491))
        self.widget.setMouseTracking(True)
        self.widget.setAutoFillBackground(False)
        self.widget.setStyleSheet(_fromUtf8("background-color: rgb(255, 199, 120);\n"
"border-color: rgb(0, 0, 0);"))
        self.layout = QtGui.QVBoxLayout(self.widget)
        
        self.view = Canvas(self.widget, self)
        self.layout.addWidget(self.view)
        
        self.widget.setObjectName(_fromUtf8("widget"))
        self.widget_2 = QtGui.QWidget(self.centralwidget)
        self.widget_2.setGeometry(QtCore.QRect(450, 10, 341, 401))
        self.widget_2.setObjectName(_fromUtf8("widget_2"))
        self.label = QtGui.QLabel(self.widget_2)
        self.label.setGeometry(QtCore.QRect(0, 0, 111, 16))
        self.label.setObjectName(_fromUtf8("label"))
        self.textBrowser = QtGui.QTextBrowser(self.widget_2)
        self.textBrowser.setGeometry(QtCore.QRect(0, 20, 341, 381))
        self.textBrowser.setObjectName(_fromUtf8("textBrowser"))
        self.widget_3 = QtGui.QWidget(self.centralwidget)
        self.widget_3.setGeometry(QtCore.QRect(450, 420, 341, 121))
        self.widget_3.setObjectName(_fromUtf8("widget_3"))
        self.label_2 = QtGui.QLabel(self.widget_3)
        self.label_2.setGeometry(QtCore.QRect(10, 0, 46, 13))
        self.label_2.setObjectName(_fromUtf8("label_2"))
        self.textBrowser_2 = QtGui.QTextBrowser(self.widget_3)
        self.textBrowser_2.setGeometry(QtCore.QRect(0, 20, 341, 101))
        self.textBrowser_2.setObjectName(_fromUtf8("textBrowser_2"))
        self.pushButton = QtGui.QPushButton(self.centralwidget)
        self.pushButton.setGeometry(QtCore.QRect(100, 510, 75, 31))
        self.pushButton.setObjectName(_fromUtf8("pushButton"))
        self.pushButton_2 = QtGui.QPushButton(self.centralwidget)
        self.pushButton_2.setGeometry(QtCore.QRect(10, 510, 75, 31))
        self.pushButton_2.setObjectName(_fromUtf8("pushButton_2"))
        self.pushButton_3 = QtGui.QPushButton(self.centralwidget)
        self.pushButton_3.setGeometry(QtCore.QRect(190, 510, 75, 31))
        self.pushButton_3.setObjectName(_fromUtf8("pushButton_3"))
        self.pushButton_4 = QtGui.QPushButton(self.centralwidget)
        self.pushButton_4.setGeometry(QtCore.QRect(280, 510, 75, 31))
        self.pushButton_4.setObjectName(_fromUtf8("pushButton_4"))
        self.pushButton_5 = QtGui.QPushButton(self.centralwidget)
        self.pushButton_5.setGeometry(QtCore.QRect(370, 510, 75, 31))
        self.pushButton_5.setObjectName(_fromUtf8("pushButton_5"))
        MainWindow.setCentralWidget(self.centralwidget)
        self.menubar = QtGui.QMenuBar(MainWindow)
        self.menubar.setGeometry(QtCore.QRect(0, 0, 807, 21))
        self.menubar.setObjectName(_fromUtf8("menubar"))
        self.menuLoadTemplate = QtGui.QMenu(self.menubar)
        self.menuLoadTemplate.setObjectName(_fromUtf8("menuLoadTemplate"))
        MainWindow.setMenuBar(self.menubar)
        self.statusbar = QtGui.QStatusBar(MainWindow)
        self.statusbar.setObjectName(_fromUtf8("statusbar"))
        MainWindow.setStatusBar(self.statusbar)
        self.actionSelectFile = QtGui.QAction(MainWindow)
        self.actionSelectFile.setObjectName(_fromUtf8("actionSelectFile"))
        self.menuLoadTemplate.addAction(self.actionSelectFile)
        self.menubar.addAction(self.menuLoadTemplate.menuAction())

        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)
        QtCore.QObject.connect(self.pushButton_2, QtCore.SIGNAL(_fromUtf8("clicked()")), MainWindow.Undo)
        QtCore.QObject.connect(self.pushButton_4, QtCore.SIGNAL(_fromUtf8("clicked()")), MainWindow.Run)
        
    def Undo( self ):
        self.view.history_words = self.view.history_words [0 : len(self.view.history_words) - 1]
        self.view.history_strokes.remove( self.view.history_strokes[-1] )
        self.view.redraw()
        self.textBrowser.setText(self.view.history_words)
        # remove the new line mark, even it does not affect compiling
        if self.view.history_words[-1] == '\n':
            self.view.history_words = self.view.history_words [0 : len(self.view.history_words) - 2]

    def Run( self ):
        f = open("mytest.cpp", "w")
        f.write(self.view.history_words)
        f.close()
        console = "g++ -o mytest mytest.cpp"
        result = os.system( console + " > log.txt" )
        if result == 0: # success
            os.system(" ./mytest > log2.txt " )
            console += '\n./mytest\n'
            console += open('log2.txt', 'r').read()
        else:
            console += '\n Compile Failure\n'
            console += open('log.txt', 'r').read()

        self.textBrowser_2.setText(console)
        

        #QtCore.QObject.connect(self.widget, event.button(), Dialog.mousePressEvent)
    def mousePressEvent(self, event):
        self.textBrowser.setText(self.view.history_words)
        
    def retranslateUi(self, MainWindow):
        MainWindow.setWindowTitle(_translate("MainWindow", "MainWindow", None))
        self.label.setText(_translate("MainWindow", "Code Recognized", None))
        self.label_2.setText(_translate("MainWindow", "Output", None))
        self.pushButton.setText(_translate("MainWindow", "Redo", None))
        self.pushButton_2.setText(_translate("MainWindow", "Undo", None))
        self.pushButton_3.setText(_translate("MainWindow", "ClearAll", None))
        self.pushButton_4.setText(_translate("MainWindow", "Run", None))
        self.pushButton_5.setText(_translate("MainWindow", "AddTemplate", None))
        self.menuLoadTemplate.setTitle(_translate("MainWindow", "LoadTemplate", None))
        self.actionSelectFile.setText(_translate("MainWindow", "SelectFile", None))

