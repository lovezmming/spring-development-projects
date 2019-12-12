import pycurl, json, sys

class Pycurl():

    post_url = sys.argv[1]
    userId = sys.argv[2]
    dateTime = sys.argv[3]

    headers = ["Content-Type:application/json;charset=utf-8"]

    url_result = json.dumps({"userId": userId,
                             "dateTime": dateTime,
                             "resultFlag": "1",
                             "file": "/home/resultFile.xlsx"
                             })

    def test_post():
        c = pycurl.Curl()
        c.setopt(pycurl.HTTPHEADER, headers)
        c.setopt(pycurl.URL, post_url)
        c.setopt(pycurl.POST, 1)
        c.setopt(pycurl.POSTFIELDS, url_result)
        print(c.getinfo(pycurl.CONTENT_TYPE))
        c.perform()
        c.close()

if __name__ == '__main__':
    pycurl = Pycurl()
    pycurl.test_post()