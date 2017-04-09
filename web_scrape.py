import nltk
from urllib import urlopen

url="https://www.federalreserve.gov/newsevents/pressreleases/monetary20170315a.htm"
html = urlopen(url).read()
raw = nltk.clean_html(html)
print(raw)
file2write = open("testfile.txt",'w')
file2write.write(raw)
file2write.close()
