from pymongo import MongoClient
from jinja2 import Environment, FileSystemLoader
import datetime
import os
import shutil

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

for app in apps.find():
    if(app['statusInt'] == 2):
        app['_hours'] = hours.find_one({'_id': ObjectId(app['_hours'])})
        app['_menu'] = menu.find_one({'_id': ObjectId(app['_menu'])})
        PATH = os.path.dirname(os.path.abspath(__file__))
        TEMPLATE_ENVIRONMENT = Environment(
            autoescape=False,
            loader=FileSystemLoader(os.path.join(PATH, 'androidTemplates')),
            trim_blocks=False) 
        versionArray = app['version'].split('.')
        newPoint = int(versionArray[1]) + 1
        newVersion = versionArray[0] + "." + str(newPoint)
        if not os.path.exists(os.path.join(PATH, 'completedBuilds/' + newVersion):
            shutil.copytree(os.path.join(PATH, 'androidBase'), os.path.join(PATH, 'completedBuilds/' + newVersion))
        

        with open(fname, 'w') as f:
            html = TEMPLATE_ENVIRONMENT.get_template(template_filename).render(app)
            f.write(html)

        



        
