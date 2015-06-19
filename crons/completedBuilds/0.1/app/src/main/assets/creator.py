import json
from pprint import pprint
from pymongo import MongoClient

with open('temp.json') as data_file:
    data = json.load(data_file)

Categories=[]

for entry in data:
    typePresent = False
    for insertion in Categories:
        if (entry['type'] == insertion['title']):
            typePresent = True

    if typePresent == False:
        Categories.append({'title':entry['type'], 'entries':[]})
        print 'appended '
    for insertion in Categories:
        if insertion['title'] == entry['type']:
            insertion['entries'].append({
                'title': entry['title'], 
                'description': entry['description'],
                'price': entry['price']
                })

menu={'Categories': Categories}

client = MongoClient('localhost',27017)
db = client.androidBuilder
menus = db.menus
menus.insert_one(menu)
