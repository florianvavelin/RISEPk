# !/usr/bin/python
# -*- coding: utf-8 -*-

import glob
import os
import io
import codecs

f = codecs.open("adjacents.txt", "r", "utf-8")
data = f.read().splitlines()
l = 2
for i in range(len(data)):
    temp = data[i].split(',')
    temp[0] += '/'
    data2 = temp[0]
    for j in range(1, len(temp)-1):
        data2 += temp[j] + ','
    data2 += temp[-1]
    print(data2)

"""for old in glob.glob('*.mp4'):
    new = old.replace('- ', 'E')
    os.rename(old, new)"""

