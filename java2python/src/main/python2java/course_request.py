import requests, json, sys

# post_url = 'http://192.168.1.123:8108/python2Java'
post_url = sys.argv[1]

userId = sys.argv[2]

dateTime = sys.argv[3]

result_url = json.dumps({"userId": userId,
         "dateTime": dateTime,
         "resultFlag": "1",
         "file":"/home/resultFile.xlsx"
         })

headers = {'Accept': 'application/json', 'Content-Type': 'application/json'}

def do_post():
    print(headers)
    rsp = requests.post(post_url, result_url, headers=headers)
    print(rsp.text)

if __name__ == '__main__':
    do_post()

