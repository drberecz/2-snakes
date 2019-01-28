from tkinter import *
import math
import string
from random import randint

i=0
root = Tk()
labelfont = ('consolas', 16, 'normal')


y = 20
x = 20
arr = []
for i in range(y):
    arr.append([0] * x)

gameover = False

def keydown(e):

    sztr_curr = str(e.char)
    sztr_curr.strip()
    allowed = 'WwSsAaDd'
    if allowed.find(sztr_curr)!=-1:
        snk1.snake_dir = sztr_curr


widget = Text(root)
widget.config(bg='gray14', fg='yellow')  
widget.config(font=labelfont)           
widget.config(height=21, width=40)       
widget.pack(fill=Y,ipady=0)
widget.bind("<KeyPress>", keydown)
widget.focus_set()



def food_place ():

    xr = randint ( 2,18)
    yr = randint ( 2,18)
    while   arr[yr][xr] != 0 :
        xr = randint ( 2,18)
        yr = randint ( 2,18)        
    return [yr,xr] 



  




class Kigyo:
  def __init__(self, number, irany):
    self.main = number
    if number == 1 :
        self.arr_snY= [10,10,10,10]
        self.arr_snX= [5,6,7,8]
        self.snY = 10
        self.snX = 8
        self.foodY = 7
        self.foodX = 8
    elif number == 2:
        self.arr_snY= [15,15,15,15]
        self.arr_snX= [9,8,7,6]
        self.snY = 15
        self.snX = 6
        self.foodY = 17
        self.foodX = 8
        self.boundXl = 3
        self.boundXh = 16
        self.boundYl = 4
        self.boundYh = 16
    elif number == 5:
        self.arr_snY= [10]
        self.arr_snX= [15]
        self.snY = 10
        self.snX = 15
        self.foodY = 0
        self.foodX = 0
        self.boundXl = 4
        self.boundXh = 15
        self.boundYl = 5
        self.boundYh = 15

    self.snake_dir = irany

  def snake_update (self):
      global arr
      global gameover
      
      if self.main > 1 : 
        if self.snX<self.boundXl :
           self.snake_dir = "W"            
        if self.snY<self.boundYl :
           self.snake_dir = "D" 
        if self.snX>self.boundXh :
           self.snake_dir = "S" 
        if self.snY>self.boundYh  and  self.snX>=self.boundXl:
           self.snake_dir = "A"
        elif self.snY>self.boundYh and self.snX<self.boundYl:
           self.snake_dir = "W"

      if self.snake_dir == "D" or self.snake_dir == "d":
          self.snX+= 1
      elif self.snake_dir == "A" or self.snake_dir == "a":
          self.snX-= 1
      elif self.snake_dir == "W" or self.snake_dir == "w":
          self.snY-= 1
      elif self.snake_dir == "S" or self.snake_dir == "s":
          self.snY+= 1


        
      if arr[self.snY][self.snX] == 1 or arr[self.snY][self.snX] == 2 or arr[self.snY][self.snX] == 5:
          gameover = True
          print ('\a')
      self.arr_snY.append( self.snY )
      self.arr_snX.append( self.snX )
      sn_len = len(self.arr_snX)
      for i in range (0,sn_len):
        arr[ self.arr_snY[i]][ self.arr_snX[i] ] = self.main

      if self.foodY == self.snY and self.foodX == self.snX and self.main==1:

        self.foodY = food_place ()[0]
        self.foodX = food_place ()[1]

      elif self.foodY == self.snY and self.foodX == self.snX and self.main==2:
        sn_len = len(snk2.arr_snX)
        self.foodY = randint(4,16)
        if sn_len%2==0:
          self.foodX = 2
        else :
          self.foodX = 17
        
      else:
        y_temp = self.arr_snY.pop(0)
        x_temp = self.arr_snX.pop(0)
        arr[y_temp][x_temp] = 0





def arr_update ():
  global arr
  for y in range (0,20):
    for x in range (0,20):
        if y == 0 or y == 19:
            arr[y][x] = 1
        elif x == 0 or x == 19:
            arr[y][x] = 1
        else :
            arr[y][x] = 0




def anim():

    global arr
    global widget
    global gameover
    
    arr[snk1.foodY][snk1.foodX] = 3
    arr[snk2.foodY][snk2.foodX] = 4
    snk1.snake_update()
    snk2.snake_update()
    snk5.snake_update()

    widget.delete('1.0', END)
    widget.configure(fg='grey')
    widget.tag_config("tegg", background="black", foreground="yellow")
    widget.tag_config("tegg2", background="gray14", foreground="blue")
    widget.tag_config("spchr", font = ("Consolas", 13))
    for y in range (0,20):
        sztr = ""
        for x in range (0,20):       
          if  arr[y][x]== 1:
             sztr += "█"
          elif  arr[y][x]== 2:
             sztr += '▓'
          elif  arr[y][x]== 3:
             sztr += '()'
          elif  arr[y][x]== 4:
             sztr += '||'
          elif  arr[y][x]== 5:
             sztr += 'oo'
          else:
             sztr += '░'
        widget.insert (END, sztr+ '\n')
        mod_sztr = 0     
        for x in range (0,20):
            coord = str(y+1)+"."+str(x+math.floor(mod_sztr)) 
            if  arr[y][x]>= 3 and arr[y][x]<= 5:
                mod_sztr += 1                
       #         coord2 = str(y+1)+"."+str(x+math.floor(mod_sztr)+1) 
                widget.tag_add("spchr", coord) 
            if  arr[y][x]== 1:
                widget.tag_add("tegg", coord)  
            if  arr[y][x]== 2:
                widget.tag_add("tegg2", coord)

    if gameover == False:     
        root.after(300, anim)

snk1 = Kigyo (1, "D")
snk2 = Kigyo (2, "A")
snk5 = Kigyo (5, "S")
root.after(400, anim)
arr_update ()

root.mainloop()
