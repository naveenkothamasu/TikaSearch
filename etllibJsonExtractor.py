import glob
import os
def executecommand(file):
     command = "tsvtojson -t input1.tsv   -j " + file +   "  -c col.txt -o employmentjobs"
     print command 
inputpath = '/Users/srikanthgandupalli/csci572/sampledata/*.tsv'
outputdir = '/Users/srikanthgandupalli/csci572/code/outputdata/'   
files=glob.glob(inputpath)
outfilename = "output"
outfilesuffix = ".json"
counter  = 0
for file in files:
	f = open(file, 'r' )
	for line in f.readlines():
		os.system(" rm -rf input1.tsv ")
		tempinputfile = open("input1.tsv" , 'w')
		tempinputfile.write(line)
		tempinputfile.close()
		executecommand(outputdir+outfilename+str(counter)+outfilesuffix)
		counter = counter + 1
			 
     
