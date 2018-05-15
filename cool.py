# !/usr/bin/python
# -*- coding: utf-8 -*-

import glob
import os
import io
import codecs

f = codecs.open("territoires.txt", "r", "utf-8")
data = f.read().splitlines()
l = 2
for i in range(len(data)):
    temp = data[i]+',Color(255,255,'+str(l)+')'
    l += 5
    print(temp)

"""for old in glob.glob('*.mp4'):
    new = old.replace('- ', 'E')
    os.rename(old, new)"""

