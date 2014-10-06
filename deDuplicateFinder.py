import subprocess
import glob
import ast

def getMD5(file):
        cmd = "md5   " + file 
	p = subprocess.Popen(cmd, shell=True, stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, close_fds=True)
	output = p.stdout.read().split("\n")[0]
	md5 = output.split("=")[1]
	return md5 

md5Map = { }
urlMap = { }
unique_count = 0
inputpath = "/Users/srikanthgandupalli/Desktop/Courses/csci572/tika-1.6/outputs/*.json"
for file in glob.glob(inputpath):
	print file
	md5 = getMD5(file)
	if (md5Map.has_key(md5)):
		continue
	else:
		filereader = open(file,'r')
		try:
		
			new_json_data = eval(filereader.read())
			url = new_json_data["job details"][0]['url']
			person = new_json_data["job details"][0]['contactPerson']
			postdate = new_json_data["job details"][0]['postedDate']
			unique_key = url  + person +  postdate ;
     			if(urlMap.has_key(unique_key)):
				continue
			else:
				unique_count = unique_count + 1
				urlMap[unique_key] = 1
			filereader.close()
			md5Map[md5] = 1
		except:
			print "not able to process file " + file
			pass
print unique_count    
			    		
			 	
		

