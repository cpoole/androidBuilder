from pymongo import MongoClient
from jinja2 import Environment, FileSystemLoader
import datetime
import os
import shutil
import os.path

client = MongoClient()
client = MongoClient('localhost',27017)

db = client.androidBuilder

apps = db.restaurants
hours = db.hours
menu = db.menus
filesToRender=[
        'app/src/main/assets/menu.json',
        'app/src/main/java/activities/MainActivity.java',
        'app/src/main/res/values/strings.xml',
        'app/src/main/java/fragments/MenuCategoryFragment.java',
        'app/src/main/AndroidManifest.xml',
        'app/build.gradle'
        ]

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
            loader=FileSystemLoader(os.path.join(PATH, 'androidTemplates')),
            trim_blocks=False) 

        #Discover the version of the app so that similar versions will overwrite and
        #version changes will create new data
        versionArray = app['version'].split('.')
        newPoint = int(versionArray[1]) + 1
        newVersion = versionArray[0] + "." + str(newPoint)

        #set completed builds output directory and create it if it does not exist
        directory = os.path.join(PATH, ('completedBuilds/' + newVersion))
        if not os.path.exists(directory):
            shutil.copytree(os.path.join(PATH, 'androidBase'), os.path.join(PATH, 'completedBuilds/' + newVersion))

        #iterate through our "templates" and render the files passing in the app dictionary
        for entry in filesToRender:
            output = 'completedBuilds/' + newVersion + '/' +  entry
            with open(os.path.join(PATH,output), 'w') as f:
                entry = 'androidBase/' + entry
                tempNameArray = entry.split('/')
                tempName = tempNameArray[len(tempNameArray)-1]
                filer = TEMPLATE_ENVIRONMENT.get_template(tempName).render({'app' : app})
                f.write(filer)

        



        
