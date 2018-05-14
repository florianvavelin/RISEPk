# !/usr/bin/python

import glob
import os
import io

f = open("territoires.txt", "r")
data = f.read().splitlines()
for i in range(len(data)):
    temp = data[i].replace("Color(,,)", "Color()")
    print(temp)

"""for old in glob.glob('*.mp4'):
    new = old.replace('- ', 'E')
    os.rename(old, new)"""

