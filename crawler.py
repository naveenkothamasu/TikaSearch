import glob 
import os
for f in glob.glob("../input/*.tsv"):
	print f
	tfwriter = open("tempinput.txt","w")
	tfwriter.write(f)
	tfwriter.close()
	print 'processing file : ' + f
	os.system(" java -jar ../tika-app/target/tika-app-1.6.jar " + f)
	os.system("rm -f tempinput.txt")
 

