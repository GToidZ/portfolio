import requests
import datetime
import lxml.html
import xlsxwriter

user = ""
password = ""

wb = xlsxwriter.Workbook('attendance-'+datetime.datetime.now().strftime("%d-%m-%Y_%H-%M")+".xlsx")
ws = wb.add_worksheet()
ws.write('A1', 'ID')
ws.write('B1', 'Name')
ws.write('C1', 'Class')
ws.write('D1', 'Time')
entry = 1

connection_headers = {'user-agent': 'attendance-app/1.0.0', 'connection': 'keep-alive',
                      'cookie': ''}

act_connection_url = ''
act_info_url = ''

while True:
    print("#######################")
    user = input("กรุณาใส่เลขประจำตัว ('close' เพื่อปิดระบบ) | ")
    if user == "close":
        wb.close()
        break
    password = input("กรุณาใส่วันเดือนปีเกิด (เช่น วันที่ 1 สิงหาคม 2545 ให้ใส่เป็น 01082545) | ")

    act_connection_payload = {
        '__VIEWSTATE': '',
        'txtUserName': user,
        'txtUserPassword': password,
        'btnLogin.x': 0,
        'btnLogin.y': 0}
    connect = requests.post(act_connection_url, data=act_connection_payload, headers=connection_headers)
    info = requests.get(act_info_url, headers=connection_headers)
    data = lxml.html.fromstring(info.text)
    if connect.text.find("ข้อมูลไม่ถูกต้อง") > -1:
        print('กรุณากรอกข้อมูลให้ถูกต้อง\n#######################\n')
    else:
        print('\nเข้าเรียนเมื่อ ' + datetime.datetime.now().strftime("%H:%M @ %d/%m/%Y"))

        logged_name = str(data.xpath('//span[@id="lblName"]/text()')[0])
        logged_class = str(data.xpath('//span[@id="lblClassRoom"]/text()')[0])
        logged_id = str(data.xpath('//span[@id="lblStudentID"]/text()')[0])

        print(logged_name + "\n" + logged_class + "\n" + logged_id + "\n#######################\n")
        ws.write('A' + str(entry + 1), logged_id)
        ws.write('B' + str(entry + 1), logged_name)
        ws.write('C' + str(entry + 1), logged_class)
        ws.write('D' + str(entry + 1), datetime.datetime.now().strftime("%H:%M"))
        entry += 1
