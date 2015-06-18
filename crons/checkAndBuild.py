from pymongo import MongoClient
from jinja2 import Environment, FileSystemLoader
import datetime
import os
import shutil
import os.path
import fnmatch
import glob

client = MongoClient()
client = MongoClient('localhost',27017)

db = client.androidBuilder

apps = db.restaurants
hours = db.hours
menu = db.menus
excludeList=[
        'gradlew',
        'model_data.bin',
        'cache.xml',
        'gradle.iml',
        'gradle-wrapper.properties',
        'gradle-wrapper.jar',
        'proguard-rules.pro',
        'app.iml',
        '.gitignore',
        'classes.dex',
        'resources-debug-test.ap_',
        'resources-debug.ap_'
         
        ]
excludes = [
        '*build*',
        '*.iml',
        '*.jar',
        '*.png',
        '*.jpg',
        '*.ap_',
        '*.pro',
        '*.dex',
        '*.bin'
]

#excludes = r'|'.join([fnmatch.translate(x) for x in excludes]) or r'$.'
#This will query the database for any applications that are in status 2 and generate a new build

for app in apps.find():
    if(app['statusInt'] == 2):
        #app['_hours'] = hours.find_one({'_id': ObjectId(app['_hours'])})
        #fetch menu for this applicaiton id
        app['_menu'] = menu.find_one({'_id': app['_menu']})

        #Inform jinja2 of the environment that it will be sourcing files from,
        #and set associated flags
        PATH = os.path.dirname(os.path.abspath(__file__))
        TEMPLATE_ENVIRONMENT = Environment(
            autoescape=False,
           # loader=FileSystemLoader(os.path.join(PATH, 'androidBase')),
            loader=FileSystemLoader(PATH),
            trim_blocks=False) 

        #Discover the version of the app so that similar versions will overwrite and
        #version changes will create new data
        versionArray = app['version'].split('.')
        newPoint = int(versionArray[1]) + 1
        newVersion = versionArray[0] + "." + str(newPoint)

        #set completed builds output directory and create it if it does not exist
        directory = os.path.join(PATH, ('completedBuilds/' + newVersion))
        if not os.path.exists(directory):
            os.mkdir(directory)

        #shutil.copytree(os.path.join(PATH, 'androidBase'), os.path.join(PATH, 'completedBuilds/' + newVersion))
        for root, subdirs, files in os.walk("androidBase"):
            for dire in subdirs:
                print dire
            print "+++++++++++++++++++++++++"
            #subdirs[:] = fnmatch.filter(subdirs,'app*')

            tempFiles = []           
            for f in files:
                allowed = True
                for pattern in excludes:
                    if fnmatch.fnmatch(f,pattern) or fnmatch.fnmatch(os.path.join(root,f),pattern):
                        allowed = False
                        break
                if allowed == True:
                    tempFiles.append(f)
            files = tempFiles


           # print "=================="
            for filename in files:

                output = 'completedBuilds/' + newVersion + '/' + filename 
                print "/////////////" 
                print "filename= " + filename 
                print  "////////////////"
                print "path= " + PATH
                if filename not in excludeList:

                    with open(os.path.join(PATH,output), 'w') as f:
                        #entry = 'androidBase/' + entry
                        #tempNameArray = entry.split('/')
                        #tempName = tempNameArray[len(tempNameArray)-1]
                        print "final dir= " + os.path.join(root,filename)
                        location = os.path.join(root,filename)
                        print "template location = " + location
                        filer = TEMPLATE_ENVIRONMENT.get_template(os.path.join(root,filename)).render({'app' : app})
                        f.write(filer)

##        #iterate through our "templates" and render the files passing in the app dictionary
##        for entry in filesToRender:
##            output = 'completedBuilds/' + newVersion + '/' +  entry
##            with open(os.path.join(PATH,output), 'w') as f:
##                entry = 'androidBase/' + entry
##                tempNameArray = entry.split('/')
#                tempName = tempNameArray[len(tempNameArray)-1]
#                filer = TEMPLATE_ENVIRONMENT.get_template(tempName).render({'app' : app})
#                f.write(filer)
#
